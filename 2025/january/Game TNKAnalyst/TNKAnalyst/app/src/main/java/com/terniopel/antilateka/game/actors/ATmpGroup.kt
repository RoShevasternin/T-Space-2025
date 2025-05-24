package com.terniopel.antilateka.game.actors

import com.terniopel.antilateka.game.utils.advanced.AdvancedGroup
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}