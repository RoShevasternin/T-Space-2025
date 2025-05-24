package com.funkun.kylkan.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.OnscreenKeyboardType
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.funkun.kylkan.game.utils.GameColor
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.font.FontParameter
import com.funkun.kylkan.game.utils.toSeparate
import com.funkun.kylkan.util.log

class AInputText(
    override val screen: AdvancedScreen,
    private val onscreenKeyboardType: OnscreenKeyboardType = OnscreenKeyboardType.Default,
): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.ALL)
        .setSize(54)

    private val font54 = screen.fontGenerator_Roboto_Regular.generateFont(parameter)

    private val textFieldStyle = TextField.TextFieldStyle().apply {
            font       = font54
            fontColor  = GameColor.black_36
            cursor     = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.black_36))
            selection  = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.black_36.cpy().apply { a = 0.25f }))

            //messageFont      = font54
            //messageFontColor = GameColor.gray
        }


    val textField = TextField("", textFieldStyle)

    override fun addActorsOnGroup() {
        addTextField()
    }

    private fun addTextField() {
        addAndFillActor(textField)

        textField.alignment = Align.left

        textField.setOnscreenKeyboard { visible ->
            Gdx.input.setOnscreenKeyboardVisible(visible, onscreenKeyboardType)
        }
    }
    
}