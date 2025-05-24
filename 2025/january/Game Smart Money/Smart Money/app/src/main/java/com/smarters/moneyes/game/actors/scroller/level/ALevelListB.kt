package com.smarters.moneyes.game.actors.scroller.level

import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen

class ALevelListB(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listLevel = List(6) { ALevel(screen, it + 6, cbg) }

    var blockSelect: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        var nx = 0f
        var ny = 411f
        listLevel.onEachIndexed { index, level ->
            addActor(level)
            level.setBounds(nx, ny, 314f, 366f)
            level.blockSelect = { blockSelect(it) }

            nx += 314 + 44
            if (index.inc() % 3 == 0) {
                nx = 0f
                ny -= 366 + 45
            }

            if (com.smarters.moneyes.game.actors.main.listLevel[index + 6]) {
                level.CHECK()
            }
        }
    }

}