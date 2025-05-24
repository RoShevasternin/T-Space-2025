package com.smarters.moneyes.game.actors.scroller

import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.actors.checkbox.ACheckBox
import com.smarters.moneyes.game.actors.checkbox.ACheckBoxGroup

class APoints(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val cbg     = ACheckBoxGroup()
    private val listBox = List(3) { ACheckBox(screen, ACheckBox.Type.POINT) }

    var blockCheck: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        var nx = 0f

        listBox.onEachIndexed { index, box ->
            box.checkBoxGroup = cbg
            addActor(box)
            box.setBounds(nx, 0f, 24f, 25f)
            nx += 24 + 33

            //box.setOnCheckListener { isCheck ->  if (isCheck) blockCheck(index) }
        }

        listBox.first().check(false)

    }

    // Logic ------------------------------------------------------------------

    fun check(index: Int) {
        listBox[index].check(false)
    }

}