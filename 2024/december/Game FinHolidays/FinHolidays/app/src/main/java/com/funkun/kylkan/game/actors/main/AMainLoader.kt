package com.funkun.kylkan.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.funkun.kylkan.game.actors.progress.AProgressLoader
import com.funkun.kylkan.game.utils.Block
import com.funkun.kylkan.game.utils.TIME_ANIM_SCREEN
import com.funkun.kylkan.game.utils.actor.animHideSuspend
import com.funkun.kylkan.game.utils.actor.animShowSuspend
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgLogo = Image(gdxGame.assetsLoader.logo)

    val progress = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addLogo()
                addProgress()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(67f, 877f, 877f, 439f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(168f, 1014f, 674f, 27f)
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