package com.liberator.wisoliter.game.actors.world

import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen

class AWorld(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        return height
    }

    val aWorldBackground = AWorldBackground(screen)
    val aWorldForeground = AWorldForeground(screen)

    override fun addActorsOnGroup() {
        addWorldBackground()
        addWorldForeground()
    }

    // Actors -----------------------------------------------------------------------------------

    private fun addWorldBackground() {
        addAndFillActor(aWorldBackground)
    }

    private fun addWorldForeground() {
        addAndFillActor(aWorldForeground)
    }

}