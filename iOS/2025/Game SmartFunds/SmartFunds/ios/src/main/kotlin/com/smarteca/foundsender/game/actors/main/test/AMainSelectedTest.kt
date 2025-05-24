package com.smarteca.foundsender.game.actors.main.test

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.actors.AAnswer
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.button.AButtonText
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
import com.smarteca.foundsender.game.utils.actor.disable
import com.smarteca.foundsender.game.utils.actor.enable
import com.smarteca.foundsender.game.utils.actor.setOnClickListener
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter

class AMainSelectedTest(
    override val screen: SelectedTestScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font102 = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))
    private val font72  = screen.fontGenerator_Bold.generateFont(parameter.setSize(68))
    private val font48  = screen.fontGenerator_Regular.generateFont(parameter.setSize(48))
    private val font48_SB = screen.fontGenerator_SemiBold.generateFont(parameter.setSize(48))

    private val ls102 = LabelStyle(font102, Color.WHITE)
    private val ls72  = LabelStyle(font72, Color.WHITE.cpy().apply { a = 0.80f })
    private val ls48  = LabelStyle(font48, Color.WHITE)
    private val ls48_SB = LabelStyle(font48_SB, GameColor.black_16)

    private val currentDataQuiz = GLOBAL_listDataQuiz[AMainTest.SELECTED_TEST_INDEX]

    private var currentIndex = 0
    private var currentQ     = currentDataQuiz.listQ[currentIndex]
    private var currentListA = currentDataQuiz.listA[currentIndex]
    private val randomIndex  = (0..3).shuffled()

    private val aLogo       = ALogo(screen)
    private val lblTitle    = ALabelAutoFont(screen, GLOBAL_listTestTitle[AMainTest.SELECTED_TEST_INDEX], ls102)
    private val lblQuest    = Label(currentQ, ls72)
    private val aBottomMenu = ABottomMenu(screen, ABottomMenu.Type.Test)
    private val btnAllTest  = AButton(screen, AButton.Type.AllTest)
    private val btnNext     = AButtonText(screen, ls48_SB,"Next question", AButton.Type.Green)
    private val imgForma    = Image(gdxGame.assetsAll.ANSWERS)

    private val listAnswer = List(4) { AAnswer(screen, ls48, currentListA[randomIndex[it]]).also { ans -> ans.isWin = randomIndex[it] == 0 } }

    override fun addActorsOnGroup() {
        color.a = 0f
        SelectedTestScreen.WIN_COUNT = 0

        addALogo()
        addTitle()
        addABottomMenu()
        addBtnAllTest()
        addBtnNext()
        addImgForma()
        addLblQ()
        addListAnswer()

        animShowMain()
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

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(51f, 472f, 1078f, 135f)
        btnNext.setOnClickListener {
            if (currentAns == null) return@setOnClickListener

            currentIndex++
            if (currentIndex >= 10) {
                navTo(TestResultScreen::class.java.name)
            } else next()
        }
    }

    private fun addImgForma() {
        addActor(imgForma)
        imgForma.setBounds(66f, 696f, 1049f, 902f)
    }

    private fun addLblQ() {
        addActor(lblQuest)
        lblQuest.setBounds(66f, 1688f, 1048f, 174f)
        lblQuest.setAlignment(Align.center or Align.left)
        lblQuest.wrap = true

        //lblQ.also { it.height = it.prefHeight }
        //if (lblQ.height > 78) {
        //    lblQ.y -= (lblQ.height - 78)
        //} else lblQ.y = 1189f
    }

    private var currentAns: AAnswer? = null

    private fun addListAnswer() {
        var ny = 1417f
        listAnswer.onEachIndexed { index, ans ->
            addActor(ans)
            ans.setBounds(66f, ny, 1049f, 181f)
            ny -= 60 + 181

            ans.setOnClickListener {
                currentAns?.unselect()
                currentAns = ans
                currentAns?.select()

                listAnswer.forEach { it.disable() }
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

    private fun next() {
        currentQ = currentDataQuiz.listQ[currentIndex]

        val randomIndex = (0..3).shuffled()
        currentListA = currentDataQuiz.listA[currentIndex]

        lblQuest.setText(currentQ)
        //lblQ.also { it.height = it.prefHeight }
        //if (lblQ.height > 78) lblQ.y -= (lblQ.height - 78) else lblQ.y = 1189f

        listAnswer.onEachIndexed { index, it ->
            it.isWin = randomIndex[index] == 0
            it.updateText(currentListA[randomIndex[index]])
        }

        currentAns?.unselect()
        currentAns = null

        listAnswer.forEach { it.enable() }

    }

}
