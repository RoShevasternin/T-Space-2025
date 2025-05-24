package com.figidnansovich.glamour.game.actors

import com.figidnansovich.glamour.game.utils.advanced.AdvancedGroup
import com.figidnansovich.glamour.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}