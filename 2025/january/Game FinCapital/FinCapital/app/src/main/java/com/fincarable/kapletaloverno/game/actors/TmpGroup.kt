package com.fincarable.kapletaloverno.game.actors

import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedGroup
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}