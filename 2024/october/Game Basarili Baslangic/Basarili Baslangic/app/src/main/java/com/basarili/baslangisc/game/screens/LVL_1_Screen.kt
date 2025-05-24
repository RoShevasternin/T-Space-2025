package com.basarili.baslangisc.game.screens

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