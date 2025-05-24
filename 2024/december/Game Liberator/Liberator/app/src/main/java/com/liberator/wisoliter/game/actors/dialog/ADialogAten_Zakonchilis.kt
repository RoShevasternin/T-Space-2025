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

class ADialogAten_Zakonchilis(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgBackground = Image(screen.drawerUtil.getTexture(GameColor.green_44.cpy().apply { a = 0.9f }))
    private val btnX          = AButton(screen, AButton.Type.X)
    private val imgPopUp      = Image(gdxGame.assetsAll.pop_ended_army)
    private val aStart        = Actor()

    var blockX     = {}
    var blockStart = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgBackground)
        addBtnX()
        addImgPopUp()
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
        val nH = width * (497f / WIDTH_UI)

        imgPopUp.setBounds(
            (width / 2f) - (nW / 2f),
            (height / 2f) - (nH / 2f),
            nW, nH
        )
    }

    private fun addAStart() {
        addActor(aStart)
        aStart.setBounds(
            width * (217f / WIDTH_UI),
            height * (615f / HEIGHT_UI),
            width * (341f / WIDTH_UI),
            width * (70f / WIDTH_UI),
        )
        aStart.setOnClickListener(gdxGame.soundUtil) { blockStart() }
    }

}