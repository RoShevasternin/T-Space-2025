package com.cinvestor.crotcevni.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.cinvestor.crotcevni.game.actors.progress
import com.cinvestor.crotcevni.game.screens.ImproveScreen
import com.cinvestor.crotcevni.game.screens.MenuScreen
import com.cinvestor.crotcevni.game.utils.Block
import com.cinvestor.crotcevni.game.utils.TIME_ANIM_SCREEN
import com.cinvestor.crotcevni.game.utils.actor.PosSize
import com.cinvestor.crotcevni.game.utils.actor.animDelay
import com.cinvestor.crotcevni.game.utils.actor.animHide
import com.cinvestor.crotcevni.game.utils.actor.animShow
import com.cinvestor.crotcevni.game.utils.actor.setBounds
import com.cinvestor.crotcevni.game.utils.actor.setOnClickListener
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedMainGroup
import com.cinvestor.crotcevni.game.utils.gdxGame
import kotlin.math.cos

class AMainImprove(
    override val screen: ImproveScreen,
): AdvancedMainGroup() {

    private val imgMain = Image(gdxGame.assetsAll.IMPROVE)

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addALevel()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALevel() {
        addActor(imgMain)
        imgMain.setBounds(36f, 640f, 861f, 1009f)

        val aBack = Actor()
        val a100  = Actor()
        val a200  = Actor()
        val a300  = Actor()
        val a400  = Actor()

        addActors(aBack, a100, a200, a300, a400)
        aBack.apply {
            setBounds(37f, 1530f, 191f, 112f)
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
        a100.apply {
            setBounds(710f, 1282f, 186f, 124f)
            setOnClickListener(gdxGame.soundUtil) {
                buyLevel(100)
            }
        }
        a200.apply {
            setBounds(710f, 1055f, 186f, 124f)
            setOnClickListener(gdxGame.soundUtil) {
                buyLevel(200)
            }
        }
        a300.apply {
            setBounds(710f, 843f, 186f, 124f)
            setOnClickListener(gdxGame.soundUtil) {
                buyLevel(300)
            }
        }
        a400.apply {
            setBounds(710f, 640f, 186f, 124f)
            setOnClickListener(gdxGame.soundUtil) {
                buyLevel(400)
            }
        }
    }

    private fun buyLevel(cost: Int) {
        if (gdxGame.ds_Balance.flow.value - cost >= 0) {
            // sound Buy
            gdxGame.soundUtil.apply { play(win) }

            gdxGame.ds_Balance.update { it - cost }
            progress?.progressPercentFlow?.value = 100f

            if (gdxGame.ds_Level.flow.value + 1 <= 4) {
                gdxGame.ds_Level.update { it + 1 }
            }
        } else {
            // sound Fail
            gdxGame.soundUtil.apply { play(fail) }
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