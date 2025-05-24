package com.sburbanaizer.verginiafalesska.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.sburbanaizer.verginiafalesska.game.screens.ImproveScreen
import com.sburbanaizer.verginiafalesska.game.utils.Block
import com.sburbanaizer.verginiafalesska.game.utils.GameColor
import com.sburbanaizer.verginiafalesska.game.utils.TIME_ANIM_SCREEN
import com.sburbanaizer.verginiafalesska.game.utils.actor.animDelay
import com.sburbanaizer.verginiafalesska.game.utils.actor.animHide
import com.sburbanaizer.verginiafalesska.game.utils.actor.animShow
import com.sburbanaizer.verginiafalesska.game.utils.actor.enable
import com.sburbanaizer.verginiafalesska.game.utils.actor.setBoundsScaled
import com.sburbanaizer.verginiafalesska.game.utils.actor.setOnClickListener
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedGroup
import com.sburbanaizer.verginiafalesska.game.utils.advanced.imgInvestWin
import com.sburbanaizer.verginiafalesska.game.utils.font.FontParameter
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame
import com.sburbanaizer.verginiafalesska.game.utils.runGDX
import com.sburbanaizer.verginiafalesska.game.utils.toSeparate
import com.sburbanaizer.verginiafalesska.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AMainImprove(
    override val screen: ImproveScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters("1234lv")
    private val font42        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(42))

    private val ls42 = Label.LabelStyle(font42, GameColor.green_39)

    private val lblLvlProdaja = Label("", ls42)
    private val lblLvlDobicha = Label("", ls42)

    private val imgImpa = Image(gdxGame.assetsAll.improvment)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgImpa()
        addLbls()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblLvlProdaja, lblLvlDobicha)
        lblLvlProdaja.setBounds(334f, 1028f, 74f, 29f)
        lblLvlDobicha.setBounds(230f, 875f, 74f, 29f)

        coroutine?.launch {
            gdxGame.ds_Level.flow.collect { level ->
                runGDX {
                    lblLvlProdaja.setText("${level.lvlProdaja} lv")
                    lblLvlDobicha.setText("${level.lvlDobicha} lv")
                }
            }
        }
    }

    private fun addImgImpa() {
        addActor(imgImpa)
        imgImpa.setBounds(27f, 842f, 660f, 420f)
    }

    private fun addBtns() {
        val aBack = Actor()
        val a200  = Actor()
        val a300  = Actor()
        addActors(aBack, a200, a300)

        aBack.apply {
            setBounds(28f, 1171f, 85f, 85f)
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }

        a200.apply {
            setBounds(543f, 995f, 142f, 95f)
            setOnClickListener(gdxGame.soundUtil) {
                if(gdxGame.ds_Balance.flow.value - 200 >= 0) {
                    gdxGame.ds_Balance.update { it - 200 }
                    if (gdxGame.ds_Level.flow.value.lvlProdaja + 1 <= 4) {
                        gdxGame.ds_Level.update {
                            it.lvlProdaja += 1
                            it
                        }
                    }
                }
            }
        }
        a300.apply {
            setBounds(543f, 843f, 142f, 95f)
            setOnClickListener(gdxGame.soundUtil) {
                if(gdxGame.ds_Balance.flow.value - 300 >= 0) {
                    gdxGame.ds_Balance.update { it - 300 }
                    if (gdxGame.ds_Level.flow.value.lvlDobicha + 1 <= 4) {
                        gdxGame.ds_Level.update {
                            it.lvlDobicha += 1
                            it
                        }
                    }
                }
            }
        }
    }

    // Logic -------------------------------------------------------------------

    private fun investSum(actor: Actor, sum: Int, time: Int) {
        gdxGame.coroutine.launch {
            var timer = 0
            while (isActive && timer <= time) {
                log("timer = $timer | sum = $sum")
                timer += 1
                delay(1000)
            }
            runGDX {
                actor.enable()
                showWin(sum)
            }
        }
    }

    fun showWin(sum: Int) {
        val winSum = when(sum) {
            300  -> 330
            500  -> 750
            1000 -> 2000
            else -> 330
        }
        gdxGame.ds_Balance.update { it + winSum }

        imgInvestWin.clearActions()
        imgInvestWin.color.a = 0f
        imgInvestWin.drawable = TextureRegionDrawable(
            when(sum) {
                300  -> gdxGame.assetsAll.p1
                500  -> gdxGame.assetsAll.p2
                1000 -> gdxGame.assetsAll.p3
                else -> gdxGame.assetsAll.p1
            }
        )
        imgInvestWin.animShow(0.25f) {
            imgInvestWin.animDelay(2f) {
                imgInvestWin.animHide(0.25f)
            }
        }
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        animShow(TIME_ANIM_SCREEN)
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

}