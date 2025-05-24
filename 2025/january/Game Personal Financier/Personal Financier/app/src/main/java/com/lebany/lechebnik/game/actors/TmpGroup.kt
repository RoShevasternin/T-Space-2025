package com.lebany.lechebnik.game.actors

import com.lebany.lechebnik.game.utils.advanced.AdvancedGroup
import com.lebany.lechebnik.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}