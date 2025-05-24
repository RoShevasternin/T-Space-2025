package com.tinkf.tnk.game.actors

import com.tinkf.tnk.game.utils.advanced.AdvancedGroup
import com.tinkf.tnk.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}