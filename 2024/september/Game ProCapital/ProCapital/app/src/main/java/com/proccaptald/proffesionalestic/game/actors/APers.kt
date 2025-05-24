package com.proccaptald.proffesionalestic.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen

class APers(
    override val screen: AdvancedScreen,
    private val region: TextureRegion,
) : AdvancedGroup() {

    var step = 0

    override fun addActorsOnGroup() {
        addActors(Image(region))
    }

}