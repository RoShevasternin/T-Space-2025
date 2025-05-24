package com.basarili.baslangisc.game.actors

import com.basarili.baslangisc.game.utils.advanced.AdvancedGroup
import com.basarili.baslangisc.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}