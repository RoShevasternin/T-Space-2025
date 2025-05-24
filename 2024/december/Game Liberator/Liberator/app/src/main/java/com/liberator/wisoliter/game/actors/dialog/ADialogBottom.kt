package com.liberator.wisoliter.game.actors.dialog

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.liberator.wisoliter.game.actors.button.AButton
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.SizeScaler
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.font.FontParameter
import com.liberator.wisoliter.game.utils.gdxGame

class ADialogBottom(
    override val screen: AdvancedScreen,
    title: String = "",
    text : String = "",
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 775f)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font45        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(45))
    private val font41        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(41))

    private val ls45 = LabelStyle(font45, GameColor.white)
    private val ls41 = LabelStyle(font41, GameColor.green_44)

    private val imgWhite = Image(screen.drawerUtil.getTexture(Color.WHITE))
    private val imgTop   = Image(gdxGame.assetsAll.orange)
    private val lblTitle = Label(title, ls45)
    private val lblText  = Label(text, ls41)
    private val btnConty = AButton(screen, AButton.Type.Conty)

    var blockConty = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgWhite)
        addImgTop()
        addLblTitle()
        addLblText()
        addBtnConty()
    }

    // Actors ----------------------------------------------------------------------------

    private fun addImgTop() {
        addActor(imgTop)
        imgTop.setBoundsScaled(sizeScaler, 0f, 582f, 775f, 94f)
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBoundsScaled(sizeScaler, 276f, 614f, 221f, 31f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addLblText() {
        addActor(lblText)
        lblText.setBoundsScaled(sizeScaler, 70f, 248f, 635f, 273f)
        lblText.setAlignment(Align.top, Align.center)
        lblText.wrap = true
    }

    private fun addBtnConty() {
        addActor(btnConty)
        btnConty.setBoundsScaled(sizeScaler, 202f, 94f, 372f, 71f)
        btnConty.setOnClickListener { blockConty() }
    }

    // Logic ----------------------------------------------------------------------------

    fun setNewTT(title: String = "", text: String = "", newY: Float = 0f) {
        lblTitle.setText(title)
        lblText.setText(text)

        if (newY != 0f) btnConty.y = newY

    }

}