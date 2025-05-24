package com.ogonechek.putinvestor.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.ogonechek.putinvestor.game.utils.GameColor
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedGroup
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.font.FontParameter
import com.ogonechek.putinvestor.game.utils.gdxGame
import com.ogonechek.putinvestor.game.utils.toSeparate

class APanel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val summa = gdxGame.ds_Invest.flow.value.sumOf { it.summa }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "₽")
    private val font145       = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(145))
    private val font80        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(80))

    private val ls145 = Label.LabelStyle(font145, GameColor.orange_FD)
    private val ls80  = Label.LabelStyle(font80, GameColor.blue_20)

    private val lblOrange = Label(summa.toSeparate() + " ₽", ls145)
    private val lblBlue   = Label(summa.toSeparate() + " ₽", ls80)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.panel))
        addLbls()
    }

    // Actors ----------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblOrange, lblBlue)
        lblOrange.setBounds(171f, 392f, 873f, 105f)
        lblBlue.setBounds(171f, 211f, 421f, 58f)
    }

}