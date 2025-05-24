package com.traoya.yatirimya.game.actors.button

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.traoya.yatirimya.game.utils.actor.disable
import com.traoya.yatirimya.game.utils.advanced.AdvancedScreen

open class ATextButton(
    override val screen: AdvancedScreen,
    text: String,
    labelStyle: Label.LabelStyle,
    type: Type = Type.None,
) : AButton(screen, type) {

    val label = Label(text, labelStyle)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAndFillActor(label)

        label.disable()
        label.setAlignment(Align.center)
    }

}