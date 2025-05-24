package com.simsim.capitalsim.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.simsim.capitalsim.game.actors.ASim
import com.simsim.capitalsim.game.actors.checkbox.ACheckBoxGroup
import com.simsim.capitalsim.game.utils.Block
import com.simsim.capitalsim.game.utils.TIME_ANIM_SCREEN
import com.simsim.capitalsim.game.utils.actor.animHideSuspend
import com.simsim.capitalsim.game.utils.actor.animShowSuspend
import com.simsim.capitalsim.game.utils.advanced.AdvancedGroup
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainSim(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgProgress = Image(gdxGame.assetsAll.progress)
    private val imgPod      = Image(gdxGame.assetsAll.podilka)

    private val aSim = ASim(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addProgress()
                addASim()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addProgress() {
        addActors(imgProgress, imgPod)
        imgProgress.setBounds(53f, 1972f, 899f, 17f)
        imgPod.setBounds(53f, 1972f, 121f, 17f)

        aSim.blockNext = {
            imgPod.addAction(Actions.moveBy(129f, 0f, TIME_ANIM_SCREEN, Interpolation.sineIn))
        }
    }

    private fun addASim() {
        addActor(aSim)
        aSim.setBounds(53f, 376f, 898f, 1534f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Anim ------------------------------------------------


}