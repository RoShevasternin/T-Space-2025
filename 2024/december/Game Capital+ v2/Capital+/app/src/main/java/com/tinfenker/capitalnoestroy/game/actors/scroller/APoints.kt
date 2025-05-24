package com.tinfenker.capitalnoestroy.game.actors.scroller

import com.tinfenker.capitalnoestroy.game.actors.checkbox.ACheckBox
import com.tinfenker.capitalnoestroy.game.actors.checkbox.ACheckBoxGroup
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedGroup
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedScreen

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