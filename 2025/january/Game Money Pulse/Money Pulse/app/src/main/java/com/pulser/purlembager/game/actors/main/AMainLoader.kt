package com.pulser.purlembager.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.actors.ALogo
import com.pulser.purlembager.game.utils.Block
import com.pulser.purlembager.game.utils.TIME_ANIM_SCREEN
import com.pulser.purlembager.game.utils.actor.animHideSuspend
import com.pulser.purlembager.game.utils.actor.animShowSuspend
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame
import com.pulser.purlembager.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aLogo  = ALogo(screen)
    private val aTitle = Image(gdxGame.assetsLoader.logo)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addLogo()
                addTitle()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLogo() {
        addActor(aLogo)
        aLogo.setBounds(238f, 1241f, 235f, 221f)
    }

    private fun addTitle() {
        addActor(aTitle)
        aTitle.setBounds(502f, 1260f, 455f, 169f)
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