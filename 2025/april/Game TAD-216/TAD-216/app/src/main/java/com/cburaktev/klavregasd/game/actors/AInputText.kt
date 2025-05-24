package com.cburaktev.klavregasd.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.OnscreenKeyboardType
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.cburaktev.klavregasd.game.utils.GameColor
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedGroup
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedScreen

class AInputText(
    override val screen: AdvancedScreen,
    val onscreenKeyboardType: OnscreenKeyboardType = OnscreenKeyboardType.Default,
    font29: BitmapFont,
): AdvancedGroup() {

    private val textFieldStyle = TextField.TextFieldStyle().apply {
            font       = font29
            fontColor  = GameColor.black_21
            cursor     = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.black_21))
            selection  = TextureRegionDrawable(screen.drawerUtil.getTexture(Color.BLACK.cpy().apply { a = 0.25f }))
        }

    private val imgCursor = Image(screen.drawerUtil.getTexture(GameColor.black_21))

    val textField = TextField("", textFieldStyle)



    var blockTextFieldListener: (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addTextField()
        addImgCursor()
    }

    private fun addImgCursor() {
        //addActor(imgCursor)
        //imgCursor.setPosition(
        //    (width / 2) - 1.5f,
        //    (height / 2) - 25f,
        //)
        //imgCursor.setSize(3f, 50f)

        //imgCursor.isVisible = false
        //imgCursor.addAction(Acts.forever(Acts.sequence(
        //    Acts.fadeOut(0.2f),
        //    Acts.delay(0.3f),
        //    Acts.fadeIn(0.2f),
        //)))
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
                stage?.keyboardFocus = null
                Gdx.input.setOnscreenKeyboardVisible(false)
            }

            //imgCursor.isVisible = textField.text.isBlank()
        }
//        textField.addListener(object : FocusListener() {
//            override fun keyboardFocusChanged(event: FocusEvent?, actor: Actor?, focused: Boolean) {
//                imgCursor.isVisible = focused
//            }
//        })
    }

//    fun showCursor() {
//        imgCursor.isVisible = true
//    }

}