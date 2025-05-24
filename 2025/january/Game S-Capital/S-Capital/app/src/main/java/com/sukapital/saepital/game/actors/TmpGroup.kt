package com.sukapital.saepital.game.actors

import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}