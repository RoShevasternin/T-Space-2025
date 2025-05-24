package com.traoya.yatirimya.game.screens

import com.traoya.yatirimya.game.actors.main.AMainGely
import com.traoya.yatirimya.game.actors.main.AMainTutorial
import com.traoya.yatirimya.game.utils.Block
import com.traoya.yatirimya.game.utils.advanced.AdvancedMainScreen
import com.traoya.yatirimya.game.utils.advanced.AdvancedStage
import com.traoya.yatirimya.game.utils.gdxGame
import com.traoya.yatirimya.game.utils.region

class GelyScreen: AdvancedMainScreen() {

    override val aMain = AMainGely(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_GAME.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}