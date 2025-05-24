package com.pulser.purlembager.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.utils.GameColor
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.font.FontParameter
import com.pulser.purlembager.game.utils.gdxGame

class ABack(
    override val screen: AdvancedScreen,
    title: String,
    type: Type,
): AdvancedGroup() {

    enum class Type {
        Black, White
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font50        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(50))

    private val ls50 = Label.LabelStyle(font50, if (type == Type.Black) GameColor.black_31 else GameColor.white_FA)

    private val lblTitle = Label(title, ls50)
    private val imgBack  = Image(if (type == Type.Black) gdxGame.assetsAll.back_black else gdxGame.assetsAll.back_white)

    var blockBack = {}

    override fun addActorsOnGroup() {
        val aBack = Actor()

        addActors(lblTitle, imgBack, aBack)
        lblTitle.apply {
            setBounds(264f, -7f, 647f, 64f)
            setAlignment(Align.center)
        }

        imgBack.setBounds(59f, -2f, 62f, 63f)
        aBack.apply {
            setBounds(46f, -13f, 88f, 88f)
            setOnClickListener { blockBack() }
        }
    }

}