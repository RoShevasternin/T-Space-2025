package com.sberigatelny.finexpertaizer.game.actors

import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedGroup
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}