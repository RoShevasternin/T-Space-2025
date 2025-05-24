package com.padrestoranom.easypaydonalds.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.padrestoranom.easypaydonalds.game.actors.checkbox.ACheckBox
import com.padrestoranom.easypaydonalds.game.actors.checkbox.ACheckBoxGroup
import com.padrestoranom.easypaydonalds.game.utils.actor.disable
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGroup
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedScreen

class ACategoryItem(
    override val screen: AdvancedScreen,
    val cbg: ACheckBoxGroup,
    region: TextureRegion,
    title: String,
    ls26_m: Label.LabelStyle,
): AdvancedGroup() {

    private val boxCircle = ACheckBox(screen, ACheckBox.Type.Circle)
    private val imgItem   = Image(region)
    private val lblTitle  = Label(title, ls26_m)

    override fun addActorsOnGroup() {
        boxCircle.checkBoxGroup = cbg

        addActors(boxCircle, imgItem, lblTitle)
        boxCircle.setBounds(40f, 42f, 111f, 111f)
        imgItem.setBounds(40f, 42f, 111f, 111f)
        lblTitle.setBounds(51f, 0f, 88f, 31f)

        lblTitle.setAlignment(Align.center)

        imgItem.disable()
    }

}