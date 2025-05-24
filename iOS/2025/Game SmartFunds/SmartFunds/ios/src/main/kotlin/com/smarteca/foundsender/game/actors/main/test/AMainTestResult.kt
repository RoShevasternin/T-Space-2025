package com.smarteca.foundsender.game.actors.main.test

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.label.ALabelAutoFont
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.SelectedTestScreen
import com.smarteca.foundsender.game.screens.test.TestResultScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.animDelay
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class AMainTestResult(
    override val screen: TestResultScreen,
): AdvancedMainGroup() {

    private val resultCount = if (SelectedTestScreen.WIN_COUNT > 10) 10 else SelectedTestScreen.WIN_COUNT.absoluteValue

    private val resultIndex = when {
        resultCount <= 4  -> 0
        resultCount <= 8  -> 1
        resultCount <= 10 -> 2
        else -> 0
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font102   = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))
    private val font102_H = screen.fontGenerator_Heavy.generateFont(parameter.setSize(102))

    private val ls102   = LabelStyle(font102, Color.WHITE)
    private val ls102_H = LabelStyle(font102_H, GameColor.green)

    private val aLogo       = ALogo(screen)
    private val lblTitle    = ALabelAutoFont(screen, GLOBAL_listTestTitle[AMainTest.SELECTED_TEST_INDEX], ls102)
    private val aBottomMenu = ABottomMenu(screen, ABottomMenu.Type.Test)
    private val btnAllTest  = AButton(screen, AButton.Type.AllTest)
    private val imgResult   = Image(gdxGame.assetsAll.listResult[resultIndex])
    private val lblResult   = Label(resultCount.toString(), ls102_H)

    override fun addActorsOnGroup() {
        color.a = 0f

        addALogo()
        addTitle()
        addABottomMenu()
        addBtnAllTest()
        addImgResult()
        addLblResult()

        val resultPercent = (resultCount / 10f) * 100f
        gdxGame.ds_TestProgressData.update {
            val mList = it.toMutableList()
            mList[AMainTest.SELECTED_TEST_INDEX] = resultPercent.roundToInt()
            mList
        }

        animShowMain {
            gdxGame.soundUtil.apply { play(win_game) }
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addALogo() {
        addActor(aLogo)
        aLogo.setBounds(364f, 2217f, 452f, 200f)
    }

    private fun addTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(66f, 1866f, 1048f, 133f)
    }

    private fun addABottomMenu() {
        addActor(aBottomMenu)
        aBottomMenu.setBounds(0f, 107f, 1181f, 148f)

        aBottomMenu.apply {
            blockDashboard = { navTo(DashboardScreen::class.java.name) }
            blockSavings = { navTo(SavingsScreen::class.java.name) }
            blockCalculator = { navTo(CalculatorScreen::class.java.name) }
            blockGlossary = { navTo(GlossaryScreen::class.java.name) }
            blockTest = {}
        }
    }

    private fun addBtnAllTest() {
        addActor(btnAllTest)
        btnAllTest.setBounds(30f, 2018f, 264f, 133f)
        btnAllTest.setOnClickListener {
            navTo(TestScreen::class.java.name)
        }
    }

    private fun addImgResult() {
        addActor(imgResult)
        imgResult.setBounds(66f, 908f, 1049f, 728f)
    }

    private fun addLblResult() {
        addActor(lblResult)
        lblResult.setBounds(449f, 1443f, 120f, 133f)
        lblResult.setAlignment(Align.center)
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
