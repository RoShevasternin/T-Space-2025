package com.frusune.abvger.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen

class AHeader(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgPanel = Image(screen.game.all.header)
    private val imgLogo  = Image(screen.game.all.logo)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActor(imgLogo)
        imgLogo.setBounds(272f,38f,207f,126f)
    }

}