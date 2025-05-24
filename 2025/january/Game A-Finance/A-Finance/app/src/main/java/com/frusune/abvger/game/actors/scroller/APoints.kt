package com.frusune.abvger.game.actors.scroller

import com.frusune.abvger.game.actors.checkbox.ACheckBox
import com.frusune.abvger.game.actors.checkbox.ACheckBoxGroup
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen

class APoints(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val cbg     = ACheckBoxGroup()
    private val listBox = List(3) { ACheckBox(screen, ACheckBox.Static.Type.POINT) }

    var blockCheck: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        var nx = 0f

        listBox.onEachIndexed { index, box ->
            box.checkBoxGroup = cbg
            addActor(box)
            box.setBounds(nx,0f,31f,31f)
            nx += 31+31

            box.setOnCheckListener { isCheck ->  if (isCheck) blockCheck(index) }
        }
        listBox.first().check(false)

    }

    // Logic ------------------------------------------------------------------

    fun check(index: Int) {
        listBox[index].check(false)
    }

}