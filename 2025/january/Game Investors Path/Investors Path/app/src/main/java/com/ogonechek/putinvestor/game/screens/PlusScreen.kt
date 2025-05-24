package com.ogonechek.putinvestor.game.screens

import com.ogonechek.putinvestor.game.actors.main.AMainPlus
import com.ogonechek.putinvestor.game.utils.Block
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedStage

class PlusScreen: AdvancedScreen() {

    private val aMain = AMainPlus(this)

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