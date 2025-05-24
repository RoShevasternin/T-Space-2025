package com.eqcpert.ginvestrum.game.actors.systemUI

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedGroup
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedScreen

class AHeader(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgHeader = Image(screen.game.all._9_panel)

    override fun addActorsOnGroup() {
        setBounds(0f, y / screen.scalerY, screen.stageBack.width, screen.stageBack.height)
        addAndFillActor(imgHeader)
    }

}

