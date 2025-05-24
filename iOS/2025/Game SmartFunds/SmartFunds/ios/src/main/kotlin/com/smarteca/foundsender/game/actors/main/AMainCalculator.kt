package com.smarteca.foundsender.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.smarteca.foundsender.game.actors.*
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.button.AButtonText
import com.smarteca.foundsender.game.dataStore.DataSaving
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.*
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter

class AMainCalculator(
    override val screen: CalculatorScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font102 = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))
    private val font48  = screen.fontGenerator_SemiBold.generateFont(parameter.setSize(48))

    private val fontM_54  = screen.fontGenerator_Medium.generateFont(parameter.setSize(54))
    private val fontB_60  = screen.fontGenerator_Bold.generateFont(parameter.setSize(60))

    private val ls102 = LabelStyle(font102, Color.WHITE)
    private val ls48  = LabelStyle(font48, GameColor.black_16)

    private val lsM_54 = LabelStyle(fontM_54, Color.WHITE)
    private val lsB_60 = LabelStyle(fontB_60, GameColor.green)

    private val aLogo        = ALogo(screen)
    private val lblTitle     = Label("Deposit Calculator", ls102)
    private val lblResult    = Label("Result", ls102)
    private val btnReset     = AButton(screen, AButton.Type.Reset)
    private val aBottomMenu  = ABottomMenu(screen, ABottomMenu.Type.Calculator)
    private val btnCalcul    = AButtonText(screen, ls48, "Calculate", AButton.Type.Green)

    private val aCalculatorForma = ACalculatorForma(screen)

    private val tmpGroup   = ATmpGroup(screen)
    private val scrollPane = AScrollPane(tmpGroup)

    override fun addActorsOnGroup() {
        color.a = 0f

        addALogo()
        addLblTitle()
        addBtnReset()
        addABottomMenu()
        addBtnCalculate()
        addACalculatorForma()
        addResult()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALogo() {
        addActor(aLogo)
        aLogo.setBounds(364f, 2217f, 452f, 200f)
    }

    private fun addLblTitle() {
        addActors(lblTitle)
        lblTitle.apply {
            setBounds(60f, 1987f, 501f, 124f)
        }
    }

    private fun addBtnReset() {
        addActor(btnReset)
        btnReset.apply {
            setBounds(1009f, 1968f, 161f, 161f)
            setOnClickListener {
                aCalculatorForma.reset()
            }
        }
    }

    private fun addABottomMenu() {
        addActor(aBottomMenu)
        aBottomMenu.setBounds(0f, 107f, 1181f, 148f)

        aBottomMenu.apply {
            blockDashboard = { navTo(DashboardScreen::class.java.name) }
            blockSavings = { navTo(SavingsScreen::class.java.name) }
            blockCalculator = {}
            blockGlossary = { navTo(GlossaryScreen::class.java.name) }
            blockTest = { navTo(TestScreen::class.java.name) }
        }
    }

    private fun addBtnCalculate() {
        addActor(btnCalcul)
        btnCalcul.apply {
            setBounds(75f, 860f, 1030f, 135f)
            setOnClickListener {
                aCalculatorForma.handlerSave()?.let {
                    val dataSaving = DataSaving(
                        "",
                        it.depositAmount,
                        it.interestRate,
                        it.depositTerm,
                        it.monthlyContribution,
                    )
                    val resultGroup = AResultCalculatorGroup(
                        screen,
                        aCalculatorForma.isSimple,
                        dataSaving,
                        lsB_60,
                        lsM_54,
                    )

                    tmpGroup.clear()
                    tmpGroup.addAndFillActor(resultGroup)

                }
            }
        }
    }

    private fun addACalculatorForma() {
        addActor(aCalculatorForma)
        aCalculatorForma.setBounds(75f, 1115f, 1031f, 782f)
    }

    private fun addResult() {
        addActor(lblResult)
        lblResult.setBounds(60f, 668f, 501f, 124f)

        addActor(scrollPane)
        scrollPane.setBounds(54f, 287f, 1073f, 322f)
        tmpGroup.setSize(1040f, 601f)
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

//    private fun getTextInput() = object : TextInputListener {
//        override fun input(text: String?) {
//            blockTextFieldListener(text ?: "")
//        }
//
//        override fun canceled() {}
//    }
//
//    private fun handlerSave(): Boolean {
//        return if (inputData.nName != falseInputData.nName &&
//            inputData.amount != falseInputData.amount &&
//            inputData.rate   != falseInputData.rate &&
//            inputData.term   != falseInputData.term &&
//            inputData.contr  != falseInputData.contr) {
//
//            log("Всі поля відрізняються!")
//
//            gdxGame.ds_SavingData.update { data ->
//                val mData = data.toMutableList()
//
//                EDIT_DATA_SAVING?.let { mData.remove(it) }
//
//                mData.add(inputData)
//                mData
//            }
//
//            true
//        } else {
//            log("Є хоча б одне однакове поле!")
//            false
//        }
//    }
}
