package com.kaskazer.kazmuchero.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.kaskazer.kazmuchero.game.LibGDXGame
import com.kaskazer.kazmuchero.game.actors.*
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedStage
import com.kaskazer.kazmuchero.game.utils.disable

class LVL_6_Screen(override val game: LibGDXGame) : AbstractLVLScreen() {

    override val valueClick: Int = 6
    override val valueLVL  : Int = 6

    // Actors
    private val imgBuilding1  = ABuild_1(this)
    private val imgBuilding2  = ABuild_1(this)
    private val item3         = Image(game.all.building_2)
    private val item4         = Image(game.all.building_2)
    private val item5         = Image(game.all.building_3)
    private val item6         = Image(game.all.building_3)

    override fun AdvancedStage.addActorsOnStageUI_2() {
        addImgBuildings()
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addImgBuildings() {
        val list = listOf(imgBuilding1, imgBuilding2, item3, item4, item5, item6)
        addActors(list)
        list.onEach { it.disable() }

        imgBuilding1.setBounds(665f, 1128f, 349f, 389f)
        imgBuilding2.setBounds(387f, 972f, 349f, 389f)
        item3.setBounds(16f, 1345f, 206f, 154f)
        item4.setBounds(219f, 1448f, 206f, 154f)
        item5.setBounds(205f, 1667f, 167f, 203f)
        item6.setBounds(77f, 1595f, 167f, 203f)
    }

}