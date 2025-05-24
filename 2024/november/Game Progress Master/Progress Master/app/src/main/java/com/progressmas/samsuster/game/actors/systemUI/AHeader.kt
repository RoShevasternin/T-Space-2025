package com.progressmas.samsuster.game.actors.systemUI

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.progressmas.samsuster.game.utils.advanced.AdvancedGroup
import com.progressmas.samsuster.game.utils.advanced.AdvancedScreen

class AHeader(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgHeader = Image(screen.game.all._9_panel)

    override fun addActorsOnGroup() {
        setBounds(0f, y / screen.scalerY, screen.stageBack.width, screen.stageBack.height)
        addAndFillActor(imgHeader)
    }

}

