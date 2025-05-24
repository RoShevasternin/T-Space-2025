package com.borubashka.arsemajeg.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.borubashka.arsemajeg.game.actors.AAnswer
import com.borubashka.arsemajeg.game.actors.button.AButton
import com.borubashka.arsemajeg.game.actors.button.ATextButton
import com.borubashka.arsemajeg.game.actors.progress.AProgressBig
import com.borubashka.arsemajeg.game.screens.LevelsScreen
import com.borubashka.arsemajeg.game.screens.QuizScreen
import com.borubashka.arsemajeg.game.screens.ResultScreen
import com.borubashka.arsemajeg.game.utils.Block
import com.borubashka.arsemajeg.game.utils.GLOBAL_listDataQuiz
import com.borubashka.arsemajeg.game.utils.GameColor
import com.borubashka.arsemajeg.game.utils.TIME_ANIM_SCREEN
import com.borubashka.arsemajeg.game.utils.actor.animDelay
import com.borubashka.arsemajeg.game.utils.actor.animHide
import com.borubashka.arsemajeg.game.utils.actor.animShow
import com.borubashka.arsemajeg.game.utils.actor.disable
import com.borubashka.arsemajeg.game.utils.actor.enable
import com.borubashka.arsemajeg.game.utils.actor.setOnClickListener
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedMainGroup
import com.borubashka.arsemajeg.game.utils.font.FontParameter
import com.borubashka.arsemajeg.game.utils.gdxGame
import kotlin.collections.get

class AMainQuiz(override val screen: QuizScreen): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font39        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(39))
    private val font49        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(49))
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))

    private val ls39  = Label.LabelStyle(font39, Color.WHITE)
    private val ls49  = Label.LabelStyle(font49, GameColor.black)
    private val ls51B = Label.LabelStyle(font51, GameColor.blue)
    private val ls51G = Label.LabelStyle(font51, GameColor.gray)

    private val currentDataQuiz = GLOBAL_listDataQuiz[LevelsScreen.SELECTED_LVL_INDEX]

    private var currentIndex = 0
    private var currentQ     = currentDataQuiz.listQ[currentIndex]
    private var currentListA = currentDataQuiz.listA[currentIndex]
    private val randomIndex  = (0..3).shuffled()

    private val btnBack        = AButton(screen, AButton.Type.Back)
    private val progress       = AProgressBig(screen)
    private val lblQuestionNum = Label("Вопрос 1", ls49)
    private val btnNext        = ATextButton(screen, "Дальше", ls51G, AButton.Type.White)
    private val imgPanel       = Image(gdxGame.assetsAll.panel_white)

    private val lblQ        = Label(currentQ, ls51B)
    private val listAns     = List(4) { AAnswer(screen, ls39, currentListA[randomIndex[it]]).also { ans ->
        ans.isWin = randomIndex[it] == 0
    } }

    override fun addActorsOnGroup() {
        screen.stageBack.root.color.a = 0f
        color.a = 0f

        addBtnBack()
        addLblQuestionNum()
        addProgress()
        addBtnNext()
        addImgPanel()
        addLblQ()
        addListAns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(37f, 1830f, 84f, 84f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addProgress() {
        val imgBack = Image(gdxGame.assetsAll.white_back_big)
        addActor(imgBack)
        imgBack.setBounds(49f, 1770f, 828f, 38f)

        addActor(progress)
        progress.setBounds(49f, 1770f, 828f, 38f)
    }

    private fun addLblQuestionNum() {
        addActor(lblQuestionNum)
        lblQuestionNum.setBounds(360f, 1831f, 207f, 80f)
        lblQuestionNum.setAlignment(Align.center)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(49f, 111f, 828f, 124f)
        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex >= 5) {
                 screen.hideScreen {
                     gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
                 }
            } else next()
        }
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(49f, 1123f, 828f, 475f)
    }

    private fun addLblQ() {
        addActor(lblQ)
        lblQ.setBounds(49f, 1123f, 828f, 475f)
        lblQ.setAlignment(Align.center)
        lblQ.wrap = true
    }

    private var currentAns: AAnswer? = null

    private fun addListAns() {
        var ny = 938f
        listAns.onEachIndexed { index, ans ->
            addActor(ans)
            ans.setBounds(49f, ny, 828f, 124f)
            ny -= 61 + 124

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

    private val oneStep = 100 / 5

    private fun next() {
        lblQuestionNum.setText("Вопрос ${currentIndex.inc()}")
        progress.progressPercentFlow.value = (oneStep * currentIndex).toFloat()

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