package com.progressmas.samsuster.game.actors

import com.progressmas.samsuster.game.utils.advanced.AdvancedGroup
import com.progressmas.samsuster.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}