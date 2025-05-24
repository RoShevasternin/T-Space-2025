package com.busiknesik.pomeshnek.game.actors

import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedGroup
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}