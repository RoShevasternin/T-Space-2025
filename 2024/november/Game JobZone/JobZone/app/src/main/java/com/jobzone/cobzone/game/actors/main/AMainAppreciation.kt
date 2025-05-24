package com.jobzone.cobzone.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jobzone.cobzone.game.actors.AListki
import com.jobzone.cobzone.game.actors.ANotActive
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.TIME_ANIM_SCREEN
import com.jobzone.cobzone.game.utils.actor.animHideSuspend
import com.jobzone.cobzone.game.utils.actor.animShowSuspend
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainAppreciation(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgTitle = Image(screen.game.assetsAll.thanks)
    private val anim     = AListki(screen)

    override fun addActorsOnGroup() {

        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgTitle()
                addAnim()
            }

            animShowMain()
            runGDX { anim.startAnim() }
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(19f, 887f, 693f, 328f)
    }

    private fun addAnim() {
        addActor(anim)
        anim.setBounds(-4f, -15f, 520f, 786f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            animShowSuspend(TIME_ANIM_SCREEN)
        }
    }

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

}