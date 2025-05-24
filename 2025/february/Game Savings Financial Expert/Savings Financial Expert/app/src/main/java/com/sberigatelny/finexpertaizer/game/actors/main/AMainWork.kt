package com.sberigatelny.finexpertaizer.game.actors.main

import com.badlogic.gdx.math.Vector2
import com.sberigatelny.finexpertaizer.game.actors.APers
import com.sberigatelny.finexpertaizer.game.actors.APlus
import com.sberigatelny.finexpertaizer.game.actors.ATop
import com.sberigatelny.finexpertaizer.game.actors.button.AButton
import com.sberigatelny.finexpertaizer.game.screens.GameScreen
import com.sberigatelny.finexpertaizer.game.screens.StartScreen
import com.sberigatelny.finexpertaizer.game.screens.WorkScreen
import com.sberigatelny.finexpertaizer.game.utils.Block
import com.sberigatelny.finexpertaizer.game.utils.TIME_ANIM_SCREEN
import com.sberigatelny.finexpertaizer.game.utils.actor.*
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedMainGroup
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.game.utils.region
import com.sberigatelny.finexpertaizer.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainWork(
    override val screen: WorkScreen,
): AdvancedMainGroup() {

    private val IS_PEREEZD = gdxGame.ds_IsNewWork.flow.value

    // Actors
    private val aTop       = ATop(screen)
    private val btnToHome  = AButton(screen, AButton.Type.Home)
    private val aPers      = APers(screen, 0)
    private val aPlus      = APlus(screen, 10)

    // Field
    private val tmpVector = Vector2()
    private var clickCost = 10

    override fun addActorsOnGroup() {
        color.a = 0f

        addATop()
        addBtnToHome()
        addAPers()
        addAndFillActor(aPlus)

        animShowMain {
            aPers.startAnim()
        }

        this.setOnClickListenerWithBlock(
            touchUpBlock = { x, y ->
                aPlus.animInPos(tmpVector.set(x, y))
                gdxGame.soundUtil.apply { play(touch) }

                gdxGame.ds_Balance.update {
                    val result = it + clickCost
                    result
                }
            }
        )

        if (IS_PEREEZD) {
            screen.setBackBackground(gdxGame.assetsAll.back_5.region)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addATop() {
        addActor(aTop)
        aTop.setBounds(65f, 1888f, 855f, 101f)

        aTop.aTimer.blockHours = { hour ->
            if (gdxGame.ds_IsTutorial.flow.value.not()) {
                when {
                    hour == 17 -> {
                        updateState(State.ToHome)
                    }
                }
            }
        }
    }

    private fun addBtnToHome() {
        addActor(btnToHome)
        btnToHome.setBounds(706f, 1665f, 214f, 138f)
        btnToHome.disable()
        btnToHome.color.a = 0f
        btnToHome.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name)
            }
        }
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
            State.ToHome -> {
                aPers.updateChel(7)
                btnToHome.apply {
                    this.animShow(TIME_ANIM_SCREEN)
                    this.enable()
                }
            }
        }
    }

    // Class ---------------------------------------------------------------------

    private enum class State {
        ToHome
    }

}