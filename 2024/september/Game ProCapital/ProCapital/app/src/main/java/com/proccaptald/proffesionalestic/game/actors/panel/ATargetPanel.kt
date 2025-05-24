package com.proccaptald.proffesionalestic.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.proccaptald.proffesionalestic.game.actors.AButton
import com.proccaptald.proffesionalestic.game.utils.GColor
import com.proccaptald.proffesionalestic.game.utils.actor.setOnClickListener
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.font.FontParameter

class ATargetPanel(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(42)
    private val font          = screen.fontGenerator_Pusia_Bold.generateFont(fontParameter)

    private val ls42 = LabelStyle(font, GColor.black)

    private val listCount = listOf(100,250,333,425,500,645,775,865,999)

    private var counter = 0

    private val btnLeft  = AButton(screen, AButton.Static.Type.LEFT)
    private val btnRight = AButton(screen, AButton.Static.Type.RIGHT)
    private val lblCount = Label(listCount[counter].toString(), ls42)

    var target = listCount[counter]

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.all.target_panel))
        addActors(btnLeft, btnRight, lblCount)
        btnLeft.apply {
            setBounds(138f,39f,42f,40f)
            setOnClickListener {
                if (counter-1 >= 0) {
                    counter--
                    target = listCount[counter]
                    lblCount.setText(target)
                }
            }
        }
        btnRight.apply {
            setBounds(373f,39f,42f,40f)
            setOnClickListener {
                if (counter+1 <= listCount.lastIndex) {
                    counter++
                    target = listCount[counter]
                    lblCount.setText(target)
                }
            }
        }
        lblCount.setBounds(274f,45f,92f,29f)
    }

}