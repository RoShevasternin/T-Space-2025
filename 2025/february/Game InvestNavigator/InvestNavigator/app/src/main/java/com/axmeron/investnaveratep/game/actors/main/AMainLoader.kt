package com.axmeron.investnaveratep.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.axmeron.investnaveratep.game.actors.ALogo
import com.axmeron.investnaveratep.game.actors.progress.AProgressLoader
import com.axmeron.investnaveratep.game.utils.Block
import com.axmeron.investnaveratep.game.utils.TIME_ANIM_SCREEN
import com.axmeron.investnaveratep.game.utils.actor.animHideSuspend
import com.axmeron.investnaveratep.game.utils.actor.animShowSuspend
import com.axmeron.investnaveratep.game.utils.actor.setOrigin
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.runGDX
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgProgressBack = Image(gdxGame.assetsLoader.progress_back)
    private val logo            = Image(gdxGame.assetsLoader.logo) //ALogo(screen)
    private val gear            = Image(gdxGame.assetsLoader.gear) //ALogo(screen)

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
        logo.setBounds(308f, 881f, 364f, 134f)

        addActor(gear)
        gear.setBounds(133f, 875f, 158f, 158f)
        gear.setOrigin(Align.center or Align.right)

        gear.addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-0.2f, -0.2f, 0.3f),
            Actions.scaleTo(1f, 1f, 0.3f),
        )))
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