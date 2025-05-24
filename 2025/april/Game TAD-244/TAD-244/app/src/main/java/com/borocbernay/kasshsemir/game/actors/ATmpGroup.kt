package com.borocbernay.kasshsemir.game.actors

import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedGroup
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}