package com.romander.navfenixgater.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.OnscreenKeyboardType
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.romander.navfenixgater.game.utils.Acts
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.gdxGame

class AInputText(
    override val screen: AdvancedScreen,
    val onscreenKeyboardType: OnscreenKeyboardType = OnscreenKeyboardType.Default,
    font29: BitmapFont,
    ls29_Reg: Label.LabelStyle,
    symbol: String,
): AdvancedGroup() {

    private val textFieldStyle = TextField.TextFieldStyle().apply {
            font       = font29
            fontColor  = GameColor.black_39
            cursor     = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.black_39))
            selection  = TextureRegionDrawable(screen.drawerUtil.getTexture(Color.BLACK.cpy().apply { a = 0.25f }))
        }

    private val imgCursor = Image(screen.drawerUtil.getTexture(GameColor.black_39))
    private val imgPanel  = Image(gdxGame.assetsAll.INPUT)

    private val lblSymbol = Label(symbol, ls29_Reg)

    val textField = TextField("", textFieldStyle)



    var blockTextFieldListener: (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addTextField()
        addImgCursor()
        addLblSymbol()
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

    private fun addLblSymbol() {
        addActor(lblSymbol)
        lblSymbol.setBounds(524f, 31f, 57f, 21f)
        lblSymbol.setAlignment(Align.right)
    }

    private fun addTextField() {
        addActor(textField)
        textField.setBounds(19f, 0f, 570f, 83f)

        textField.focusTraversal = false
        textField.isDisabled = true

        textField.alignment = Align.center

        textField.setOnscreenKeyboard { visible ->
            Gdx.input.setOnscreenKeyboardVisible(visible, onscreenKeyboardType)
        }

        textField.setTextFieldListener { _, key ->
            blockTextFieldListener(textField.text)

            if (key == '\n' || key == '\r') { // Перевіряємо Enter або Return
                (stage.keyboardFocus as? TextField)?.isDisabled = true


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