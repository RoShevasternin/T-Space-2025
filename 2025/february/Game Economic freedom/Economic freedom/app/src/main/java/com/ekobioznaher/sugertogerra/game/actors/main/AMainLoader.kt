package com.ekobioznaher.sugertogerra.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ekobioznaher.sugertogerra.game.actors.ALogo
import com.ekobioznaher.sugertogerra.game.utils.Block
import com.ekobioznaher.sugertogerra.game.utils.TIME_ANIM_SCREEN
import com.ekobioznaher.sugertogerra.game.utils.actor.animHideSuspend
import com.ekobioznaher.sugertogerra.game.utils.actor.animShowSuspend
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedGroup
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedScreen
import com.ekobioznaher.sugertogerra.game.utils.gdxGame
import com.ekobioznaher.sugertogerra.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    //private val aLogo = ALogo(screen)
    private val imgLogo = Image(gdxGame.assetsLoader.logo)
    private val imgaaa = Image(gdxGame.assetsLoader.aaa)

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
        addActor(imgLogo)
        imgLogo.setBounds(45f, 1001f, 1059f, 498f)

        addActor(imgaaa)
        imgaaa.setBounds(85f, 1150f, 189f, 200f)

        imgaaa.addAction(Actions.forever(
            Actions.sequence(
                Actions.moveBy(-15f, 0f, 0.5f),
                Actions.moveBy(15f, 0f, 0.5f),
            )
        ))
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