package com.fincarable.kapletaloverno.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fincarable.kapletaloverno.game.actors.checkbox.ACheckBox
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedGroup
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedScreen
import com.uxo.monaxa.game.actors.checkbox.ACheckBoxGroup

class AChooseColor_RBG(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val img     = Image(screen.game.all.chose_color_rbg)
    private val listBox = List(3) { ACheckBox(screen, ACheckBox.Static.Type.DEFAULT) }

    var blockSelector: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(img)

        var nx  = 34f
        val cbg = ACheckBoxGroup()
        listBox.onEachIndexed { index, aCheckBox ->
            addActor(aCheckBox)
            aCheckBox.checkBoxGroup = cbg
            if (index == 0) aCheckBox.check()
            aCheckBox.setBounds(nx, 61f, 140f, 140f)
            nx += 47 + 140

            aCheckBox.setOnCheckListener { isCheck ->
                if (isCheck) blockSelector(index)
            }
        }
    }

    fun check(index: Int) {
        listBox[index].check()
    }

}