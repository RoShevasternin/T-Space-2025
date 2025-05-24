package com.kaskazer.kazmuchero.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedGroup
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen
import com.kaskazer.kazmuchero.game.utils.font.FontParameter

class APanelLVL(
    override val screen: AdvancedScreen,
    level: Int,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val font66        = screen.fontGenerator_AlbertSans.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "-LVL").setSize(66))

    private val ls66 = LabelStyle(font66, Color.WHITE)

    private val imgPanel = Image(screen.game.all.panel_lvl)
    private val lblLVL   = Label("${level}-LVL", ls66)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActor(lblLVL)
        lblLVL.setBounds(51f, 50f, 175f, 66f)
        lblLVL.setAlignment(Align.center)
    }

}