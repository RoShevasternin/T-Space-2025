package com.liberator.wisoliter.game.actors.dialog

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.liberator.wisoliter.game.actors.button.AButton
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.HEIGHT_UI
import com.liberator.wisoliter.game.utils.WIDTH_UI
import com.liberator.wisoliter.game.utils.actor.setOnClickListener
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.font.FontParameter
import com.liberator.wisoliter.game.utils.gdxGame

class ADialogAten_Neobhodimo(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font41        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(41))

    private val ls41 = LabelStyle(font41, GameColor.green_44)

    private val imgBackground = Image(screen.drawerUtil.getTexture(GameColor.green_44.cpy().apply { a = 0.9f }))
    private val btnX          = AButton(screen, AButton.Type.X)
    private val imgPopUp      = Image(gdxGame.assetsAll.pop_need_xp)
    private val lblText       = Label("", ls41)
    private val aStart        = Actor()

    var blockX     = {}
    var blockStart = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgBackground)
        addBtnX()
        addImgPopUp()
        addLblText()
        addAStart()
    }

    // Actors ----------------------------------------------------------------------------

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(
            width * (672f / WIDTH_UI),
            height * (1577f / HEIGHT_UI),
            width * (63f / WIDTH_UI),
            width * (63f / WIDTH_UI),
        )
        btnX.setOnClickListener { blockX() }
    }

    private fun addImgPopUp() {
        addActor(imgPopUp)

        val nW = width * (496f / WIDTH_UI)
        val nH = width * (447f / WIDTH_UI)

        imgPopUp.setBounds(
            (width / 2f) - (nW / 2f),
            (height / 2f) - (nH / 2f),
            nW, nH
        )
    }

    private fun addLblText() {
        addActor(lblText)

        val nW = width * (415f / WIDTH_UI)
        val nH = width * (28f / WIDTH_UI)

        lblText.setBounds(
            width * (180f / WIDTH_UI),
            height * (742f / HEIGHT_UI),
            nW, nH,
        )
        lblText.setAlignment(Align.center)
    }

    private fun addAStart() {
        addActor(aStart)
        aStart.setBounds(
            width * (263f / WIDTH_UI),
            height * (640f / HEIGHT_UI),
            width * (249f / WIDTH_UI),
            width * (70f / WIDTH_UI),
        )
        aStart.setOnClickListener(gdxGame.soundUtil) { blockStart() }
    }

    // Logic --------------------------------------------------------------------------

    fun setNeedXp(xp: Int) {
        lblText.setText("$xp xp")
    }
}