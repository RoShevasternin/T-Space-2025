package com.simsim.capitalsim.game.screens

import com.simsim.capitalsim.R
import com.simsim.capitalsim.game.actors.main.AMainOnboarding
import com.simsim.capitalsim.game.utils.Block
import com.simsim.capitalsim.game.utils.GameColor
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.advanced.AdvancedStage
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.game.utils.runGDX
import kotlinx.coroutines.launch

class OnboardingScreen: AdvancedScreen() {

    private val aMain = AMainOnboarding(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        gdxGame.activity.apply {
            setStatusBarColor(R.color.white)
            setNavBarColor(R.color.white)
        }
        gdxGame.backgroundColor = GameColor.white
        addMain()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}