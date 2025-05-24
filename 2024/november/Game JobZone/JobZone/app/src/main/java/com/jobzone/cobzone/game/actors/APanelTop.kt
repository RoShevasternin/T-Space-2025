package com.jobzone.cobzone.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.jobzone.cobzone.game.actors.button.AButton
import com.jobzone.cobzone.game.utils.GameColor
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.font.FontParameter
import com.jobzone.cobzone.game.utils.SizeScaler

class APanelTop(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 732f)

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.ALL)
        .setSize(43)

    private val font43 = screen.fontGenerator_Gilroy_Medium.generateFont(parameter)

    private val ls43 = LabelStyle(font43, GameColor.black_24)

    private var imgLogo: Image?   = null
    private var lblText: Label?   = null
    private var btnBack: AButton? = null

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(Color.WHITE)))
    }

    // Actors ------------------------------------------------------------------------

    fun addImgLogo() {
        imgLogo = Image(screen.game.assetsLoader.logo)

        addActor(imgLogo)
        imgLogo!!.setBoundsScaled(190f, 30f, 351f, 98f)
    }

    fun addLblText(text: String) {
        lblText = Label("", ls43)

        addActor(lblText)
        lblText!!.setBoundsScaled(202f, 74f, 327f, 52f)
        lblText!!.setAlignment(Align.center)
        lblText!!.setText(text)
    }

    fun addBtnBack() {
        btnBack = AButton(screen, AButton.Type.Back)

        addActor(btnBack)
        btnBack!!.setBoundsScaled(17f, 53f, 95f, 95f)

        btnBack!!.setOnClickListener {
            screen.hideScreen { screen.game.navigationManager.back() }
        }

    }

}