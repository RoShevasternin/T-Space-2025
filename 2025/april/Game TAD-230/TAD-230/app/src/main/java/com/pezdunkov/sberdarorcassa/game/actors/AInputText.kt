package com.pezdunkov.sberdarorcassa.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.OnscreenKeyboardType
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.pezdunkov.sberdarorcassa.game.utils.GameColor
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedGroup
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedScreen

class AInputText(
    override val screen: AdvancedScreen,
    val onscreenKeyboardType: OnscreenKeyboardType = OnscreenKeyboardType.Default,
    font29: BitmapFont,
): AdvancedGroup() {

    private val textFieldStyle = TextField.TextFieldStyle().apply {
            font       = font29
            fontColor  = GameColor.background
            cursor     = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.background))
            selection  = TextureRegionDrawable(screen.drawerUtil.getTexture(Color.BLACK.cpy().apply { a = 0.25f }))
        }

    //private val imgCursor = Image(screen.drawerUtil.getTexture(GameColor.black_1B))

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

        textField.alignment = Align.center or Align.left

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