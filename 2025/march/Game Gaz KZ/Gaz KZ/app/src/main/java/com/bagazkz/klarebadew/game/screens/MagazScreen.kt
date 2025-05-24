package com.bagazkz.klarebadew.game.screens

import com.bagazkz.klarebadew.game.actors.main.AMainGreating
import com.bagazkz.klarebadew.game.actors.main.AMainMagaz
import com.bagazkz.klarebadew.game.actors.main.AMainMenu
import com.bagazkz.klarebadew.game.utils.Block
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedMainScreen
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedStage
import com.bagazkz.klarebadew.game.utils.gdxGame
import com.bagazkz.klarebadew.game.utils.region

class MagazScreen: AdvancedMainScreen() {

    override val aMain = AMainMagaz(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsLoader.BACKDA.region)
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