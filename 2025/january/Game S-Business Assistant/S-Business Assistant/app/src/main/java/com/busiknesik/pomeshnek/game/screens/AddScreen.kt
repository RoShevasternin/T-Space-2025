package com.busiknesik.pomeshnek.game.screens

import com.busiknesik.pomeshnek.game.actors.main.AMainAdd
import com.busiknesik.pomeshnek.game.utils.Block
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedStage

class AddScreen: AdvancedScreen() {

    private val aMain = AMainAdd(this)

    override fun AdvancedStage.addActorsOnStageUI() {
//        addAndFillActor(Image(drawerUtil.getTexture(Color.BROWN)))
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}