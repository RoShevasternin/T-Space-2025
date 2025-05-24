package com.liberator.wisoliter.game.actors

import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}