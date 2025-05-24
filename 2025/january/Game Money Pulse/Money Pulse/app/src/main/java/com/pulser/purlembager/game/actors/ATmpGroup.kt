package com.pulser.purlembager.game.actors

import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}