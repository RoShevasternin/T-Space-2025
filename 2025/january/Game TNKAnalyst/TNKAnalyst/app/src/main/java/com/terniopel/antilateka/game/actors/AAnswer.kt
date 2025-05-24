package com.terniopel.antilateka.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.terniopel.antilateka.game.actors.checkbox.ACheckBox
import com.terniopel.antilateka.game.actors.checkbox.ACheckBoxGroup
import com.terniopel.antilateka.game.utils.actor.disable
import com.terniopel.antilateka.game.utils.advanced.AdvancedGroup
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen

class AAnswer(
    override val screen: AdvancedScreen,
    val labelStyle: LabelStyle,
    val cbg: ACheckBoxGroup
): AdvancedGroup() {

    val box = ACheckBox(screen, ACheckBox.Type.ANSWER)
    val lbl = Label("", labelStyle)


    override fun addActorsOnGroup() {
        box.checkBoxGroup = cbg
        addAndFillActor(box)
        addActor(lbl)
        lbl.apply {
            disable()
            setBounds(128f, 41f, 647f, 57f)
            wrap = true
        }
    }

    // Logic ------------------------------------------------------------------------------

    fun setText(text: String) {
        lbl.apply {
            setText(text)
            height = prefHeight
        }
        height     = (lbl.height + 82f)
        box.height = height

    }

}