package com.inveanst.litka.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.inveanst.litka.game.utils.GColor
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen

class ABackground(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgHeader = Image(screen.drawerUtil.getRegion(GColor.green))
    private val imgMain   = Image(screen.drawerUtil.getRegion(GColor.background))
    private val imgLogo   = Image(screen.game.splash.logo)

    override fun addActorsOnGroup() {
        addActors(imgMain, imgLogo)
        imgMain.setBounds(0f,0f,width,684f)
        imgLogo.setBounds(137f,700f,100f,62f)

        screen.stageBack.addActor(imgHeader)
        imgHeader.setBounds(0f,684f * screen.coffHeight, screen.stageBack.viewport.worldWidth,200f * screen.coffHeight)
    }

}