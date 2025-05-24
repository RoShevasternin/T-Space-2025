package com.smarteca.foundsender.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.button.AButtonText
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.animDelay
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter
import com.smarteca.foundsender.util.IOSUtil

class AMainDashboard(
    override val screen: DashboardScreen,
): AdvancedMainGroup() {

    companion object {
        private var isFirst = true
    }

    private val listDataSaving = gdxGame.ds_SavingData.flow.value
    private val mapSummary     = calculateSummary(listDataSaving)

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font72  = screen.fontGenerator_Bold.generateFont(parameter.setSize(72))
    private val font102 = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))
    private val font48  = screen.fontGenerator_SemiBold.generateFont(parameter.setSize(48))

    private val ls72  = LabelStyle(font72, Color.WHITE)
    private val ls102 = LabelStyle(font102, Color.WHITE)
    private val ls48  = LabelStyle(font48, GameColor.black_16)

    private val totalValue    = mapSummary["Total Invested"]?.toSeparate() ?: 0
    private val monthlyValue  = mapSummary["Monthly Profit"]?.toSeparate() ?: 0
    private val expectedValue = mapSummary["Expected Amount at Closure"]?.toSeparate() ?: 0

    private val aLogo            = ALogo(screen)
    private val lblTitle         = Label("Dashboard", ls102)
    private val btnDialogs       = AButton(screen, AButton.Type.Dialog)
    private val imgDashboardText = Image(gdxGame.assetsAll.DASHBOARD_TEXT)
    private val btnPolicy        = AButtonText(screen, ls48, "Privacy policy", AButton.Type.Green)
    private val aBottomMenu      = ABottomMenu(screen, ABottomMenu.Type.Dashboard)
    private val lblTotal         = Label("$totalValue$", ls72)
    private val lblMonthly       = Label("$monthlyValue$", ls72)
    private val lblExpected      = Label("$expectedValue$", ls72)

    override fun addActorsOnGroup() {
        if (isFirst) {
            isFirst = false

            gdxGame.musicUtil.apply {
                currentMusic = music_def.apply {
                    isLooping = true
                    coff = 0.25f
                }
            }
        }

        color.a = 0f

        addALogo()
        addTitle()
        addDashboard()
        addBtnPolicy()
        addABottomMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALogo() {
        addActor(aLogo)
        aLogo.setBounds(364f, 2217f, 452f, 200f)
    }

    private fun addTitle() {
        addActors(lblTitle, btnDialogs)
        lblTitle.apply {
            setBounds(60f, 1987f, 501f, 124f)
        }
        btnDialogs.setBounds(1004f, 1968f, 161f, 161f)
        btnDialogs.setOnClickListener {
            IOSUtil.sendEmail("smartfunds@yahoo.com")
        }
    }

    private fun addDashboard() {
        addActor(imgDashboardText)
        imgDashboardText.setBounds(66f, 818f, 1049f, 989f)

        addActors(lblTotal, lblMonthly, lblExpected)
        lblTotal.setBounds(66f, 1500f, 1048f, 123f)
        lblMonthly.setBounds(66f, 1129f, 1048f, 123f)
        lblExpected.setBounds(66f, 635f, 1048f, 123f)
    }

    private fun addBtnPolicy() {
        addActor(btnPolicy)
        btnPolicy.apply {
            setBounds(75f, 435f, 1031f, 136f)
            setOnClickListener {
                IOSUtil.openUrlInSafariViewController("https://brightfocuszone.store")
            }
        }
    }

    private fun addABottomMenu() {
        addActor(aBottomMenu)
        aBottomMenu.setBounds(0f, 107f, 1181f, 148f)

        aBottomMenu.apply {
            blockDashboard = { }
            blockSavings = { navTo(SavingsScreen::class.java.name) }
            blockCalculator = { navTo(CalculatorScreen::class.java.name) }
            blockGlossary = { navTo(GlossaryScreen::class.java.name) }
            blockTest = { navTo(TestScreen::class.java.name) }
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
