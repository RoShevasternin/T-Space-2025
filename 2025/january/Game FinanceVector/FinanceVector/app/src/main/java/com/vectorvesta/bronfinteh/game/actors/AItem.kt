package com.vectorvesta.bronfinteh.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.vectorvesta.bronfinteh.game.utils.actor.setOnClickListener
import com.vectorvesta.bronfinteh.game.utils.actor.setOnTouchListener
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedScreen
import com.vectorvesta.bronfinteh.game.utils.gdxGame
import com.vectorvesta.bronfinteh.util.log

class AItem(
    override val screen: AdvancedScreen,
    val title: String,

    ls38_gray : Label.LabelStyle,
    ls38_black: Label.LabelStyle,

    val inputType: Input.OnscreenKeyboardType,
    val blockFormatText: (String) -> String,
): AdvancedGroup() {

    private val lblTitle = Label(title, ls38_gray)
    private val lblText  = Label("", ls38_black)
    private val imgLine  = Image(gdxGame.assetsAll.line)

    var blockInputText: (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addActors(imgLine, lblTitle, lblText)

        imgLine.setSize(width, 2.5f)
        lblTitle.setBounds(0f, 91f, 191f, 49f)
        lblText.setBounds(0f, 24f, 188f, 34f)

        setOnTouchListener(gdxGame.soundUtil) {
            Gdx.input.getTextInput(object : Input.TextInputListener {
                override fun input(text: String) {
                    blockInputText(text)
                    lblText.setText(blockFormatText(text))
                }

                override fun canceled() {}
            }, title, "", "", inputType)
        }
    }

}