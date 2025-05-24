package com.kaskazer.kazmuchero.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.kaskazer.kazmuchero.game.utils.GColor
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedGroup
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen
import com.kaskazer.kazmuchero.game.utils.font.FontParameter
import kotlin.math.absoluteValue

class APanelBalance(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val font66        = screen.fontGenerator_AlbertSans.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "+-").setSize(66))
    private val font55        = screen.fontGenerator_AlbertSans.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "+-").setSize(55))

    private val ls66 = LabelStyle(font66, Color.WHITE)
    private val ls55 = LabelStyle(font55, Color.WHITE)

    private val ls55_Red   = LabelStyle(font55, GColor.red)
    private val ls55_Green = LabelStyle(font55, GColor.green)

    private var valueGaz  = 0
    private var valueCoin = 0

    val getValueGaz  get() = valueGaz
    val getValueCoin get() = valueCoin

    private val imgPanel = Image(screen.game.all.balance_panel_center)
    private val lblGaz   = Label(valueGaz.toString(), ls66)
    private val lblCoin  = Label(valueCoin.toString(), ls66)

    private val lblGazResult  = Label("", ls55_Green)
    private val lblCoinResult = Label("", ls55_Green)

    private var isPanelCenter = true

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActors(lblGaz, lblCoin, lblGazResult, lblCoinResult)

        lblGaz.setBounds(205f, 30f, 149f, 66f)
        lblCoin.setBounds(672f, 30f, 126f, 66f)

        lblGazResult.setBounds(139f, -66f, 134f, 55f)
        lblCoinResult.setBounds(606f, -66f, 134f, 55f)
        lblGazResult.setAlignment(Align.center)
        lblCoinResult.setAlignment(Align.center)
    }

    // Logic ----------------------------------------------------------------------------

    fun updateGaz(isShowChange: Boolean = false, block: (Int) -> Int) {
        val change = block(valueGaz)
        val result = (change - valueGaz)

        if (isShowChange) {
            if (result > 0) {
                lblGazResult.apply {
                    style = ls55_Green
                    setText("+$result")
                }
            } else {
                lblGazResult.apply {
                    style = ls55_Red
                    setText("$result")
                }
            }

            lblGazResult.clearActions()
            lblGazResult.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.run { lblGazResult.setText("") }
            ))
        }

        valueGaz = change
        lblGaz.setText(valueGaz)
        checkSum()
        screen.game.dataStore.updateGaz { valueGaz }
    }

    fun updateCoin(isShowChange: Boolean = false, block: (Int) -> Int) {

        val change = block(valueCoin)
        val result = (change - valueCoin)

        if (isShowChange) {
            if (result > 0) {
                lblCoinResult.apply {
                    style = ls55_Green
                    setText("+$result")
                }
            } else {
                lblCoinResult.apply {
                    style = ls55_Red
                    setText("$result")
                }
            }

            lblCoinResult.clearActions()
            lblCoinResult.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.run { lblCoinResult.setText("") }
            ))
        }

        valueCoin = change
        lblCoin.setText(valueCoin)
        checkSum()
        screen.game.dataStore.updateCoin { valueCoin }
    }

    private fun checkSum() {
        val isBigValue = (valueGaz.absoluteValue >= 10_000 || valueCoin.absoluteValue >= 10_000)

        if (isBigValue) {
            if (isPanelCenter) {
                isPanelCenter = false

                imgPanel.drawable = TextureRegionDrawable(screen.game.all.balance_panel_left)
                lblGaz.apply {
                    style = ls55
                    setBounds(147f, 36f, 159f, 55f)
                }
                lblCoin.apply {
                    style = ls55
                    setBounds(614f, 36f, 105f, 55f)
                }
            }
        } else {
            if (isPanelCenter.not()) {
                isPanelCenter = true

                imgPanel.drawable = TextureRegionDrawable(screen.game.all.balance_panel_center)
                lblGaz.apply {
                    style = ls66
                    setBounds(205f, 30f, 149f, 66f)
                }
                lblCoin.apply {
                    style = ls66
                    setBounds(672f, 30f, 126f, 66f)
                }
            }
        }
    }

}