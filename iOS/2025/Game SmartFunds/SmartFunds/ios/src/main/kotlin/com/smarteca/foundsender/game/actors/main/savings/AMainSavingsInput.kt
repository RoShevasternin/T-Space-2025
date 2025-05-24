package com.smarteca.foundsender.game.actors.main.savings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.TextInputListener
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.ATmpGroup
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.button.AButtonText
import com.smarteca.foundsender.game.dataStore.DataSaving
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsInputScreen
import com.smarteca.foundsender.game.screens.savings.SavingsResultScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.*
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter

class AMainSavingsInput(
    override val screen: SavingsInputScreen,
): AdvancedMainGroup() {

    companion object {
        var EDIT_DATA_SAVING: DataSaving? = null
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font102 = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))
    private val font72  = screen.fontGenerator_Bold.generateFont(parameter.setSize(72))
    private val font48  = screen.fontGenerator_SemiBold.generateFont(parameter.setSize(48))

    private val ls102 = LabelStyle(font102, Color.WHITE)
    private val ls72  = LabelStyle(font72, Color.WHITE)
    private val ls48  = LabelStyle(font48, GameColor.black_16)

    private val aLogo        = ALogo(screen)
    private val lblTitle     = Label("New saving", ls102)
    private val btnSavings   = AButton(screen, AButton.Type.Savings)
    private val btnReset     = AButton(screen, AButton.Type.Reset)
    private val aBottomMenu  = ABottomMenu(screen, ABottomMenu.Type.Savings)
    private val imgForma     = Image(gdxGame.assetsAll.SAVINGS_FORMA)

    private val tmpGroup   = ATmpGroup(screen)
    private val listLbl    = List(5) { Label("", ls72) }
    private val btnCalcul  = AButtonText(screen, ls48, "Calculate", AButton.Type.Green)
    private val scrollPane = ScrollPane(tmpGroup)

    // Field
    private var currentLabel = listLbl.first()
    private var blockTextFieldListener: (String) -> Unit = {}

    private val falseInputData = DataSaving("", 0, 0, 0, 0)
    private val inputData      = EDIT_DATA_SAVING ?: DataSaving("", 0, 0, 0, 0)

    override fun addActorsOnGroup() {
        color.a = 0f

        addALogo()
        addLblTitle()
        addBtnSavings()
        addBtnReset()
        addABottomMenu()
        addForma()

        EDIT_DATA_SAVING?.let {
            listOf<Int>(0, it.amount, it.rate, it.term, it.contr).forEachIndexed { index, value ->
                if (index == 0) listLbl[0].setText(it.nName)
                else listLbl[index].setText(value.toSeparate())
            }
        }

        animShowMain()
    }

    override fun dispose() {
        super.dispose()
        EDIT_DATA_SAVING = null
    }

    // Actors ------------------------------------------------------------------------

    private fun addALogo() {
        addActor(aLogo)
        aLogo.setBounds(364f, 2217f, 452f, 200f)
    }

    private fun addLblTitle() {
        addActors(lblTitle)
        lblTitle.apply {
            setBounds(60f, 1815f, 501f, 124f)
        }
    }

    private fun addBtnSavings() {
        addActor(btnSavings)
        btnSavings.apply {
            setBounds(30f, 1962f, 267f, 133f)
            setOnClickListener {
                navTo(SavingsScreen::class.java.name)
            }
        }
    }

    private fun addBtnReset() {
        addActor(btnReset)
        btnReset.apply {
            setBounds(1009f, 1801f, 161f, 161f)
            setOnClickListener {
                inputData.apply {
                    nName  = ""
                    amount = 0
                    rate   = 0
                    term   = 0
                    contr  = 0
                }
                listLbl.onEach { it.setText("") }
            }
        }
    }

    private fun addABottomMenu() {
        addActor(aBottomMenu)
        aBottomMenu.setBounds(0f, 107f, 1181f, 148f)

        aBottomMenu.apply {
            blockDashboard = { navTo(DashboardScreen::class.java.name) }
            blockSavings = {}
            blockCalculator = { navTo(CalculatorScreen::class.java.name) }
            blockGlossary = { navTo(GlossaryScreen::class.java.name) }
            blockTest = { navTo(TestScreen::class.java.name) }
        }
    }

    private fun addForma() {
        addActor(scrollPane)
        scrollPane.setBounds(0f, 306f, 1180f, 1449f)

        tmpGroup.setSize(1180f, 1654f)
        tmpGroup.addActor(imgForma)
        imgForma.setBounds(0f, 175f, 1180f, 1449f)
        tmpGroup.addActor(btnCalcul)
        btnCalcul.apply {
            setBounds(75f, 30f, 1030f, 135f)
            setOnClickListener {
                if (handlerSave()) {
                    gdxGame.soundUtil.apply { play(calculate) }
                    navTo(SavingsResultScreen::class.java.name)
                }
            }
        }

        val listKeyboardType = listOf(
            Input.OnscreenKeyboardType.Default,
            Input.OnscreenKeyboardType.NumberPad,
            Input.OnscreenKeyboardType.NumberPad,
            Input.OnscreenKeyboardType.NumberPad,
            Input.OnscreenKeyboardType.NumberPad,
        )
        val listTitle = listOf(
            "Name",
            "Deposit Amount",
            "Interest Rate",
            "Deposit Term",
            "Monthly Contribution",
        )

        var ny = 1390f
        listLbl.forEachIndexed { index, label ->
            tmpGroup.addActor(label)
            label.setAlignment(Align.center)
            label.setBounds(75f, ny, 1030f, 124f)
            ny -= 155 + 124

            label.setOnTouchListener(gdxGame.soundUtil) {
                var initialText = ""

                when(index) {
                    0 -> {
                        blockTextFieldListener = { text ->
                            inputData.nName = text.take(20)
                            currentLabel.setText(inputData.nName)
                        }
                        EDIT_DATA_SAVING?.let { initialText = it.nName }
                    }
                    1 -> {
                        blockTextFieldListener = { text ->
                            inputData.amount = text.isNumTake(6)
                            currentLabel.setText(inputData.amount.toSeparate())
                        }
                        EDIT_DATA_SAVING?.let { initialText = it.amount.toString() }
                    }
                    2 -> {
                        blockTextFieldListener = { text ->
                            inputData.rate = text.isNumTake(2)
                            currentLabel.setText(inputData.rate)
                        }
                        EDIT_DATA_SAVING?.let { initialText = it.rate.toString() }
                    }
                    3 -> {
                        blockTextFieldListener = { text ->
                            inputData.term = text.isNumTake(2)
                            currentLabel.setText(inputData.term)
                        }
                        EDIT_DATA_SAVING?.let { initialText = it.term.toString() }
                    }
                    4 -> {
                        blockTextFieldListener = { text ->
                            inputData.contr = text.isNumTake(6)
                            currentLabel.setText(inputData.contr.toSeparate())
                        }
                        EDIT_DATA_SAVING?.let { initialText = it.contr.toString() }
                    }
                }

                currentLabel = label
                Gdx.input.getTextInput(getTextInput(), listTitle[index], initialText, "", listKeyboardType[index])
            }
        }
    }


    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Runnable) {
        //children.onEach { it.clearActions() }
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.run() }
    }

    override fun animHideMain(blockEnd: Runnable) {
        //children.onEach { it.clearActions() }
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.run() }
    }

    // Logic -------------------------------------------------

    private fun navTo(screenName: String) {
        screen.hideScreen {
            gdxGame.navigationManager.navigate(screenName, screen::class.java.name)
        }
    }

    private fun getTextInput() = object : TextInputListener {
        override fun input(text: String?) {
            blockTextFieldListener(text ?: "")
        }

        override fun canceled() {}
    }

    private fun handlerSave(): Boolean {
        return if (inputData.nName != falseInputData.nName &&
            inputData.amount != falseInputData.amount &&
            inputData.rate   != falseInputData.rate &&
            inputData.term   != falseInputData.term &&
            inputData.contr  != falseInputData.contr) {

            log("Всі поля відрізняються!")

            gdxGame.ds_SavingData.update { data ->
                val mData = data.toMutableList()

                OLD_EDITABLE_DATA?.let { mData.remove(it) }
                OLD_EDITABLE_DATA = null

                mData.add(inputData)
                mData
            }

            true
        } else {
            log("Є хоча б одне однакове поле!")
            gdxGame.soundUtil.apply { play(fail) }
            false
        }
    }
}
