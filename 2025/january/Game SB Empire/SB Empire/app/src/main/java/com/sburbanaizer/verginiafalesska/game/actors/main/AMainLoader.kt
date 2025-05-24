package com.sburbanaizer.verginiafalesska.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sburbanaizer.verginiafalesska.game.actors.AProgress
import com.sburbanaizer.verginiafalesska.game.utils.Block
import com.sburbanaizer.verginiafalesska.game.utils.TIME_ANIM_SCREEN
import com.sburbanaizer.verginiafalesska.game.utils.actor.animHide
import com.sburbanaizer.verginiafalesska.game.utils.actor.animShow
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedGroup
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedScreen
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame
import com.sburbanaizer.verginiafalesska.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val logo  = Image(gdxGame.assetsLoader.logo)
    val progress      = AProgress(screen)


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
        addActor(logo)
        logo.setBounds(43f, 463f, 628f, 529f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(119f, 595f, 475f, 19f)
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        animShow(TIME_ANIM_SCREEN)
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) { block.invoke() }
    }

}