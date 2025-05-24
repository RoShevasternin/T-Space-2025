package com.kaskazer.kazmuchero.game.actors

import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedGroup
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}