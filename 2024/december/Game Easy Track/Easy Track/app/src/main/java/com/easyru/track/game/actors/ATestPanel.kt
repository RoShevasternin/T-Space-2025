package com.easyru.track.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.easyru.track.game.utils.GameColor
import com.easyru.track.game.utils.advanced.AdvancedGroup
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.font.FontParameter
import com.easyru.track.game.utils.gdxGame

class ATestPanel(
    override val screen: AdvancedScreen,
    val title: String,
    val text : String,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val fontR_43 = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(43))
    private val fontR_35 = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(35))

    private val lsR_43 = Label.LabelStyle(fontR_43, GameColor.black_24)
    private val lsR_35 = Label.LabelStyle(fontR_35, GameColor.black_24)

    private val imgPanel = Image(gdxGame.assetsAll.nine_white)
    private val lblTitle = Label(title, lsR_43)
    private val lblText  = Label(text, lsR_35)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblText()
        addLblTitle()

        height = (lblTitle.prefHeight + lblText.prefHeight + 64 + 53 + 53)
    }

    override fun sizeChanged() {
        super.sizeChanged()
        imgPanel.setSize(width, height)

        lblTitle.y = height - (53 + lblTitle.prefHeight)
    }

    // Actors ---------------------------------------------------

    private fun addLblText() {
        addActor(lblText)

        lblText.apply {
            wrap   = true
            width  = 599f
            height = prefHeight
            setPosition(48f, 75f)
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)

        lblTitle.apply {
            setAlignment(Align.center)
            wrap   = true
            width  = 601f
            height = prefHeight
            x      = 47f
        }

    }

}