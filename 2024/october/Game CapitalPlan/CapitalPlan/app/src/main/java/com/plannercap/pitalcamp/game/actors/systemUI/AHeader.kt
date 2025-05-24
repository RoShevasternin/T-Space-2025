package com.plannercap.pitalcamp.game.actors.systemUI

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plannercap.pitalcamp.game.utils.GColor
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedGroup
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedScreen

class AHeader(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgHeader = Image(screen.drawerUtil.getRegion(GColor.green))
    private val imgLogo   = Image(screen.game.all.logo)

    override fun addActorsOnGroup() {
        screen.stageBack.addActor(imgHeader)
        imgHeader.setBounds(0f,y / screen.scalerY, screen.viewportBack.worldWidth,screen.viewportBack.worldHeight)

        addActor(imgLogo)
        imgLogo.setBounds(237f,33f,354f,96f)
    }

}

