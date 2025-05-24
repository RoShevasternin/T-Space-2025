package com.romander.navfenixgater.game.actors.items

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.romander.navfenixgater.game.actors.ABottom
import com.romander.navfenixgater.game.actors.button.AButton
import com.romander.navfenixgater.game.dataStore.CalculationData
import com.romander.navfenixgater.game.dataStore.DataCredit
import com.romander.navfenixgater.game.dataStore.DataDeposit
import com.romander.navfenixgater.game.dataStore.DataIpoteka
import com.romander.navfenixgater.game.dataStore.DataLizing
import com.romander.navfenixgater.game.screens.HistoryScreen
import com.romander.navfenixgater.game.screens.MenuScreen
import com.romander.navfenixgater.game.screens.ResultScreen
import com.romander.navfenixgater.game.utils.Block
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.TIME_ANIM_SCREEN
import com.romander.navfenixgater.game.utils.actor.animDelay
import com.romander.navfenixgater.game.utils.actor.animHide
import com.romander.navfenixgater.game.utils.actor.animShow
import com.romander.navfenixgater.game.utils.actor.setBounds
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedMainGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.font.FontParameter
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.game.utils.toSeparateWithSymbol

class AItemLizing(
    override val screen: AdvancedScreen,
    val data: DataLizing,
): AdvancedGroup() {

    private val fieldList = listOf(
        "Сумма выплат",
        "Стоимость объекта",
        "Итого",
        "Комисии",
    )
    private val valueList = listOf(
        data.monthlyPayment.toSeparateWithSymbol(' ') + " ₽",
        data.objectCostFinal.toSeparateWithSymbol(' ') + " ₽",
        data.totalPayments.toSeparateWithSymbol(' ') + " ₽",
        data.commission.toSeparateWithSymbol(' ') + " ₽",
    )

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))
    private val font30        = screen.fontGenerator_Reg.generateFont(fontParameter.setSize(33))
    private val font36        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(36))

    private val ls30 = Label.LabelStyle(font30, GameColor.black_39)

    private val ls51_Yellow = Label.LabelStyle(font51, GameColor.yellow_FE)
    private val ls36_Yellow = Label.LabelStyle(font36, GameColor.yellow_FE)

    private val lblItemTitle = Label(data.nName, ls51_Yellow)
    private val listLblField = List(4) { Label(fieldList[it], ls30) }
    private val listLblValue = List(4) { Label(valueList[it], ls36_Yellow) }

    override fun addActorsOnGroup() {
        addValues()
    }

    // Actors ------------------------------------------------------------------------

    private fun addValues() {
        addActor(lblItemTitle)
        lblItemTitle.setBounds(0f, 339f, 220f, 67f)

        var fNY = 254f
        listLblField.forEach {
            addActor(it)
            it.setBounds(0f, fNY, 232f, 43f)
            fNY -= 37 + 43
        }

        var vNY = 254f
        listLblValue.forEach {
            addActor(it)
            it.setBounds(461f, vNY, 147f, 48f)
            it.setAlignment(Align.right)
            vNY -= 37 + 48
        }
    }

}