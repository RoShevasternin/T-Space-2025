package com.easyru.track.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.easyru.track.game.actors.ALogo
import com.easyru.track.game.actors.progress.AProgressLoader
import com.easyru.track.game.utils.Block
import com.easyru.track.game.utils.TIME_ANIM_SCREEN
import com.easyru.track.game.utils.actor.animHideSuspend
import com.easyru.track.game.utils.actor.animShowSuspend
import com.easyru.track.game.utils.advanced.AdvancedGroup
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.gdxGame
import com.easyru.track.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgProgressBack = Image(gdxGame.assetsLoader.progress_back)
    private val logo            = ALogo(screen)

    val progress = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addLogo()
                addImgProgressBack()
                addProgress()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLogo() {
        addActor(logo)
        logo.setBounds(295f, 903f, 213f, 209f)
    }

    private fun addImgProgressBack() {
        addActor(imgProgressBack)
        imgProgressBack.setBounds(135f, 711f, 535f, 120f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(135f, 711f, 535f, 21f)
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