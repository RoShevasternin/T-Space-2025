package com.proccaptald.proffesionalestic.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.proccaptald.proffesionalestic.game.utils.GColor
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.font.FontParameter

class ACoin(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(31)
    private val font          = screen.fontGenerator_Pusia_Bold.generateFont(fontParameter)

    private val ls31 = LabelStyle(font, GColor.black)

    val coin = (-20..20).filter { it != 0 }.random()

    private val imgCoin = Image(screen.game.all.coin)
    private val lblCoin = Label((if (coin > 0) "+" else "") + coin, ls31)

    override fun addActorsOnGroup() {
        addActors(imgCoin, lblCoin)
        imgCoin.setSize(33f,36f)
        lblCoin.setBounds(40f,8f,51f,21f)
    }

}