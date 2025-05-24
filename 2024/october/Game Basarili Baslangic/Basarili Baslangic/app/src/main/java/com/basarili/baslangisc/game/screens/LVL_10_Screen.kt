package com.basarili.baslangisc.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.basarili.baslangisc.game.LibGDXGame
import com.basarili.baslangisc.game.actors.*
import com.basarili.baslangisc.game.utils.TIME_ANIM
import com.basarili.baslangisc.game.utils.WIDTH_UI
import com.basarili.baslangisc.game.utils.actor.animHide
import com.basarili.baslangisc.game.utils.actor.animShow
import com.basarili.baslangisc.game.utils.actor.setOnClickListenerWithPos
import com.basarili.baslangisc.game.utils.actor.setPosition
import com.basarili.baslangisc.game.utils.advanced.AdvancedStage
import com.basarili.baslangisc.game.utils.disable
import com.basarili.baslangisc.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LVL_10_Screen(override val game: LibGDXGame) : AbstractLVLScreen() {

    override val valueClick: Int = 10
    override val valueLVL  : Int = 10

    // Actors
    private val imgBuilding1  = ABuild_1(this)
    private val imgBuilding2  = ABuild_1(this)
    private val item3         = Image(game.all.building_2)
    private val item4         = Image(game.all.building_2)
    private val item5         = Image(game.all.building_3)
    private val item6         = Image(game.all.building_3)
    private val item7         = Image(game.all.building_4)
    private val item8         = Image(game.all.building_4)
    private val item9         = Image(game.all.building_5)
    private val item10         = Image(game.all.building_6)

    override fun AdvancedStage.addActorsOnStageUI_2() {
        addImgBuildings()
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addImgBuildings() {
        val list = listOf(item3, item4, item5, item6, item7, item8, item9, item10, imgBuilding1, imgBuilding2,)
        addActors(list)
        list.onEach { it.disable() }

        imgBuilding1.setBounds(665f, 1128f, 349f, 389f)
        imgBuilding2.setBounds(387f, 972f, 349f, 389f)
        item3.setBounds(16f, 1345f, 206f, 154f)
        item4.setBounds(219f, 1448f, 206f, 154f)
        item5.setBounds(205f, 1667f, 167f, 203f)
        item6.setBounds(77f, 1595f, 167f, 203f)
        item7.setBounds(683f, 1577f, 110f, 154f)
        item8.setBounds(600f, 1537f, 110f, 154f)
        item9.setBounds(705f, 1518f, 126f, 271f)
        item10.setBounds(233f, 1139f, 490f, 736f)
    }

}