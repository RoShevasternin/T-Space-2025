package com.traoya.yatirimya.game.actors

import com.traoya.yatirimya.game.utils.advanced.AdvancedGroup
import com.traoya.yatirimya.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}