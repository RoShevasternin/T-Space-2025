package com.cinvestor.crotcevni.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.cinvestor.crotcevni.game.actors.progress
import com.cinvestor.crotcevni.game.screens.InvestScreen
import com.cinvestor.crotcevni.game.utils.Block
import com.cinvestor.crotcevni.game.utils.TIME_ANIM_SCREEN
import com.cinvestor.crotcevni.game.utils.actor.animDelay
import com.cinvestor.crotcevni.game.utils.actor.animHide
import com.cinvestor.crotcevni.game.utils.actor.animShow
import com.cinvestor.crotcevni.game.utils.actor.enable
import com.cinvestor.crotcevni.game.utils.actor.setOnClickListener
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedMainGroup
import com.cinvestor.crotcevni.game.utils.advanced.imgInvestWin
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.cinvestor.crotcevni.game.utils.runGDX
import com.cinvestor.crotcevni.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.compareTo

class AMainInvest(
    override val screen: InvestScreen,
): AdvancedMainGroup() {

    private val imgMain = Image(gdxGame.assetsAll.INVEST)

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addALevel()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALevel() {
        addActor(imgMain)
        imgMain.setBounds(36f, 902f, 861f, 747f)

        val aBack = Actor()
        val a300  = Actor()
        val a500  = Actor()
        val a1000 = Actor()

        addActors(aBack, a300, a500, a1000)
        aBack.apply {
            setBounds(37f, 1530f, 191f, 112f)
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
        a300.apply {
            setBounds(710f, 1300f, 186f, 124f)
            setOnClickListener(gdxGame.soundUtil) {
                investSum(this, 300, 10)
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
        a500.apply {
            setBounds(710f, 1101f, 186f, 124f)
            setOnClickListener(gdxGame.soundUtil) {
                investSum(this, 500, 20)
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
        a1000.apply {
            setBounds(710f, 902f, 186f, 124f)
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
            gdxGame.soundUtil.apply { play(win) }

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
        } else {
            gdxGame.soundUtil.apply { play(fail) }
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
                300  -> gdxGame.assetsAll._1
                500  -> gdxGame.assetsAll._2
                1000 -> gdxGame.assetsAll._3
                else -> gdxGame.assetsAll._1
            }
        )
        imgInvestWin.animShow(0.25f) {
            imgInvestWin.animDelay(2f) {
                imgInvestWin.animHide(0.25f)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}