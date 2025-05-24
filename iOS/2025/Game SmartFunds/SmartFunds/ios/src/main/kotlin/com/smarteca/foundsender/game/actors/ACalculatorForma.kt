package com.smarteca.foundsender.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.TextInputListener
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.actors.checkbox.ACheckBox
import com.smarteca.foundsender.game.actors.main.savings.AMainSavingsInput.Companion.EDIT_DATA_SAVING
import com.smarteca.foundsender.game.dataStore.DataSaving
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.PosSize
import com.smarteca.foundsender.game.utils.actor.setBounds
import com.smarteca.foundsender.game.utils.actor.setOnClickListener
import com.smarteca.foundsender.game.utils.actor.setOnTouchListener
import com.smarteca.foundsender.game.utils.advanced.AdvancedGroup
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen
import com.smarteca.foundsender.game.utils.font.FontParameter

class ACalculatorForma(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(48)

    private val font = screen.fontGenerator_Bold.generateFont(parameter)
    private val ls   = LabelStyle(font, Color.WHITE)

    private val imgForma  = Image(gdxGame.assetsAll.CALCULATOR_FORMA)
    private val box       = ACheckBox(screen, ACheckBox.Type.SimpleCompound)
    private val listLabel = List(4) { Label("", ls) }

    // Field
    private var currentLabel = listLabel.first()
    private var blockTextFieldListener: (String) -> Unit = {}

    private val falseInputData = DataCalculatorResult(0, 0, 0, 0)
    private val inputData      = DataCalculatorResult(0, 0, 0, 0)

    var isSimple = true
        private set

    override fun addActorsOnGroup() {
        addImgForma()
        addBox()
        addLabels()
    }

    private fun addImgForma() {
        addActor(imgForma)
        imgForma.setBounds(0f, 165f, 1031f, 617f)
    }

    private fun addBox() {
        addActor(box)
        box.setBounds(0f, 0f, 739f, 136f)

        box.setOnCheckListener { isCompound ->
            if (isCompound) {
                isSimple = false
            } else { // isSimple
                isSimple = true
            }
        }
    }

    private fun addLabels() {
        val listKeyboardType = listOf(
            Input.OnscreenKeyboardType.NumberPad,
            Input.OnscreenKeyboardType.NumberPad,
            Input.OnscreenKeyboardType.NumberPad,
            Input.OnscreenKeyboardType.NumberPad,
        )
        val listPosSize      = listOf(
            PosSize(0f, 565f, 375f, 124f),
            PosSize(580f, 565f, 369f, 124f),
            PosSize(0f, 286f, 270f, 124f),
            PosSize(580f, 280f, 375f, 124f),
        )
        val listTitle = listOf(
            "Deposit Amount",
            "Interest Rate",
            "Deposit Term",
            "Monthly Contribution",
        )

        listLabel.forEachIndexed { index, label ->
            addActor(label)
            label.setAlignment(Align.center)
            label.setBounds(listPosSize[index])

            label.setOnClickListener(gdxGame.soundUtil) {
                when(index) {
                    0 -> {
                        blockTextFieldListener = { text ->
                            inputData.depositAmount = text.isNumTake(6)
                            currentLabel.setText(inputData.depositAmount.toSeparate())
                        }
                    }
                    1 -> {
                        blockTextFieldListener = { text ->
                            inputData.interestRate = text.isNumTake(2)
                            currentLabel.setText(inputData.interestRate)
                        }
                    }
                    2 -> {
                        blockTextFieldListener = { text ->
                            inputData.depositTerm = text.isNumTake(2)
                            currentLabel.setText(inputData.depositTerm)
                        }
                    }
                    3 -> {
                        blockTextFieldListener = { text ->
                            inputData.monthlyContribution = text.isNumTake(6)
                            currentLabel.setText(inputData.monthlyContribution.toSeparate())
                        }
                    }
                }

                currentLabel = label
                Gdx.input.getTextInput(getTextInput(), listTitle[index], "", "", listKeyboardType[index])
            }
        }
    }

    // Logic -------------------------------------------------

    private fun getTextInput() = object : TextInputListener {
        override fun input(text: String?) {
            blockTextFieldListener(text ?: "")
        }

        override fun canceled() {}
    }

    fun handlerSave(): DataCalculatorResult? {
        return if (
            inputData.depositAmount       != falseInputData.depositAmount &&
            inputData.interestRate        != falseInputData.interestRate &&
            inputData.depositTerm         != falseInputData.depositTerm &&
            inputData.monthlyContribution != falseInputData.monthlyContribution) {

            log("Всі поля відрізняються!")

            gdxGame.soundUtil.apply { play(calculate) }

            inputData
        } else {
            log("Є хоча б одне однакове поле!")
            gdxGame.soundUtil.apply { play(fail) }

            null
        }
    }

    fun reset() {
        inputData.apply {
            depositAmount       = 0
            interestRate        = 0
            depositTerm         = 0
            monthlyContribution = 0
        }
        listLabel.onEach { it.setText("") }
    }

}
