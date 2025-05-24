package com.liberator.wisoliter.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.liberator.wisoliter.game.actors.AGrid
import com.liberator.wisoliter.game.actors.progress.AProgressLoader
import com.liberator.wisoliter.game.utils.Block
import com.liberator.wisoliter.game.utils.TIME_ANIM_SCREEN
import com.liberator.wisoliter.game.utils.actor.animHideSuspend
import com.liberator.wisoliter.game.utils.actor.animShowSuspend
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.game.utils.runGDX
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
        imgLogo.setBounds(78f, 597f, 622f, 491f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(85f, 603f, 608f, 42f)
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