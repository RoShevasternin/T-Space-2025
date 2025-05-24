package com.cburaktev.klavregasd.game.actors

import com.cburaktev.klavregasd.game.utils.advanced.AdvancedGroup
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}