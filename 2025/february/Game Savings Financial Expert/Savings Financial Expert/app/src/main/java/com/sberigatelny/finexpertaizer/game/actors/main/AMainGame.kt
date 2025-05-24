package com.sberigatelny.finexpertaizer.game.actors.main

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.sberigatelny.finexpertaizer.game.actors.APers
import com.sberigatelny.finexpertaizer.game.actors.APlus
import com.sberigatelny.finexpertaizer.game.actors.ATop
import com.sberigatelny.finexpertaizer.game.actors.button.AButton
import com.sberigatelny.finexpertaizer.game.screens.GameScreen
import com.sberigatelny.finexpertaizer.game.screens.StartScreen
import com.sberigatelny.finexpertaizer.game.screens.WorkScreen
import com.sberigatelny.finexpertaizer.game.utils.Acts
import com.sberigatelny.finexpertaizer.game.utils.Block
import com.sberigatelny.finexpertaizer.game.utils.TIME_ANIM_SCREEN
import com.sberigatelny.finexpertaizer.game.utils.actor.*
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedMainGroup
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.game.utils.region
import com.sberigatelny.finexpertaizer.game.utils.runGDX
import com.sberigatelny.finexpertaizer.util.log
import kotlinx.coroutines.launch

class AMainGame(
    override val screen: GameScreen,
): AdvancedMainGroup() {

    private val IS_PEREEZD = gdxGame.ds_IsPereezd.flow.value

    // Actors
    private val aTop       = ATop(screen)
    private val imgDialog  = Image(gdxGame.assetsAll.d2)
    private val imgHand    = Image(gdxGame.assetsAll.tut_hand_click)
    private val btnToWork  = AButton(screen, AButton.Type.ToWork)
    private val btnPereezd = AButton(screen, AButton.Type.Pereezd)
    private val aPers      = APers(screen, 0)
    private val aPlus      = APlus(screen, 1)

    // Field
    private val tmpVector = Vector2()
    private var clickCost = 10

    override fun addActorsOnGroup() {
        color.a = 0f

        addATop()
        addBtnToWork()
        addBtnPereezd()
        addImgDialog()
        addImgHand()
        addAPers()
        addAndFillActor(aPlus)

        animShowMain {
            if (gdxGame.ds_IsTutorial.flow.value) updateState(State.Tutorial_1) else aPers.startAnim()
        }

        this.setOnClickListenerWithBlock(
            touchUpBlock = { x, y ->
                aPlus.animInPos(tmpVector.set(x, y))
                gdxGame.soundUtil.apply { play(touch) }

                gdxGame.ds_Balance.update {
                    val result = it + clickCost
                    if (gdxGame.ds_IsTutorial.flow.value && result == 1000) updateState(State.Tutorial_2)
                    result
                }
            }
        )

        if (IS_PEREEZD) {
            updateClickCost(5)
            screen.setBackBackground(gdxGame.assetsAll.back_3.region)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addATop() {
        addActor(aTop)
        aTop.setBounds(65f, 1888f, 855f, 101f)

        var propuskCounter = 0
        var isUvolen  = true

        aTop.aTimer.blockHours = { hour ->
            if (gdxGame.ds_IsTutorial.flow.value.not()) {
                when {
                    hour in 9..12 -> {
                        updateState(State.ToWork)
                    }
                    hour == 13 -> {
                        propuskCounter++
                        if (propuskCounter >= 3) {
                            if (isUvolen) {
                                isUvolen = false
                                updateState(State.Uvolen)
                            }
                        } else {
                            isUvolen = true
                            updateState(State.HideWork)
                        }
                    }
                }
            }
        }

        var a = true
        if (IS_PEREEZD.not()) {
            coroutine?.launch {
                gdxGame.ds_Balance.flow.collect { balance ->
                    runGDX {
                        if (balance >= 20_000) {
                            if (a) {
                                a = false
                                updateState(State.Mae_20k)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addBtnToWork() {
        addActor(btnToWork)
        btnToWork.setBounds(706f, 1665f, 214f, 138f)
        btnToWork.disable()
        btnToWork.color.a = 0f
        btnToWork.setOnClickListener {
            gdxGame.ds_IsTutorial.update { false }

            screen.hideScreen {
                gdxGame.navigationManager.navigate(WorkScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addBtnPereezd() {
        addActor(btnPereezd)
        btnPereezd.setBounds(63f, 1614f, 213f, 189f)
        btnPereezd.disable()
        btnPereezd.color.a = 0f
        btnPereezd.setOnClickListener {
            gdxGame.ds_IsPereezd.update { true }
            gdxGame.ds_Balance.update { it - 20_000 }
            gdxGame.soundUtil.apply { play(win) }

            screen.hideScreen {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name)
            }
        }
    }

    private fun addImgDialog() {
        addActor(imgDialog)
        imgDialog.disable()
        imgDialog.color.a = 0f
    }

    private fun addImgHand() {
        addActor(imgHand)
        imgHand.setBounds(485f, 735f, 380f, 421f)
        imgHand.disable()
        imgHand.color.a = 0f
        imgHand.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.95f, 0.95f, 0.2f),
                Acts.scaleTo(1f, 1f, 0.2f),
            )
        ))
    }

    private fun addAPers() {
        addActor(aPers)
        aPers.setBounds(211f, 89f, 564f, 931f)
        aPers.disable()
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ----------------------------------------------

    private fun updateState(state: State) {
        when(state) {
            State.Tutorial_1 -> {
                imgDialog.apply {
                    setBounds(52f, 1368f, 881f, 232f)
                    animShow(TIME_ANIM_SCREEN)
                }
                imgHand.apply {
                    setBounds(485f, 735f, 380f, 421f)
                    animShow(TIME_ANIM_SCREEN)
                }
            }
            State.Tutorial_2 -> {
                imgDialog.animHide(TIME_ANIM_SCREEN) {
                    imgDialog.setBounds(52f, 1022f, 881f, 358f)
                    imgDialog.drawable = TextureRegionDrawable(gdxGame.assetsAll.greet_1000)
                    imgDialog.animShow(TIME_ANIM_SCREEN)
                }
                imgHand.animHide(TIME_ANIM_SCREEN) {
                    imgHand.setBounds(404f, 1379f, 530f, 510f)
                    imgHand.drawable = TextureRegionDrawable(gdxGame.assetsAll.tutorial_hand)
                    imgHand.animShow(TIME_ANIM_SCREEN)
                }
                aPers.updateChel(2)
                btnToWork.apply {
                    this.animShow(TIME_ANIM_SCREEN)
                    this.enable()
                }
            }
            State.Uvolen -> {
                btnToWork.clearActions()
                btnToWork.animHide(0.2f)
                btnToWork.disable()

                imgDialog.animHide(TIME_ANIM_SCREEN) {
                    imgDialog.setBounds(52f, 1085f, 881f, 295f)
                    imgDialog.drawable = TextureRegionDrawable(gdxGame.assetsAll.nema_rabota)
                    imgDialog.animShow(TIME_ANIM_SCREEN)
                }
                aPers.updateChel(3)

                gdxGame.soundUtil.apply { play(fail) }

                val img = Image(gdxGame.assetsAll.redfalse)
                addActor(img)
                img.setBounds(706f, 1601f, 214f, 202f)

                img.animDelay(3f) {
                    img.animHide(TIME_ANIM_SCREEN) { img.remove() }

                    aPers.updateChel(7)
                    btnToWork.apply {
                        this.animShow(TIME_ANIM_SCREEN)
                        this.enable()
                    }
                    updateState(State.NewWork)
                }
            }
            State.NewWork -> {
                gdxGame.ds_IsNewWork.update { true }

                imgDialog.animHide(TIME_ANIM_SCREEN) {
                    imgDialog.setBounds(52f, 1085f, 881f, 295f)
                    imgDialog.drawable = TextureRegionDrawable(gdxGame.assetsAll.greet_new_work)
                    imgDialog.animShow(TIME_ANIM_SCREEN)
                }
                aPers.updateChel(2)
            }
            State.HideWork -> {
                btnToWork.animHide(0.2f)
                btnToWork.disable()
            }
            State.Mae_20k -> {
                imgDialog.animHide(TIME_ANIM_SCREEN) {
                    imgDialog.setBounds(52f, 1022f, 881f, 358f)
                    imgDialog.drawable = TextureRegionDrawable(gdxGame.assetsAll.greet_10k)
                    imgDialog.animShow(TIME_ANIM_SCREEN)
                }
                aPers.updateChel(1)
                btnPereezd.apply {
                    this.animShow(TIME_ANIM_SCREEN)
                    this.enable()
                }
            }
            State.ToWork -> {
                aPers.updateChel(7)
                btnToWork.apply {
                    this.animShow(TIME_ANIM_SCREEN)
                    this.enable()
                }
            }
        }
    }

    private fun updateClickCost(cost: Int) {
        clickCost = cost
        aPlus.updateSymbol(cost)
    }

    // Class ---------------------------------------------------------------------

    private enum class State {
        Tutorial_1, Tutorial_2, Uvolen, Mae_20k, ToWork, HideWork, NewWork
    }

}