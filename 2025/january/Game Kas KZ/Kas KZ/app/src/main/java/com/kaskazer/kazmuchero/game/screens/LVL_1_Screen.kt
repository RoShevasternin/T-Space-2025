package com.kaskazer.kazmuchero.game.screens

import com.kaskazer.kazmuchero.game.LibGDXGame
import com.kaskazer.kazmuchero.game.actors.*
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedStage
import com.kaskazer.kazmuchero.game.utils.disable

class LVL_1_Screen(override val game: LibGDXGame) : AbstractLVLScreen() {

    override val valueClick: Int = 1
    override val valueLVL  : Int = 1

    // Actors
    private val imgBuilding  = ABuild_1(this)

    override fun AdvancedStage.addActorsOnStageUI_2() {
        addImgBuilding()
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addImgBuilding() {
        addActor(imgBuilding)
        imgBuilding.setBounds(387f, 972f, 349f, 389f)
        imgBuilding.disable()
    }

}