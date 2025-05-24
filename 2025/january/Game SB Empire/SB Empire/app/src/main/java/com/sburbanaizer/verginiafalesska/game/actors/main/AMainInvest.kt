package com.sburbanaizer.verginiafalesska.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.sburbanaizer.verginiafalesska.game.screens.InvestScreen
import com.sburbanaizer.verginiafalesska.game.utils.Block
import com.sburbanaizer.verginiafalesska.game.utils.TIME_ANIM_SCREEN
import com.sburbanaizer.verginiafalesska.game.utils.actor.animDelay
import com.sburbanaizer.verginiafalesska.game.utils.actor.animHide
import com.sburbanaizer.verginiafalesska.game.utils.actor.animShow
import com.sburbanaizer.verginiafalesska.game.utils.actor.disable
import com.sburbanaizer.verginiafalesska.game.utils.actor.enable
import com.sburbanaizer.verginiafalesska.game.utils.actor.setBounds
import com.sburbanaizer.verginiafalesska.game.utils.actor.setOnClickListener
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedGroup
import com.sburbanaizer.verginiafalesska.game.utils.advanced.imgInvestWin
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame
import com.sburbanaizer.verginiafalesska.game.utils.runGDX
import com.sburbanaizer.verginiafalesska.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AMainInvest(
    override val screen: InvestScreen,
): AdvancedGroup() {

    private val imgInvest    = Image(gdxGame.assetsAll.investment)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgInvestment()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgInvestment() {
        addActor(imgInvest)
        imgInvest.setBounds(27f, 690f, 660f, 572f)
    }

    private fun addBtns() {
        val aBack = Actor()
        val a300  = Actor()
        val a500  = Actor()
        val a1000 = Actor()
        addActors(aBack, a300, a500, a1000)

        aBack.apply {
            setBounds(28f, 1171f, 85f, 85f)
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
        a300.apply {
            setBounds(543f, 995f, 142f, 95f)
            setOnClickListener(gdxGame.soundUtil) {
                investSum(this, 300, 10)
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
        a500.apply {
            setBounds(543f, 843f, 142f, 95f)
            setOnClickListener(gdxGame.soundUtil) {
                investSum(this, 500, 20)
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
        a1000.apply {
            setBounds(522f, 691f, 163f, 95f)
            setOnClickListener(gdxGame.soundUtil) {
                investSum(this, 1000, 30)
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

    // Logic -------------------------------------------------------------------

    private fun investSum(actor: Actor, sum: Int, time: Int) {
        if (gdxGame.ds_Balance.flow.value >= sum) {
            gdxGame.ds_Balance.update { it - sum }

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