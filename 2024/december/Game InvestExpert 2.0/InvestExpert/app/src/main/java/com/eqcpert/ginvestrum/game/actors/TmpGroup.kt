package com.eqcpert.ginvestrum.game.actors

import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedGroup
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}