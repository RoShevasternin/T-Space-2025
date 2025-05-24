package com.vectorvesta.bronfinteh.game.actors.main.calculator

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.vectorvesta.bronfinteh.game.actors.AItem
import com.vectorvesta.bronfinteh.game.actors.APanel
import com.vectorvesta.bronfinteh.game.actors.AScrollPane
import com.vectorvesta.bronfinteh.game.actors.autoLayout.AVerticalGroup
import com.vectorvesta.bronfinteh.game.actors.button.AButton
import com.vectorvesta.bronfinteh.game.data.DataItem
import com.vectorvesta.bronfinteh.game.dataStore.DataItemType
import com.vectorvesta.bronfinteh.game.dataStore.DataItems
import com.vectorvesta.bronfinteh.game.screens.HistoryScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorLoanScreen
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.GameColor
import com.vectorvesta.bronfinteh.game.utils.TIME_ANIM_SCREEN
import com.vectorvesta.bronfinteh.game.utils.actor.animDelay
import com.vectorvesta.bronfinteh.game.utils.actor.animHide
import com.vectorvesta.bronfinteh.game.utils.actor.animShow
import com.vectorvesta.bronfinteh.game.utils.addSpace
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainGroup
import com.vectorvesta.bronfinteh.game.utils.font.FontParameter
import com.vectorvesta.bronfinteh.game.utils.gdxGame
import com.vectorvesta.bronfinteh.game.utils.isNumeric
import com.vectorvesta.bronfinteh.game.utils.toSeparateWithSymbol
import com.vectorvesta.bronfinteh.util.log

class AMainCalculatorLoan(
    override val screen: CalculatorLoanScreen,
): AdvancedMainGroup() {

    private val parameter43 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(43)
    private val parameter38 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(38)

    private val font43 = screen.fontGenerator_Medium.generateFont(parameter43)
    private val font38 = screen.fontGenerator_Medium.generateFont(parameter38)

    private val ls43   = LabelStyle(font43, GameColor.black_42)
    private val ls38_g = LabelStyle(font38, GameColor.gray_A2)
    private val ls38_b = LabelStyle(font38, GameColor.black_42)

    private val aPanel       = APanel(screen, gdxGame.assetsAll.main)
    private val btnBack      = AButton(screen, AButton.Type.Back)
    private val lblTitle     = Label("Калькулятор Кредита", ls43)
    private val btnCalculate = AButton(screen, AButton.Type.Calculate)

    private val verticalGroup  = AVerticalGroup(screen, 48f, paddingTop = 48f)
    private val scroll         = AScrollPane(verticalGroup)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanel()
        addBtnBack()
        addLblTitle()
        addAScroll()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanel() {
        addActor(aPanel)
        aPanel.setBounds(216f, 1765f, 469f, 97f)
        aPanel.blockMain = { screen.hideScreen { gdxGame.navigationManager.back() } }
        aPanel.blockHist = { screen.hideScreen { gdxGame.navigationManager.navigate(HistoryScreen::class.java.name) } }
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(48f, 1616f, 101f, 101f)
        btnBack.setOnClickListener { screen.hideScreen { gdxGame.navigationManager.back() } }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(205f, 1636f, 489f, 61f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addAScroll() {
        addActor(scroll)
        scroll.setBounds(48f, 0f, 805f, 1568f)
        verticalGroup.setSize(805f, 1995f)

        verticalGroup.apply {
            addItems()
        }
    }

    // Actors VerticalGroup ------------------------------------------------------------------------

    private fun AVerticalGroup.addItems() {
        val listDataItem = listOf(
            DataItem("", "Название", Input.OnscreenKeyboardType.Default) { text -> text.take(25) },
            DataItem("", "Сумма кредита", Input.OnscreenKeyboardType.NumberPad) { text -> text.take(8).toSeparateWithSymbol(' ') },
            DataItem("", "Процентная ставка (% годовых)", Input.OnscreenKeyboardType.NumberPad) { text -> text.take(2) + "%" },
            DataItem("", "Единоразовая комиссия (%)", Input.OnscreenKeyboardType.NumberPad) { text -> text.take(2) + "%" },
            DataItem("", "Ежемесячная комиссия", Input.OnscreenKeyboardType.NumberPad) { text -> text.take(8).toSeparateWithSymbol(' ') },
        ).onEach { data ->
            val item = AItem(screen, data.title, ls38_g, ls38_b, data.inputType, data.blockFormatText)
            item.setSize(805f, 165f)
            this.addActor(item)

            item.blockInputText = { text -> data.resultText = text }
        }

        verticalGroup.addSpace(200f)

        btnCalculate.setSize(805f, 135f)
        addActor(btnCalculate)

        btnCalculate.setOnClickListener {
            if (checkListDataItem(listDataItem)) {
                val name                  : String  = listDataItem[0].resultText
                val loanAmount            : Double  = listDataItem[1].resultText.toDouble() // Сума кредиту
                val annualInterestRate    : Double  = listDataItem[2].resultText.toDouble() // Процентна ставка (% річних)
                val oneTimeCommissionRate : Double  = listDataItem[3].resultText.toDouble() // Разова комісія (%)
                val monthlyCommission     : Double  = listDataItem[4].resultText.toDouble() // Щомісячна комісія

                val loanTermMonths = 12 // Припускаємо, що термін кредиту — 12 місяців

                // Річна процентна ставка в десятковому форматі
                val monthlyInterestRate = (annualInterestRate / 100) / 12

                // Аннуїтетний платіж
                val annuityCoefficient = (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths.toDouble())) /
                        (Math.pow(1 + monthlyInterestRate, loanTermMonths.toDouble()) - 1)

                val monthlyPayment = loanAmount * annuityCoefficient
                val totalLoanPayments = (monthlyPayment * loanTermMonths) + (monthlyCommission * loanTermMonths)

                // Загальна сума відсотків
                val totalInterest = totalLoanPayments - loanAmount - (monthlyCommission * loanTermMonths)

                // Разова комісія
                val oneTimeCommission = loanAmount * (oneTimeCommissionRate / 100)

                // Загальні комісії
                val totalCommissions = oneTimeCommission + (monthlyCommission * loanTermMonths)

                gdxGame.ds_DataItems.update { list ->
                    val mList = list.toMutableList()
                    val dataItems = DataItems(DataItemType.Loan, listOf(
                        name,
                        totalLoanPayments.toInt().toString().toSeparateWithSymbol(' ') + " ₽", //"Сума виплат"
                        totalInterest.toInt().toString().toSeparateWithSymbol(' ') + " ₽",     //"Процент"
                        loanAmount.toInt().toString().toSeparateWithSymbol(' ') + " ₽",        //"Основна сума кредиту"
                        totalCommissions.toInt().toString().toSeparateWithSymbol(' ') + " ₽"   //"Комісії"
                    ))
                    mList.add(dataItems)
                    mList
                }

                screen.hideScreen {
                    gdxGame.navigationManager.navigate(HistoryScreen::class.java.name)
                }
            } else {
                // Sound Fail
                log("No data")
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic --------------------------------------------------------------

    private fun checkListDataItem(listDataItem: List<DataItem>): Boolean {
        val check_1 = listDataItem[0].resultText.isNotEmpty()
        val check_2 = listDataItem[1].resultText.isNumeric()
        val check_3 = listDataItem[2].resultText.isNumeric()
        val check_4 = listDataItem[3].resultText.isNumeric()
        val check_5 = listDataItem[4].resultText.isNumeric()

        return (check_1 && check_2 && check_3 && check_4 && check_5)
    }

}