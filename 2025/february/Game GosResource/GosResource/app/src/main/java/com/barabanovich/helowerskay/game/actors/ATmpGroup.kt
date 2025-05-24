package com.barabanovich.helowerskay.game.actors

import com.barabanovich.helowerskay.game.utils.advanced.AdvancedGroup
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}