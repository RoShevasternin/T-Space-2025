package com.padrestoranom.easypaydonalds.game.actors

import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGroup
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}