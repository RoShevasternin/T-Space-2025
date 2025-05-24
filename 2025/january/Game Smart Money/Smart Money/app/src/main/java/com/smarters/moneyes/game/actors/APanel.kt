package com.smarters.moneyes.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.smarters.moneyes.game.actors.main.balance
import com.smarters.moneyes.game.actors.main.range
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.font.FontParameter
import com.smarters.moneyes.game.utils.gdxGame

class APanel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/")
    private val font58        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(58))
    private val font46        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(46))

    private val ls58 = Label.LabelStyle(font58, Color.WHITE.cpy().apply { a = 0.90f })
    private val ls46 = Label.LabelStyle(font46, Color.WHITE.cpy().apply { a = 0.68f })

    private val lblRange = Label("$range/12", ls46)
    private val lblCoins = Label("$balance", ls58)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.panel))
        addLbls()
    }

    // Actors ----------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblRange, lblCoins)
        lblRange.apply {
            setBounds(273f, 50f, 95f, 56f)
            setAlignment(Align.center)
        }
        lblCoins.apply {
            setBounds(842f, 42f, 37f, 71f)
            setAlignment(Align.center)
        }
    }

}