package com.gpecspertisa.sollomka.game.actors

import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedGroup
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}