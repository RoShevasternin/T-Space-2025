package com.borubashka.arsemajeg.game.actors

import com.borubashka.arsemajeg.game.utils.advanced.AdvancedGroup
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}