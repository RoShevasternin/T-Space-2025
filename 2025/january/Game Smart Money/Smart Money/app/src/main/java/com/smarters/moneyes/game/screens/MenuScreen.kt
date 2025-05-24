package com.smarters.moneyes.game.screens

import com.smarters.moneyes.game.actors.main.AMainMenu
import com.smarters.moneyes.game.utils.Block
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.advanced.AdvancedStage
import com.smarters.moneyes.game.utils.gdxGame
import com.smarters.moneyes.game.utils.region
import com.smarters.moneyes.game.utils.runGDX
import kotlinx.coroutines.launch

class MenuScreen: AdvancedScreen() {

    companion object {
        var SELECTED_LEVEL_INDEX = 0
    }

    private val aMain = AMainMenu(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.Back.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
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