package com.liberator.wisoliter.game.actors.world

import com.liberator.wisoliter.game.actors.AScrollPane
import com.liberator.wisoliter.game.utils.*
import com.liberator.wisoliter.game.utils.actor.setSizeScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen

class AWorldScrollable(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    private val aWorld = AWorld(screen)
    private val scroll = AScrollPane(aWorld)

    var blockSelectCountry: (ACountry) -> Unit = {}
    var blockZahvachenoCountry = {}

    override fun addActorsOnGroup() {
        addScrollPane()
    }

    // Actors -----------------------------------------------------------------------------------

    private fun addScrollPane() {
        addAndFillActor(scroll)

        aWorld.setSizeScaled(sizeScaler, WORLD_WIDTH, WORLD_HEIGHT)
        aWorld.aWorldForeground.also {
            it.blockSelectCountry     = { blockSelectCountry(it) }
            it.blockZahvachenoCountry = { blockZahvachenoCountry() }
        }
    }

    // Logic -------------------------------------------------------------------------------------

    fun scrollToPos() {
        runGDX {
            scroll.setSmoothScrolling(true)
            scroll.scrollTo(5650f.scaled, 1600f.scaled, 0f, 0f)
        }
    }

    fun tutorial() {
        aWorld.aWorldForeground.tutorial()
    }

}