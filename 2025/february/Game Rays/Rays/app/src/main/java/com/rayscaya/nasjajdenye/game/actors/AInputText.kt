package com.rayscaya.nasjajdenye.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.OnscreenKeyboardType
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.rayscaya.nasjajdenye.game.utils.GameColor
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedGroup
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedScreen
import com.rayscaya.nasjajdenye.game.utils.font.FontParameter
import kotlin.apply

class AInputText(
    override val screen: AdvancedScreen,
    private val onscreenKeyboardType: OnscreenKeyboardType = OnscreenKeyboardType.Default,
): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(40)
    private val font40    = screen.fontGenerator_Regular.generateFont(parameter)

    private val textFieldStyle = TextField.TextFieldStyle().apply {
            font       = font40
            fontColor  = GameColor.green_43
            cursor     = TextureRegionDrawable(screen.drawerUtil.getTexture(Color.BLACK))
            selection  = TextureRegionDrawable(screen.drawerUtil.getTexture(Color.BLACK.cpy().apply { a = 0.25f }))
        }

    val textField = TextField("", textFieldStyle)

    var blockTextFieldListener: (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addTextField()
    }

    private fun addTextField() {
        addAndFillActor(textField)

        textField.alignment = Align.center

        textField.setOnscreenKeyboard { visible ->
            Gdx.input.setOnscreenKeyboardVisible(visible, onscreenKeyboardType)
        }

        textField.setTextFieldListener { _, key ->
            blockTextFieldListener(textField.text)

            if (key == '\n' || key == '\r') { // Перевіряємо Enter або Return
                stage.keyboardFocus = null
                Gdx.input.setOnscreenKeyboardVisible(false)
            }
        }
    }

}