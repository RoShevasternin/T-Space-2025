package com.baleru.gamanecpidec.game.actors

import com.baleru.gamanecpidec.game.utils.advanced.AdvancedGroup
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}