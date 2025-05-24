package com.ingiodin.strinvestida.game.actors

import com.ingiodin.strinvestida.game.utils.advanced.AdvancedGroup
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}