package com.jobzone.cobzone.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jobzone.cobzone.game.actors.AProgress
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.TIME_ANIM_SCREEN
import com.jobzone.cobzone.game.utils.actor.animHideSuspend
import com.jobzone.cobzone.game.utils.actor.animShowSuspend
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgBrend = Image(screen.game.assetsLoader.brend)
    private val imgLoad  = Image(screen.game.assetsLoader.load)

    val progress = AProgress(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addBrend()
                addLoad()
                addProgress()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addBrend() {
        addActors(imgBrend)
        imgBrend.setBounds(99f, 628f, 531f, 604f)
    }

    private fun addLoad() {
        addActors(imgLoad)
        imgLoad.setBounds(273f, 349f, 168f, 37f)
    }

    private fun addProgress() {
        addActors(progress)
        progress.setBounds(82f, 421f, 568f, 52f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block { }) {
        animHideSuspend(TIME_ANIM_SCREEN)
        block.invoke()
    }

}