package com.smarteca.foundsender.game.screens

import com.smarteca.foundsender.ConfigState
import com.smarteca.foundsender.GLOBAL_ConfigState
import com.smarteca.foundsender.game.actors.main.AMainLoader
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen
import com.smarteca.foundsender.game.utils.advanced.AdvancedStage

class LoaderScreen2 : AdvancedScreen() {

    private var isFinishProgress = true

    private val aMain by lazy { AMainLoader(this) }

    override fun show() {
        super.show()
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
    }

    override fun render(delta: Float) {
        super.render(delta)
        isFinish()
    }

    override fun hideScreen(block: Runnable) {
        aMain.animHide(TIME_ANIM_SCREEN) {
            block.run()
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        aMain.setPercent(100f)
        addMain()
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun isFinish() {
        if (isFinishProgress) {
            when(GLOBAL_ConfigState) {
                ConfigState.ToGame -> {
                    isFinishProgress = false
                    navTo(DashboardScreen::class.java.name)
                }
                else -> {}
            }
        }
    }

    private fun navTo(screenName: String) {
        hideScreen {
            gdxGame.navigationManager.navigate(screenName)
        }
    }


}
