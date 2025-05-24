package com.proccaptald.proffesionalestic.game.actors.panel

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.proccaptald.proffesionalestic.game.actors.AButton
import com.proccaptald.proffesionalestic.game.utils.GColor
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.font.FontParameter

class APlayerPanel(
    override val screen: AdvancedScreen,
    val playerId: Int,
) : AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(45)
    private val font          = screen.fontGenerator_Pusia_Bold.generateFont(fontParameter)

    private val ls45 = LabelStyle(font, GColor.black)

    private val listProffesion = screen.game.all.listPersIcon

    private val btnLeft  = AButton(screen, AButton.Static.Type.LEFT)
    private val btnRight = AButton(screen, AButton.Static.Type.RIGHT)
    private val lblCount = Label("Игрок $playerId", ls45)
    private val imgIcon  = Image(listProffesion[0])

    var proffesionIndex = 0

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.all.pers_panel))
        addActors(btnLeft, btnRight, lblCount, imgIcon)
        btnLeft.apply {
            setBounds(135f,72f,42f,40f)
            setOnClickListener {
                if (proffesionIndex-1 >= 0) {
                    proffesionIndex--
                    imgIcon.drawable = TextureRegionDrawable(listProffesion[proffesionIndex])
                }
            }
        }
        btnRight.apply {
            setBounds(383f,72f,42f,40f)
            setOnClickListener {
                if (proffesionIndex+1 <= listProffesion.lastIndex) {
                    proffesionIndex++
                    imgIcon.drawable = TextureRegionDrawable(listProffesion[proffesionIndex])
                }
            }
        }
        imgIcon.setBounds(218f,30f,123f,123f)
        lblCount.setBounds(188f,173f,172f,55f)
        lblCount.setAlignment(Align.center)
    }

}