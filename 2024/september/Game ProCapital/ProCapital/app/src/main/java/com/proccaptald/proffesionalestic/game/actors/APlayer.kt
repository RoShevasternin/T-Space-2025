package com.proccaptald.proffesionalestic.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.proccaptald.proffesionalestic.R
import com.proccaptald.proffesionalestic.appContext
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.font.FontParameter

class APlayer(
    override val screen: AdvancedScreen,
    indexProffesion: Int,
) : AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(35)
    private val font          = screen.fontGenerator_Pusia_Bold.generateFont(fontParameter)

    private val ls35 = LabelStyle(font, Color.WHITE)

    private val imgCoin   = Image(screen.game.all.coin)
    private val imgIcon   = Image(screen.game.all.listPersIcon[indexProffesion])
    private val lblCount  = Label("0", ls35)
    private val lblProf   = Label(appContext.resources.getStringArray(R.array.professions)[indexProffesion], ls35)

    var coins: Int = 0
        set(value) {
            field = value
            lblCount.setText(field)
        }

    override fun addActorsOnGroup() {
        addActors(imgCoin, imgIcon, lblCount, lblProf)
        imgCoin.setBounds(48f,0f,33f,36f)
        imgIcon.setBounds(28f,87f,123f,123f)
        lblCount.setBounds(88f,6f,42f,24f)
        lblProf.setBounds(0f,46f,178f,42f)
        lblProf.setAlignment(Align.center)
        imgIcon.setOrigin(Align.center)
    }

    fun current() {
        imgIcon.clearActions()
        imgIcon.addAction(
            Actions.forever(
            Actions.sequence(
                Actions.scaleBy(-0.25f, -0.25f, 0.25f, Interpolation.sine),
                Actions.scaleBy(0.25f, 0.25f, 0.25f, Interpolation.sine),
            )
        ))
    }

    fun uncurrent(block: () -> Unit) {
        imgIcon.clearActions()
        imgIcon.addAction(Actions.sequence(
            Actions.scaleTo(1f, 1f, 0.3f, Interpolation.sine),
            Actions.run { block() }
        ))
    }

}