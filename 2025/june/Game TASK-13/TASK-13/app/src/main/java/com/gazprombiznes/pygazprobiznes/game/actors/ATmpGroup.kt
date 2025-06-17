package com.gazprombiznes.pygazprobiznes.game.actors

import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedGroup
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}