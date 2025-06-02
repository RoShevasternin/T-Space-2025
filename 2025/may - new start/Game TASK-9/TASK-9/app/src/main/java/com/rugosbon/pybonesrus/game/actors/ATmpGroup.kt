package com.rugosbon.pybonesrus.game.actors

import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedGroup
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}