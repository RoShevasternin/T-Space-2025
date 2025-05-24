package com.smarteca.foundsender.game.actors.button

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.utils.actor.disable
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen

open class AButtonText(
    override val screen: AdvancedScreen,
    ls: LabelStyle,
    text: String,
    type: Type,
) : AButton(screen, type) {

    val label = Label(text, ls)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAndFillActor(label)
        label.setAlignment(Align.center)
        label.disable()
    }

}
