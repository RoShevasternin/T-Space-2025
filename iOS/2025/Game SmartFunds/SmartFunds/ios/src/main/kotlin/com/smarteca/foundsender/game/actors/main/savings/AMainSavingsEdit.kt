package com.smarteca.foundsender.game.actors.main.savings

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.ASavingGroup
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.button.AButtonText
import com.smarteca.foundsender.game.dataStore.DataSaving
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsEditScreen
import com.smarteca.foundsender.game.screens.savings.SavingsInputScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.*
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter

class AMainSavingsEdit(
    override val screen: SavingsEditScreen,
): AdvancedMainGroup() {

    companion object {
        var EDIT_DATA_SAVINGS: DataSaving = DataSaving("", 0, 0, 0, 0)
    }

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
    private val lblTitle     = Label("Saving", ls102)
    private val btnEdit      = AButtonText(screen, ls48, "Edit", AButton.Type.Green)
    private val btnDelete    = AButton(screen, AButton.Type.Delete)
    private val btnSavings   = AButton(screen, AButton.Type.Savings)
    private val aBottomMenu  = ABottomMenu(screen, ABottomMenu.Type.Savings)
    private val aSavingGroup = ASavingGroup(screen, EDIT_DATA_SAVINGS, lsB_84, lsB_60, lsM_54)

    override fun addActorsOnGroup() {
        color.a = 0f

        addALogo()
        addLblTitle()
        addBtnSavings()
        addBtnEdit()
        addBtnDelete()
        addABottomMenu()
        addSavingGroup()

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
            setBounds(60f, 1875f, 501f, 124f)
        }
    }

    private fun addBtnSavings() {
        addActor(btnSavings)
        btnSavings.apply {
            setBounds(30f, 2022f, 267f, 133f)
            setOnClickListener {
                navTo(SavingsScreen::class.java.name)
            }
        }
    }

    private fun addBtnEdit() {
        addActor(btnEdit)
        btnEdit.apply {
            setBounds(66f, 621f, 1049f, 136f)
            setOnClickListener {
                AMainSavingsInput.EDIT_DATA_SAVING = AMainSavingsEdit.EDIT_DATA_SAVINGS
                navTo(SavingsInputScreen::class.java.name)
            }
        }
    }

    private fun addBtnDelete() {
        addActor(btnDelete)
        btnDelete.apply {
            setBounds(66f, 426f, 1049f, 136f)
            setOnClickListener {
                gdxGame.ds_SavingData.update {
                    val mList = it.toMutableList()
                    mList.remove(EDIT_DATA_SAVINGS)
                    mList
                }
                this@AMainSavingsEdit.animDelay(0.1f) {
                    navTo(SavingsScreen::class.java.name)
                }
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

    private fun addSavingGroup() {
        addActor(aSavingGroup)
        aSavingGroup.setBounds(66f, 995f, 1049f, 760f)
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
