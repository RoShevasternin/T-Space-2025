package com.pulser.purlembager.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.utils.actor.disable
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class AName(
    override val screen: AdvancedScreen,
    ls81: Label.LabelStyle,
): AdvancedGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.input_back)
    val lblName          = Label("", ls81)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActor(lblName)
        lblName.apply {
            disable()
            setBounds(354f, 87f, 342f, 104f)
            setAlignment(Align.center)
        }

        setOnClickListener {
            Gdx.input.getTextInput(object : Input.TextInputListener {
                override fun input(text: String?) {
                    lblName.setText(text)
                }
                override fun canceled() {}
            }, "Введите название", lblName.text.toString(), "");
        }
    }

}