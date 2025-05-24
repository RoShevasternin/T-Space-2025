package com.finansoviy.gurochek.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.finansoviy.gurochek.game.actors.AAnswer
import com.finansoviy.gurochek.game.actors.ACountdownTimer
import com.finansoviy.gurochek.game.actors.ATmpGroup
import com.finansoviy.gurochek.game.actors.button.AButton
import com.finansoviy.gurochek.game.screens.CategoryScreen
import com.finansoviy.gurochek.game.screens.GameScreen
import com.finansoviy.gurochek.game.screens.ResultScreen
import com.finansoviy.gurochek.game.utils.*
import com.finansoviy.gurochek.game.utils.actor.animDelay
import com.finansoviy.gurochek.game.utils.actor.animHide
import com.finansoviy.gurochek.game.utils.actor.animShow
import com.finansoviy.gurochek.game.utils.actor.setBounds
import com.finansoviy.gurochek.game.utils.actor.setOnClickListener
import com.finansoviy.gurochek.game.utils.advanced.AdvancedMainGroup
import com.finansoviy.gurochek.game.utils.font.FontParameter

class AMainGame(
    override val screen: GameScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font_58  = screen.fontGenerator_Bold.generateFont(parameter.setSize(58))
    private val font_37B = screen.fontGenerator_Bold.generateFont(parameter.setSize(37))
    private val font_37R = screen.fontGenerator_Regular.generateFont(parameter.setSize(37))

    private val ls_58_black = Label.LabelStyle(font_58, GameColor.black_1E)
    private val ls_58_white = Label.LabelStyle(font_58, Color.WHITE)
    private val ls_37_white = Label.LabelStyle(font_37R, Color.WHITE)
    private val ls_37_black = Label.LabelStyle(font_37B, GameColor.black_1E)

    private val currentDataQuiz = GLOBAL_listDataQuiz[CategoryScreen.CURRENT_QUIZ_INDEX]

    private var currentIndex = 0
    private var currentQ     = currentDataQuiz.listQ[currentIndex]
    private var currentListA = currentDataQuiz.listA[currentIndex]
    private val randomIndex  = (0..3).shuffled()

    // Actors
    private val imgMain        = Image(gdxGame.assetsAll.game)
    private val lblTitle       = Label(currentDataQuiz.title, ls_58_black)
    private val btnX           = AButton(screen, AButton.Type.X)
    private val btnNext        = AButton(screen, AButton.Type.Next)
    private val countDownTimer = ACountdownTimer(screen, currentDataQuiz.time)

    private val lblQ        = Label(currentQ, ls_58_white)
    private val listAns     = List(4) { AAnswer(screen, ls_37_black, currentListA[randomIndex[it]]).also { ans ->
        ans.isWin = randomIndex[it] == 0
    } }

    private val lblCounter = Label("Вопрос 1/10", ls_37_white)

    override fun addActorsOnGroup() {
        color.a = 0f

        GameScreen.WIN_COUNT = 0

        addActor(imgMain)
        imgMain.setBounds(53f, 413f, 895f, 1445f)

        addBtnX()
        addBtnNext()
        addLblTitle()
        addTimer()
        addLblQ()
        addListAns()

        addActor(lblCounter)
        lblCounter.setBounds(125f, 1605f, 221f, 47f)

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(825f, 1900f, 107f, 107f)

        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(193f, 226f, 615f, 135f)
        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex >= 10) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
                }
            } else next()
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(68f, 1916f, 703f, 75f)
        lblTitle.setAlignment(Align.center or Align.left)
        lblTitle.wrap = true
    }

    private fun addTimer() {
        addActor(countDownTimer)
        countDownTimer.setBounds(451f, 1781f, 99f, 47f)
        countDownTimer.startTimer()
        //countDownTimer.restart()
    }

    private fun addLblQ() {
        addActor(lblQ)
        lblQ.setBounds(75f, 1211f, 848f, 351f)
        lblQ.setAlignment(Align.center or Align.top)
        lblQ.wrap = true

        //lblQ.also { it.height = it.prefHeight }
        //if (lblQ.height > 78) {
        //    lblQ.y -= (lblQ.height - 78)
        //} else lblQ.y = 1189f
    }

    private var currentAns: AAnswer? = null

    private fun addListAns() {
        var ny = 974f
        listAns.onEachIndexed { index, ans ->
            addActor(ans)
            ans.setBounds(53f, ny, 895f, 147f)
            ny -= 39 + 147

            ans.setOnClickListener {
                currentAns?.unselect()
                currentAns = ans
                currentAns?.select()
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ----------------------------------------------

    private fun next() {
        lblCounter.setText("Вопрос ${currentIndex.inc()}/10")

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