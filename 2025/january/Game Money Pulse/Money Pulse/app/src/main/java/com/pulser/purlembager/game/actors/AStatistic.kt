package com.pulser.purlembager.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.actors.shader.AMaskGroup
import com.pulser.purlembager.game.actors.shader.AStatisticCircle
import com.pulser.purlembager.game.utils.GameColor
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.font.FontParameter
import com.pulser.purlembager.game.utils.gdxGame

class AStatistic(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font50        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(50))

    private val ls50 = Label.LabelStyle(font50, GameColor.black_31)

    private val listColor = listOf(
        Color.valueOf("E6E951"),
        Color.valueOf("64D476"),
        Color.valueOf("64B1D4"),
    )
    private val listPercent = floatArrayOf(
        50f, 25f, 25f
    )

    private val statisticCircle = AStatisticCircle(screen, listColor, listPercent)
    private val lblTitle = Label("", ls50)


    override fun addActorsOnGroup() {
        val mg = AMaskGroup(screen, gdxGame.assetsAll.elp_mask)
        addAndFillActor(mg)
        mg.addAndFillActor(statisticCircle)
        addStatisticLbls()
    }

    // Actors ---------------------------------------------------------

    private fun addStatisticLbls() {
        addActors(lblTitle)
        lblTitle.apply {
            setBounds(264f, -7f, 647f, 64f)
            setAlignment(Align.center)
        }
    }

    // Logic -----------------------------------------------------------

    fun update(arrPercent: FloatArray) {
        statisticCircle.arrPercentage = arrPercent
    }

}