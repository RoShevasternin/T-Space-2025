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

class LVL_3_Screen(override val game: LibGDXGame) : AbstractLVLScreen() {

    override val valueClick: Int = 3
    override val valueLVL  : Int = 3

    // Actors
    private val imgBuilding1  = ABuild_1(this)
    private val imgBuilding2  = ABuild_1(this)
    private val item3         = Image(game.all.building_2)

    override fun AdvancedStage.addActorsOnStageUI_2() {
        addImgBuildings()
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addImgBuildings() {
        val list = listOf(imgBuilding1, imgBuilding2, item3)
        addActors(list)
        list.onEach { it.disable() }

        imgBuilding1.setBounds(665f, 1128f, 349f, 389f)
        imgBuilding2.setBounds(387f, 972f, 349f, 389f)
        item3.setBounds(16f, 1345f, 206f, 154f)
    }

}