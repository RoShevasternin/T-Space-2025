package com.ekobioznaher.sugertogerra.game.actors.scroller

import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedGroup
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedScreen
import com.ekobioznaher.sugertogerra.game.actors.checkbox.ACheckBox
import com.ekobioznaher.sugertogerra.game.actors.checkbox.ACheckBoxGroup

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