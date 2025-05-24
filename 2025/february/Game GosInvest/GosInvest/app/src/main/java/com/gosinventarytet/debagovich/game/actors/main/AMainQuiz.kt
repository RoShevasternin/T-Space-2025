package com.gosinventarytet.debagovich.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.gosinventarytet.debagovich.game.actors.button.AButton
import com.gosinventarytet.debagovich.game.actors.checkbox.ACheckBox
import com.gosinventarytet.debagovich.game.data.DataQuiz
import com.gosinventarytet.debagovich.game.screens.QuizScreen
import com.gosinventarytet.debagovich.game.screens.ResultScreen
import com.gosinventarytet.debagovich.game.utils.Block
import com.gosinventarytet.debagovich.game.utils.GLOBAL_listQuiz_01
import com.gosinventarytet.debagovich.game.utils.GLOBAL_listQuiz_02
import com.gosinventarytet.debagovich.game.utils.GLOBAL_listQuiz_03
import com.gosinventarytet.debagovich.game.utils.GameColor
import com.gosinventarytet.debagovich.game.utils.TIME_ANIM_SCREEN
import com.gosinventarytet.debagovich.game.utils.actor.animDelay
import com.gosinventarytet.debagovich.game.utils.actor.animHide
import com.gosinventarytet.debagovich.game.utils.actor.animShow
import com.gosinventarytet.debagovich.game.utils.actor.disable
import com.gosinventarytet.debagovich.game.utils.actor.setOnClickListener
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedMainGroup
import com.gosinventarytet.debagovich.game.utils.font.FontParameter
import com.gosinventarytet.debagovich.game.utils.gdxGame
import com.uxo.monaxa.game.actors.checkbox.ACheckBoxGroup

class AMainQuiz(
    override val screen: QuizScreen,
): AdvancedMainGroup() {

    companion object {
        var quizType = QuizType.Random
    }

    enum class QuizType {
        Random, Quiz1, Quiz2, Quiz3, All
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font59 = screen.fontGenerator_Medium.generateFont(parameter.setSize(59))
    private val font50 = screen.fontGenerator_Bold.generateFont(parameter.setSize(50))
    private val font38 = screen.fontGenerator_Bold.generateFont(parameter.setSize(38))

    private val ls59 = Label.LabelStyle(font59, GameColor.blue_2E)
    private val ls50 = Label.LabelStyle(font50, Color.WHITE.cpy().apply { a = 0.80f })
    private val ls38 = Label.LabelStyle(font38, Color.WHITE.cpy().apply { a = 0.80f })

    private val lblCount = Label("01", ls59)
    private val btnBack  = AButton(screen, AButton.Type.Back)
    private val lblQuest = Label("", ls50)
    private val btnAns   = AButton(screen, AButton.Type.Ans)

    private val listImgAns = List(4) { Image(gdxGame.assetsAll.select) }
    private val listBox    = List(4) { ACheckBox(screen, ACheckBox.Type.ITEM) }
    private val listLbl    = List(4) { Label("", ls38) }

    // Field

    private val listAllQuizData = listOf(
        GLOBAL_listQuiz_01,
        GLOBAL_listQuiz_02,
        GLOBAL_listQuiz_03,
    )

    private val currentQuizDataList: List<DataQuiz> = when(quizType) {
        QuizType.Random -> listAllQuizData.random()
        QuizType.Quiz1  -> listAllQuizData[0]
        QuizType.Quiz2  -> listAllQuizData[1]
        QuizType.Quiz3  -> listAllQuizData[2]
        QuizType.All    -> listAllQuizData.flatten().shuffled().take(10)
    }.shuffled()

    private var currentIndex    = 0
    private var currentQuizData = currentQuizDataList[currentIndex]

    override fun addActorsOnGroup() {
        color.a = 0f

        addLblCount()
        addBtnBack()
        addLblQuest()
        addAns()
        addBtnAns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblCount() {
        addActor(lblCount)
        lblCount.setBounds(59f, 1505f, 77f, 71f)
        lblCount.setAlignment(Align.center)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(55f, 1609f, 85f, 85f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addLblQuest() {
        addActor(lblQuest)
        lblQuest.setBounds(78f, 1046f, 635f, 427f)
        lblQuest.setAlignment(Align.top or Align.left)
        lblQuest.wrap = true

        lblQuest.setText(currentQuizData.quest)
    }

    private fun addBtnAns() {
        addActor(btnAns)
        btnAns.setBounds(168f, 80f, 458f, 117f)
        btnAns.setOnClickListener { next() }
    }

    private fun addAns() {
        var ny  = 845f
        val cbg = ACheckBoxGroup()

        listImgAns.onEachIndexed { index, box ->
            addActor(box)
            box.setBounds(78f, ny, 635f, 168f)

            addActor(listBox[index])
            listBox[index].checkBoxGroup = cbg
            listBox[index].setBounds(145f, ny + 72f, 23f, 23f)
            listBox[index].disable()

            addActor(listLbl[index])
            listLbl[index].setBounds(213f, ny + 15f, 475f, 136f)
            listLbl[index].setAlignment(Align.center or Align.left)
            listLbl[index].wrap = true
            listLbl[index].disable()
            listLbl[index].setText(currentQuizData.listAns[index])

            ny -= 20 + 168

            box.setOnClickListener(gdxGame.soundUtil) {
                if (listBox[index].checkFlow.value) {
                    listBox[index].uncheck()
                } else {
                    listBox[index].check()
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

    // Logic -------------------------------------------------------

    private fun next() {
        currentIndex++

        if (currentIndex >= 10) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
            }
        } else {
            currentQuizData = currentQuizDataList[currentIndex]

            val counter = if (currentIndex == 9) "10" else "0${currentIndex.inc()}"
            lblCount.setText(counter)

            lblQuest.setText(currentQuizData.quest)

            listLbl.onEachIndexed { index, lbl ->
                lbl.setText(currentQuizData.listAns[index])
            }
            listBox.onEach { it.uncheck() }
        }
    }

}