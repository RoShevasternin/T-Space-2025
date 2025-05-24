package com.jobzone.cobzone.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.TIME_ANIM_SCREEN
import com.jobzone.cobzone.game.utils.actor.animHideSuspend
import com.jobzone.cobzone.game.utils.actor.animMoveToSuspend
import com.jobzone.cobzone.game.utils.actor.animScaleToSuspend
import com.jobzone.cobzone.game.utils.actor.animShowSuspend
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainGreeting(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgEasy = Image(screen.game.assetsAll.easy)
    private val imgRrr  = Image(screen.game.assetsAll.rrr)
    private val imgLupa = Image(screen.game.assetsAll.lupa)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addImgEasy()
                addImgLupa()
                addImgRrr()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgEasy() {
        addActor(imgEasy)
        imgEasy.setBounds(37f, 884f, 657f, 462f)
        imgEasy.setOrigin(Align.center)
        imgEasy.setScale(0f)
    }

    private fun addImgLupa() {
        addActor(imgLupa)
        imgLupa.setBounds(-2f, -870f, 733f, 868f)
        imgLupa.color.a = 0f
    }

    private fun addImgRrr() {
        addActor(imgRrr)
        imgRrr.setBounds(460f, 594f, 153f, 153f)
        imgRrr.setOrigin(Align.center)
        imgRrr.setScale(0f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            launch { imgEasy.animScaleToSuspend(1f, TIME_ANIM_SCREEN, Interpolation.sineOut) }

            launch { imgLupa.animShowSuspend(TIME_ANIM_SCREEN) }
            launch { imgLupa.animMoveToSuspend(-2f, 0f, TIME_ANIM_SCREEN, Interpolation.sineOut) }
        }

        animForever_Rrr()
    }

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Anim -------------------------------------------------------

    private fun animForever_Rrr() {
        runGDX {
            imgRrr.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(1f, 1f, 0.5f),
                Actions.scaleTo(0.5f, 0.5f, 0.5f),
            )))
        }
    }

}