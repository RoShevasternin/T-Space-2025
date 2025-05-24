package com.sukapital.saepital.game.actors.systemPanel.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.sukapital.saepital.game.actors.checkbox.ACheckBox
import com.sukapital.saepital.game.data.Expense
import com.sukapital.saepital.game.data.Income
import com.sukapital.saepital.game.utils.GColor
import com.sukapital.saepital.game.utils.actor.setOnClickListener
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.font.FontParameter
import com.sukapital.saepital.game.utils.removeNonDigits
import com.sukapital.saepital.game.utils.runGDX
import com.sukapital.saepital.game.utils.toBalance

class AIncomeExpense(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val fontMedium39  = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(39))

    private val ls39 = LabelStyle(fontMedium39, GColor.dark)

    private val imgPanel = Image(screen.game.all.IncomeExpense)
    private val lblName  = Label("Название", ls39)
    private val lblSumma = Label("Сумма", ls39)
    private val boxInEx  = ACheckBox(screen, ACheckBox.Type.INC_EXP)

    private var inputName  = ""
    private var inputSumma = ""

    var blockRemove: () -> Unit = {}
    var blockDone  : () -> Unit = {}

    private var inputType = InputType.Income

    enum class InputType {
        Income, Expense
    }

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLbls()
        addBtns()

        addActor(boxInEx)
        boxInEx.apply {
            setBounds(105f,447f,618f,71f)
            setOnCheckListener {
                inputType = if (it) InputType.Expense else InputType.Income
            }
        }
    }

    private fun addLbls() {
        addActors(lblName, lblSumma)
        lblName.setBounds(160f,341f,186f,51f)
        lblSumma.setBounds(160f,219f,186f,51f)

        val aName  = Actor()
        val aSumma = Actor()
        addActors(aName, aSumma)
        aName.apply {
            setBounds(160f,325f,507f,67f)
            setOnClickListener(screen.game.soundUtil) {
                Gdx.input.getTextInput(
                    inputListenerName,
                    "Введите название",
                    "",
                    "Название",
                    Input.OnscreenKeyboardType.Default
                )
            }
        }
        aSumma.apply {
            setBounds(160f,203f,507f,67f)
            setOnClickListener(screen.game.soundUtil) {
                Gdx.input.getTextInput(
                    inputListenerSumma,
                    "Введите сумму",
                    "",
                    "Сумма",
                    Input.OnscreenKeyboardType.NumberPad
                )
            }
        }
    }

    private fun addBtns() {
        val aRemove = Actor()
        val aDone   = Actor()
        addActors(aRemove, aDone)
        aRemove.apply {
            setBounds(160f,66f,132f,81f)
            setOnClickListener(screen.game.soundUtil) {
                blockRemove()
            }
        }
        aDone.apply {
            setBounds(535f,66f,132f,81f)
            setOnClickListener(screen.game.soundUtil) {
                if (checkInputLbls()) {
                    when(inputType) {
                        InputType.Income -> {
                            screen.game.incomeUtil.update { incomeList ->
                                incomeList.apply {
                                    add(
                                        Income(
                                            System.currentTimeMillis(),
                                            nameDesire = inputName,
                                            summa      = inputSumma.removeNonDigits().toInt()
                                        )
                                    )
                                }
                            }
                        }
                        InputType.Expense -> {
                            screen.game.expenseUtil.update { expenseList ->
                                expenseList.apply {
                                    add(
                                        Expense(
                                            System.currentTimeMillis(),
                                            nameDesire = inputName,
                                            summa      = inputSumma.removeNonDigits().toInt()
                                        )
                                    )
                                }
                            }
                        }
                    }

                    blockDone()
                } else screen.game.soundUtil.apply { play(fail_game, 0.15f) }
            }
        }
    }

    // Logic -------------------------------------------------------------------

    private val inputListenerName = object : Input.TextInputListener {
        override fun input(text: String) {
            runGDX {
                val result = text.take(20)
                inputName  = result
                lblName.setText(result)
            }
        }

        override fun canceled() {}
    }

    private val inputListenerSumma = object : Input.TextInputListener {
        override fun input(text: String) {
            runGDX {
                val result = text.take(7).removeNonDigits()
                inputSumma = result
                lblSumma.setText(result.toInt().toBalance)
            }
        }

        override fun canceled() {}
    }

    fun reset() {
        lblName.setText("Название")
        lblSumma.setText("Сумма")
    }

    private fun checkInputLbls(): Boolean {
        val resultName  = inputName.isNotBlank()
        val resultSumma = inputSumma.isNotBlank()
        return (resultName && resultSumma)
    }

}