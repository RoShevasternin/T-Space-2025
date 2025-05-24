package com.cryonetpoint.ecsporush.game.actors

import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedGroup
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}