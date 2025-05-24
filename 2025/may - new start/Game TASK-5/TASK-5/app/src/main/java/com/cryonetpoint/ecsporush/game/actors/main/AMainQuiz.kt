package com.cryonetpoint.ecsporush.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cryonetpoint.ecsporush.game.actors.AAnswer
import com.cryonetpoint.ecsporush.game.actors.button.AButton
import com.cryonetpoint.ecsporush.game.actors.button.ATextButton
import com.cryonetpoint.ecsporush.game.screens.MenuScreen
import com.cryonetpoint.ecsporush.game.screens.QuizScreen
import com.cryonetpoint.ecsporush.game.screens.ResultScreen
import com.cryonetpoint.ecsporush.game.utils.*
import com.cryonetpoint.ecsporush.game.utils.actor.animDelay
import com.cryonetpoint.ecsporush.game.utils.actor.animHide
import com.cryonetpoint.ecsporush.game.utils.actor.animShow
import com.cryonetpoint.ecsporush.game.utils.actor.setOnClickListener
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedMainGroup
import com.cryonetpoint.ecsporush.game.utils.font.FontParameter

class AMainQuiz(override val screen: QuizScreen): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font79        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(79))
    private val font68        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(68))
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))

    private val ls79 = Label.LabelStyle(font79, GameColor.yellow)
    private val ls68 = Label.LabelStyle(font68, Color.WHITE)
    private val ls51 = Label.LabelStyle(font51, Color.WHITE)

    private val selectedIndex   = MenuScreen.SELECTED_LVL_INDEX
    private val currentDataQuiz = if (selectedIndex == 0) GLOBAL_listDataQuiz.random() else if(selectedIndex > 2) GLOBAL_listDataQuiz.last() else GLOBAL_listDataQuiz[selectedIndex]

    private var currentIndex = 0
    private var currentQ     = currentDataQuiz.listQ[currentIndex]
    private var currentListA = currentDataQuiz.listA[currentIndex]
    private val randomIndex  = (0..3).shuffled()

    private val btnBack        = AButton(screen, AButton.Type.Back)
    private val lblQuestionNum = Label("01", ls79)
    private val btnAnswer      = AButton(screen, AButton.Type.Answer)

    private val lblQ        = Label(currentQ, ls68)
    private val listAns     = List(4) { AAnswer(screen, ls51, currentListA[randomIndex[it]]).also { ans ->
        ans.isWin = randomIndex[it] == 0
    } }

    override fun addActorsOnGroup() {
        screen.stageBack.root.color.a = 0f
        color.a = 0f

        addBtnBack()
        addLblQuestionNum()
        addBtnNext()
        addLblQ()
        addListAns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(74f, 2043f, 114f, 115f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addLblQuestionNum() {
        addActor(lblQuestionNum)
        lblQuestionNum.setBounds(74f, 1919f, 90f, 97f)
        //lblQuestionNum.setAlignment(Align.center)
    }

    private fun addBtnNext() {
        addActor(btnAnswer)
        btnAnswer.setBounds(74f, 159f, 941f, 158f)
        btnAnswer.setOnClickListener {
            currentIndex++
            if (currentIndex >= 10) {
                 screen.hideScreen {
                     gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
                 }
            } else next()
        }
    }

    private fun addLblQ() {
        addActor(lblQ)
        lblQ.setBounds(74f, 1530f, 940f, 332f)
        lblQ.setAlignment(Align.left or Align.center)
        lblQ.wrap = true
    }

    private var currentAns: AAnswer? = null

    private fun addListAns() {
        var ny = 1190f
        listAns.onEachIndexed { index, ans ->
            addActor(ans)
            ans.setBounds(74f, ny, 941f, 226f)
            ny -= 27 + 226

            ans.setOnClickListener {
                currentAns?.unselect()
                currentAns = ans
                currentAns?.select()
            }
        }
    }


    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ----------------------------------------------

    private fun next() {
        val numText = if (currentIndex.inc() >= 10) "10" else "0${currentIndex.inc()}"
        lblQuestionNum.setText(numText)

        currentQ = currentDataQuiz.listQ[currentIndex]

        val randomIndex = (0..3).shuffled()
        currentListA = currentDataQuiz.listA[currentIndex]

        lblQ.setText(currentQ)
        //lblQ.also { it.height = it.prefHeight }
        //if (lblQ.height > 78) lblQ.y -= (lblQ.height - 78) else lblQ.y = 1189f

        listAns.onEachIndexed { index, it ->
            it.isWin = randomIndex[index] == 0
            it.updateText(currentListA[randomIndex[index]])
        }

        currentAns?.unselect()
        currentAns = null

    }

}