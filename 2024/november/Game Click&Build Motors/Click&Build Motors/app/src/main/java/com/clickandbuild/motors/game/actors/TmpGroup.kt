package com.clickandbuild.motors.game.actors

import com.clickandbuild.motors.game.utils.advanced.AdvancedGroup
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}