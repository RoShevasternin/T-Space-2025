package com.smarteca.foundsender.game.actors

import com.smarteca.foundsender.game.utils.advanced.AdvancedGroup
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}
