package com.vectorvesta.bronfinteh.game.actors.main

import com.vectorvesta.bronfinteh.game.actors.APanel
import com.vectorvesta.bronfinteh.game.actors.AVision
import com.vectorvesta.bronfinteh.game.actors.button.AButton
import com.vectorvesta.bronfinteh.game.screens.HistoryScreen
import com.vectorvesta.bronfinteh.game.screens.MenuScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorDepositScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorInvestmentsScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorLeasingScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorLoanScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorMortgageScreen
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.TIME_ANIM_SCREEN
import com.vectorvesta.bronfinteh.game.utils.actor.animDelay
import com.vectorvesta.bronfinteh.game.utils.actor.animHide
import com.vectorvesta.bronfinteh.game.utils.actor.animShow
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainGroup
import com.vectorvesta.bronfinteh.game.utils.gdxGame

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val aPanel  = APanel(screen, gdxGame.assetsAll.main)
    private val aVision = AVision(screen)

    private val btnMORTGAGE    = AButton(screen, AButton.Type.MORTGAGE)
    private val btnLOAN        = AButton(screen, AButton.Type.LOAN)
    private val btnINVESTMENTS = AButton(screen, AButton.Type.INVESTMENTS)
    private val btnDEPOSIT     = AButton(screen, AButton.Type.DEPOSIT)
    private val btnLEASING     = AButton(screen, AButton.Type.LEASING)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanel()
        addAVision()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanel() {
        addActor(aPanel)
        aPanel.setBounds(216f, 1765f, 469f, 97f)
        aPanel.blockHist = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAVision() {
        addActor(aVision)
        aVision.setBounds(118f, 1126f, 672f, 528f)
    }

    private fun addBtns() {
        val listCalculatorName = listOf(
            CalculatorMortgageScreen::class.java.name,
            CalculatorLoanScreen::class.java.name,
            CalculatorInvestmentsScreen::class.java.name,
            CalculatorDepositScreen::class.java.name,
        )

        var nx = 48f
        var ny = 683f

        listOf(btnMORTGAGE, btnLOAN, btnINVESTMENTS, btnDEPOSIT).onEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(nx, ny, 390f, 316f)

            nx += 23 + 390
            if (index.inc() % 2 == 0) {
                nx = 48f
                ny -= 23 + 316
            }

            btn.setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(listCalculatorName[index], screen::class.java.name)
                }
            }
        }

        addActor(btnLEASING)
        btnLEASING.apply {
            setBounds(48f, 79f, 805f, 242f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(CalculatorLeasingScreen::class.java.name, screen::class.java.name)
                }
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

}