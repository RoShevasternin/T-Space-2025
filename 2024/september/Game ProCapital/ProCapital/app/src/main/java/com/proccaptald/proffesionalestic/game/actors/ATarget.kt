package com.proccaptald.proffesionalestic.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.font.FontParameter

class ATarget(
    override val screen: AdvancedScreen,
    target: Int,
) : AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(35)
    private val font          = screen.fontGenerator_Pusia_Bold.generateFont(fontParameter)

    private val ls35 = LabelStyle(font, Color.WHITE)

    private val imgCoin   = Image(screen.game.all.coin)
    private val imgTarget = Image(screen.game.all.target)
    private val lblCount  = Label(target.toString(), ls35)

    override fun addActorsOnGroup() {
        addActors(imgCoin, imgTarget, lblCount)
        imgCoin.setSize(33f,36f)
        imgTarget.setBounds(7f,49f,89f,42f)
        lblCount.setBounds(40f,6f,63f,24f)
    }

}