package com.ekobioznaher.sugertogerra.game.screens

import com.ekobioznaher.sugertogerra.game.actors.main.AMainMenu
import com.ekobioznaher.sugertogerra.game.utils.Block
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedScreen
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedStage
import com.ekobioznaher.sugertogerra.game.utils.gdxGame
import com.ekobioznaher.sugertogerra.game.utils.region
import com.ekobioznaher.sugertogerra.game.utils.runGDX
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