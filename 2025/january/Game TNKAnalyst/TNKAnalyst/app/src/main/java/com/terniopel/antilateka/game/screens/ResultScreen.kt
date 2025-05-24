package com.terniopel.antilateka.game.screens

import com.terniopel.antilateka.R
import com.terniopel.antilateka.game.actors.main.AMainResult
import com.terniopel.antilateka.game.utils.Block
import com.terniopel.antilateka.game.utils.GameColor
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen
import com.terniopel.antilateka.game.utils.advanced.AdvancedStage
import com.terniopel.antilateka.game.utils.gdxGame
import com.terniopel.antilateka.game.utils.runGDX
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