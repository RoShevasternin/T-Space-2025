package com.jobzone.cobzone.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.OnscreenKeyboardType
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.jobzone.cobzone.game.utils.GameColor
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.font.FontParameter

class AInputText(
    override val screen: AdvancedScreen,
    private val hintText: String,
    private val onscreenKeyboardType: OnscreenKeyboardType = OnscreenKeyboardType.Default,
): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.ALL)
        .setSize(32)

    private val font32 = screen.fontGenerator_Gilroy_Regular.generateFont(parameter)

    private val textFieldStyle = TextField.TextFieldStyle().apply {
            font       = font32
            fontColor  = GameColor.gray
            cursor     = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.gray))
            selection  = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.gray.cpy().apply { a = 0.25f }))

            messageFont      = font32
            messageFontColor = GameColor.gray
        }


    private val imgPanel  = Image(screen.game.assetsAll.pole_vvoda)
    private val textField = TextField("", textFieldStyle)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addTextField()
    }

    private fun addTextField() {
        addActor(textField)
        textField.setBounds(20f, 0f, 552f, 80f)

        textField.messageText = hintText
        textField.alignment   = Align.left

        textField.setOnscreenKeyboard { visible ->
            Gdx.input.setOnscreenKeyboardVisible(visible, onscreenKeyboardType)
        }
    }

}