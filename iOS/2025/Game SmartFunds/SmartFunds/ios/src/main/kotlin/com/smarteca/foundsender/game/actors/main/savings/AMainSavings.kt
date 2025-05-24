package com.smarteca.foundsender.game.actors.main.savings

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.ASavingGroup
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.button.AButtonText
import com.smarteca.foundsender.game.actors.AScrollPane
import com.smarteca.foundsender.game.actors.autoLayout.AVerticalGroup
import com.smarteca.foundsender.game.dataStore.DataSaving
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsEditScreen
import com.smarteca.foundsender.game.screens.savings.SavingsInputScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.GameColor
import com.smarteca.foundsender.game.utils.TIME_ANIM_SCREEN
import com.smarteca.foundsender.game.utils.actor.animDelay
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.actor.setOnTouchListener
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter
import com.smarteca.foundsender.game.utils.gdxGame

var OLD_EDITABLE_DATA: DataSaving? = null

class AMainSavings(
    override val screen: SavingsScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font102 = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))
    private val font48  = screen.fontGenerator_SemiBold.generateFont(parameter.setSize(48))

    private val fontM_54  = screen.fontGenerator_Medium.generateFont(parameter.setSize(54))
    private val fontB_60  = screen.fontGenerator_Bold.generateFont(parameter.setSize(60))
    private val fontB_84  = screen.fontGenerator_Bold.generateFont(parameter.setSize(84))

    private val ls102 = LabelStyle(font102, Color.WHITE)
    private val ls48  = LabelStyle(font48, GameColor.black_16)

    private val lsM_54 = LabelStyle(fontM_54, Color.WHITE)
    private val lsB_60 = LabelStyle(fontB_60, GameColor.green)
    private val lsB_84 = LabelStyle(fontB_84, GameColor.green)

    private val aLogo        = ALogo(screen)
    private val lblTitle     = Label("Savings", ls102)
    private val btnCalculate = AButtonText(screen, ls48, "Calculate a saving", AButton.Type.Green)
    private val aBottomMenu  = ABottomMenu(screen, ABottomMenu.Type.Savings)
    private val lblAll       = Label("All savings", ls102)

    private val listDataSaving = gdxGame.ds_SavingData.flow.value

    private val verticalGroup    = AVerticalGroup(screen, 90f, isWrap = true)
    private val scrollPane       = AScrollPane(verticalGroup)
    private val listASavingGroup = List(listDataSaving.size) { ASavingGroup(screen, listDataSaving[it], lsB_84, lsB_60, lsM_54) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addALogo()
        addLblTitle()
        addBtnCalculate()
        addLblAll()
        addABottomMenu()
        addScroll()

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

    private fun addBtnCalculate() {
        addActor(btnCalculate)
        btnCalculate.apply {
            setBounds(75f, 1782f, 1031f, 136f)
            setOnClickListener {
                navTo(SavingsInputScreen::class.java.name)
            }
        }
    }

    private fun addLblAll() {
        addActors(lblAll)
        lblAll.apply {
            setBounds(60f, 1590f, 501f, 124f)
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

    private fun addScroll() {
        addActor(scrollPane)
        scrollPane.setBounds(66f, 287f, 1048f, 1213f)
        verticalGroup.setSize(1048f, 1213f)

        listASavingGroup.reversed().onEachIndexed { index, aSavingGroup ->
            aSavingGroup.setSize(1049f, 760f)
            verticalGroup.addActor(aSavingGroup)

            aSavingGroup.setOnTouchListener(gdxGame.soundUtil) {
                AMainSavingsEdit.EDIT_DATA_SAVINGS = DataSaving(
                    aSavingGroup.dataSaving.nName,
                    aSavingGroup.dataSaving.amount,
                    aSavingGroup.dataSaving.rate,
                    aSavingGroup.dataSaving.term,
                    aSavingGroup.dataSaving.contr,
                )
                OLD_EDITABLE_DATA = aSavingGroup.dataSaving
                navTo(SavingsEditScreen::class.java.name)
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

}
