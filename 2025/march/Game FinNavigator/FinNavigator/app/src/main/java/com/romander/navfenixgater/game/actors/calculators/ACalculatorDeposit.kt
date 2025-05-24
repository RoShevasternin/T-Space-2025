package com.romander.navfenixgater.game.actors.calculators

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.romander.navfenixgater.game.actors.AInputText
import com.romander.navfenixgater.game.actors.ATmpGroup
import com.romander.navfenixgater.game.actors.button.AButton
import com.romander.navfenixgater.game.actors.main.AMainResult
import com.romander.navfenixgater.game.dataStore.DataDeposit
import com.romander.navfenixgater.game.screens.ResultScreen
import com.romander.navfenixgater.game.utils.actor.setOnTouchListener
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.game.utils.isNumTake
import com.romander.navfenixgater.util.log
import kotlin.math.roundToInt

class ACalculatorDeposit(override val screen: AdvancedScreen): ACalculatorAnimator(screen) {

    private val tmpGroup = ATmpGroup(screen)
    private val scroll   = ScrollPane(tmpGroup)

    private val mainATmpGroup = ATmpGroup(screen)

    private val listTitle = listOf(
        "Название",
        "Срок",
        "Сумма вклада",
        "Процент",
        "Пополнение вкалда в месяц",
    )
    private val listSymbol = listOf(
        "",
        "мес",
        "₽",
        "%",
        "₽",
    )

    private val listLblTitle  = List(5) { Label(listTitle[it], ls29) }
    private val listInputText = List(5) { AInputText(screen,
        onscreenKeyboardType = if (it == 0) Input.OnscreenKeyboardType.Default else Input.OnscreenKeyboardType.NumberPad,
        font29, ls29_Reg, listSymbol[it]
    ) }

    private val btnRass = AButton(screen, AButton.Type.Rasss)

    private var nyInput = 701f

    private val falseInputData = CheckerDataLizing("", 0, 0.0, 0.0, 0.0)
    private val inputData      = CheckerDataLizing("", 0, 0.0, 0.0, 0.0)

    private data class CheckerDataLizing(
        var nName               : String,
        var termMonths: Int,            // Срок вкладу
        var initialAmount: Double,      // Початкова сума
        var annualPercent: Double,      // Річна ставка
        var monthlyDeposit: Double      // Щомісячне поповнення
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
        mainATmpGroup.setBounds(0f, this.height - this@ACalculatorDeposit.height, this@ACalculatorDeposit.width, this@ACalculatorDeposit.height)
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
                inputData.initialAmount = result.toDouble()
                result.toString()
            },
            {
                val result = it.isNumTake(2)
                inputData.annualPercent = result.toDouble()
                result.toString()
            },
            {
                val result = it.isNumTake(6)
                inputData.monthlyDeposit = result.toDouble()
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
            inputData.nName          != falseInputData.nName          &&
            inputData.termMonths     != falseInputData.termMonths     &&
            inputData.initialAmount  != falseInputData.initialAmount  &&
            inputData.annualPercent  != falseInputData.annualPercent  &&
            inputData.monthlyDeposit != falseInputData.monthlyDeposit
        ) {

            log("Всі поля відрізняються!")

            val savedData = calculateDeposit()

            AMainResult.SHOWED_ITEM = savedData

            gdxGame.ds_DepositData.update { data ->
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

    private fun calculateDeposit(): DataDeposit {
        val data = inputData

        val monthlyRate = data.annualPercent / 12 / 100
        var total = data.initialAmount
        var interest = 0.0
        var body = data.initialAmount

        repeat(data.termMonths) {
            val monthInterest = total * monthlyRate
            interest += monthInterest
            total += monthInterest
            total += data.monthlyDeposit
            body += data.monthlyDeposit
        }

        return DataDeposit(
            nName           = data.nName,
            totalBody       = body.roundToInt(),
            totalInterest   = interest.roundToInt(),
        )
    }


}