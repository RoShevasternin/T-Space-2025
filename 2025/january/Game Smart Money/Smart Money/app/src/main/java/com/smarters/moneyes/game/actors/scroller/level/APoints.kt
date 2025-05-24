package com.smarters.moneyes.game.actors.scroller.level

import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.actors.checkbox.ACheckBox
import com.smarters.moneyes.game.actors.checkbox.ACheckBoxGroup

class APoints(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val cbg     = ACheckBoxGroup()
    private val listBox = List(2) { ACheckBox(screen, ACheckBox.Type.POINT) }

    override fun addActorsOnGroup() {
        var nx = 0f

        listBox.onEachIndexed { index, box ->
            box.checkBoxGroup = cbg
            addActor(box)
            box.setBounds(nx, 0f, 25f, 25f)
            nx += 25 + 32
        }

        listBox.first().check(false)

    }

    // Logic ------------------------------------------------------------------

    fun check(index: Int) {
        listBox[index].check(false)
    }

}