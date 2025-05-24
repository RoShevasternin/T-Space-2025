package com.romander.navfenixgater.game.actors

import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}