package com.ayrym.inperdader.game.actors

import com.ayrym.inperdader.game.actors.checkbox.ACheckBox
import com.ayrym.inperdader.game.utils.actor.disable
import com.ayrym.inperdader.game.utils.advanced.AdvancedGroup
import com.ayrym.inperdader.game.utils.advanced.AdvancedScreen
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.uxo.monaxa.game.actors.checkbox.ACheckBoxGroup

class ABoxCenter(
    override val screen: AdvancedScreen,
    text: String,
    ls: Label.LabelStyle,
    val cbg: ACheckBoxGroup,
): AdvancedGroup() {

    private val box = ACheckBox(screen, ACheckBox.Type.CIRCLE)
    private val lbl = Label(text, ls)

    override fun addActorsOnGroup() {
        addAndFillActors(box, lbl)

        box.checkBoxGroup = cbg
        lbl.setAlignment(Align.center)
        children.onEach { it.disable() }
    }


    fun select() {
        box.check()
    }



}