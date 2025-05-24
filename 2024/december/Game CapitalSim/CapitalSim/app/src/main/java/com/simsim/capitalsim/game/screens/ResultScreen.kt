package com.simsim.capitalsim.game.screens

import com.simsim.capitalsim.R
import com.simsim.capitalsim.game.actors.main.AMainResult
import com.simsim.capitalsim.game.utils.Block
import com.simsim.capitalsim.game.utils.GameColor
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.advanced.AdvancedStage
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.game.utils.runGDX
import kotlinx.coroutines.launch

class ResultScreen: AdvancedScreen() {

    private val aMainMenu = AMainResult(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        gdxGame.activity.apply {
            setStatusBarColor(R.color.green_0D)
            setNavBarColor(R.color.green_0D)
        }
        gdxGame.backgroundColor = GameColor.green_0D
        addMain()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMainMenu.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMainMenu)
    }

}