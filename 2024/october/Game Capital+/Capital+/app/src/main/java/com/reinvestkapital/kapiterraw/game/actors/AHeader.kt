package com.reinvestkapital.kapiterraw.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.reinvestkapital.kapiterraw.game.utils.advanced.AdvancedGroup
import com.reinvestkapital.kapiterraw.game.utils.advanced.AdvancedScreen

class AHeader(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgPanel = Image(screen.game.all.header)
    private val imgLogo  = Image(screen.game.all.logo)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActor(imgLogo)
        imgLogo.setBounds(189f,75f,372f,51f)
    }

}