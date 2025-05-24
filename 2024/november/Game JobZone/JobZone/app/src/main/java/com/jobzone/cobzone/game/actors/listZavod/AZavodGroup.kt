package com.jobzone.cobzone.game.actors.listZavod

import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen

class AZavodGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        return height
    }

    var blockClick: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        var ny = 4180f
        List(10) { id ->
            AZavod(screen, id).also { zavod ->
                addActor(zavod)
                zavod.setBounds(0f, ny, 731f, 446f)
                ny -= (18 + 446)
                zavod.blockClick = { blockClick(id) }
            }
        }
    }

}