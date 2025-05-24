package com.smarters.moneyes.game.actors.main

import com.smarters.moneyes.game.actors.ALogo
import com.smarters.moneyes.game.utils.Block
import com.smarters.moneyes.game.utils.TIME_ANIM_SCREEN
import com.smarters.moneyes.game.utils.actor.animHideSuspend
import com.smarters.moneyes.game.utils.actor.animShowSuspend
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aLogo = ALogo(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addLogo()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLogo() {
        addActor(aLogo)
        aLogo.setBounds(422f, 1020f, 306f, 448f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animShowSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

}