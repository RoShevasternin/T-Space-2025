package com.funkun.kylkan.game.actors

import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}