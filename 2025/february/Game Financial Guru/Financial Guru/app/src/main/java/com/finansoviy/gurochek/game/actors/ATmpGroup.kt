package com.finansoviy.gurochek.game.actors

import com.finansoviy.gurochek.game.utils.advanced.AdvancedGroup
import com.finansoviy.gurochek.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}