package com.kaskazer.kazmuchero.game.screens

import com.kaskazer.kazmuchero.game.LibGDXGame
import com.kaskazer.kazmuchero.game.actors.*
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedStage
import com.kaskazer.kazmuchero.game.utils.disable

class LVL_2_Screen(override val game: LibGDXGame) : AbstractLVLScreen() {

    override val valueClick: Int = 2
    override val valueLVL  : Int = 2

    // Actors
    private val imgBuilding1  = ABuild_1(this)
    private val imgBuilding2  = ABuild_1(this)

    override fun AdvancedStage.addActorsOnStageUI_2() {
        addImgBuildings()
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addImgBuildings() {
        val list = listOf(imgBuilding1, imgBuilding2)
        addActors(list)
        list.onEach { it.disable() }

        imgBuilding1.setBounds(665f, 1128f, 349f, 389f)
        imgBuilding2.setBounds(387f, 972f, 349f, 389f)
    }

}