package com.romander.navfenixgater.game.actors.calculators

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.romander.navfenixgater.game.actors.AInputText
import com.romander.navfenixgater.game.actors.ATmpGroup
import com.romander.navfenixgater.game.actors.button.AButton
import com.romander.navfenixgater.game.actors.main.AMainResult
import com.romander.navfenixgater.game.dataStore.DataCredit
import com.romander.navfenixgater.game.dataStore.DataLizing
import com.romander.navfenixgater.game.screens.ResultScreen
import com.romander.navfenixgater.game.utils.actor.setOnTouchListener
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.game.utils.isNumTake
import com.romander.navfenixgater.util.log
import kotlin.math.roundToInt

class ACalculatorCredit(override val screen: AdvancedScreen): ACalculatorAnimator(screen) {

    private val tmpGroup = ATmpGroup(screen)
    private val scroll   = ScrollPane(tmpGroup)

    private val mainATmpGroup = ATmpGroup(screen)

    private val listTitle = listOf(
        "Название",
        "Срок кредитования",
        "Стоимость кредита",
        "Процентная ставка",
        "Единоразовая комиссия",
        "Амортизация (месячная)",
    )
    private val listSymbol = listOf(
        "",
        "мес",
        "₽",
        "%",
        "%",
        "%",
    )

    private val listLblTitle  = List(6) { Label(listTitle[it], ls29) }
    private val listInputText = List(6) { AInputText(screen,
        onscreenKeyboardType = if (it == 0) Input.OnscreenKeyboardType.Default else Input.OnscreenKeyboardType.NumberPad,
        font29, ls29_Reg, listSymbol[it]
    ) }

    private val btnRass = AButton(screen, AButton.Type.Rasss)

    private var nyInput = 701f

    private val falseInputData = CheckerDataLizing("", 0, 0.0, 0.0, 0.0, 0.0)
    private val inputData      = CheckerDataLizing("", 0, 0.0, 0.0, 0.0, 0.0)

    private data class CheckerDataLizing(
        var nName               : String,
        var termMonths: Int,
        var creditAmount: Double,
        var annualInterestRate: Double,
        var oneTimeCommissionPercent: Double,
        var monthlyAmortizationPercent: Double
    )

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addScroll()

        tmpGroup.addMainTmpGroup()

        mainATmpGroup.also {
            it.addListLblTitle()
            it.addListInputText()
            it.addBtnRasss()
        }
    }

    // Actors ----------------------------------------------------------------------------

    private fun addScroll() {
        addAndFillActor(scroll)
        tmpGroup.setSize(width, 1445f)
    }

    private fun AdvancedGroup.addMainTmpGroup() {
        addActor(mainATmpGroup)
        mainATmpGroup.setBounds(0f, this.height - this@ACalculatorCredit.height, this@ACalculatorCredit.width, this@ACalculatorCredit.height)
    }

    private fun AdvancedGroup.addListLblTitle() {
        var ny = 805f
        listLblTitle.forEach { lbl ->
            addActor(lbl)
            lbl.setBounds(0f, ny, 142f, 36f)
            ny -= 137 + 36
        }
    }

    private fun AdvancedGroup.addListInputText() {

        val listBlock = listOf<(String) -> String>(
            {
                val result = it.takeLast(20)
                inputData.nName = result
                result
            },
            {
                val result = it.isNumTake(2)
                inputData.termMonths = result
                result.toString()
            },
            {
                val result = it.isNumTake(6)
                inputData.creditAmount = result.toDouble()
                result.toString()
            },
            {
                val result = it.isNumTake(2)
                inputData.annualInterestRate = result.toDouble()
                result.toString()
            },
            {
                val result = it.isNumTake(2)
                inputData.oneTimeCommissionPercent = result.toDouble()
                result.toString()
            },
            {
                val result = it.isNumTake(2)
                inputData.monthlyAmortizationPercent = result.toDouble()
                result.toString()
            },
        )

        listInputText.forEachIndexed { index, input ->
            addActor(input)
            input.setBounds(0f, nyInput, 609f, 83f)
            nyInput -= 92 + 83

            input.setOnTouchListener(gdxGame.soundUtil) {

                input.textField.stage.keyboardFocus = input.textField
                input.textField.isDisabled = false // дозволяємо вводити
                Gdx.input.setOnscreenKeyboardVisible(true, input.onscreenKeyboardType)

                scroll.scrollPercentY = 0f

                input.animInputToEnterText(listTitle[index]) {
                    mainATmpGroup.children.filter { it != input }
                }
            }

            input.blockTextFieldListener = { text ->
                input.textField.text = listBlock[index].invoke(text)
            }
        }
    }

    private fun AdvancedGroup.addBtnRasss() {
        addActor(btnRass)
        btnRass.setBounds(0f, nyInput, 609f, 83f)

        btnRass.setOnClickListener(null) {
            if (handlerSave()) {
                gdxGame.soundUtil.apply { play(click) }
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
                }
            }
        }
    }

    // Logic -------------------------------------------------------------------

    private fun handlerSave(): Boolean {
        return if (
            inputData.nName                      != falseInputData.nName                      &&
            inputData.termMonths                 != falseInputData.termMonths                 &&
            inputData.creditAmount               != falseInputData.creditAmount               &&
            inputData.annualInterestRate         != falseInputData.annualInterestRate         &&
            inputData.oneTimeCommissionPercent   != falseInputData.oneTimeCommissionPercent   &&
            inputData.monthlyAmortizationPercent != falseInputData.monthlyAmortizationPercent
        ) {

            log("Всі поля відрізняються!")

            val savedData = calculateCredit()

            AMainResult.SHOWED_ITEM = savedData

            gdxGame.ds_CreditData.update { data ->
                val mData = data.toMutableList()
                mData.add(savedData)
                mData
            }

            true
        } else {
            log("Є хоча б одне однакове поле!")
            gdxGame.soundUtil.apply { play(click) }
            false
        }
    }

    private fun calculateCredit(): DataCredit {
        val data = inputData

        val monthlyInterestRate = data.annualInterestRate / 12 / 100
        val oneTimeCommission = data.creditAmount * data.oneTimeCommissionPercent / 100

        // Основна сума — це та, яку треба буде виплатити (без урахування комісій)
        val monthlyAmortization = data.creditAmount * data.monthlyAmortizationPercent / 100

        // Відсотки за весь термін
        val totalInterest = data.creditAmount * monthlyInterestRate * data.termMonths

        // Сума виплат = амортизація * кількість місяців + відсотки + комісія
        val totalAmortization = monthlyAmortization * data.termMonths
        val totalPayment = totalAmortization + totalInterest + oneTimeCommission

        return DataCredit(
            nName           = data.nName,
            totalPayment    = totalPayment.roundToInt(),
            creditBody      = totalAmortization.roundToInt(),
            interestAmount  = totalInterest.roundToInt(),
            commission      = oneTimeCommission.roundToInt(),
        )
    }


}