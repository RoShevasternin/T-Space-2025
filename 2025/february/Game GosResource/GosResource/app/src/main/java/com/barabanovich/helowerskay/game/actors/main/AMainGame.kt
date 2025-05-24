package com.barabanovich.helowerskay.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.barabanovich.helowerskay.game.actors.AAnswer
import com.barabanovich.helowerskay.game.actors.button.AButton
import com.barabanovich.helowerskay.game.screens.CategoryScreen
import com.barabanovich.helowerskay.game.screens.GameScreen
import com.barabanovich.helowerskay.game.screens.ResultScreen
import com.barabanovich.helowerskay.game.utils.*
import com.barabanovich.helowerskay.game.utils.actor.animDelay
import com.barabanovich.helowerskay.game.utils.actor.animHide
import com.barabanovich.helowerskay.game.utils.actor.animShow
import com.barabanovich.helowerskay.game.utils.actor.setOnClickListener
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedMainGroup
import com.barabanovich.helowerskay.game.utils.font.FontParameter

class AMainGame(
    override val screen: GameScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font_54   = screen.fontGenerator_Bold.generateFont(parameter.setSize(54))
    private val font_54_r = screen.fontGenerator_Regular.generateFont(parameter.setSize(54))
    private val font_43   = screen.fontGenerator_Regular.generateFont(parameter.setSize(43))

    private val ls_54     = Label.LabelStyle(font_54, GameColor.black_44)
    private val ls_54_r   = Label.LabelStyle(font_54_r, GameColor.black_44)
    private val ls_43     = Label.LabelStyle(font_43, GameColor.black_44)

    private val currentDataQuiz = GLOBAL_listDataQuiz[CategoryScreen.CURRENT_QUIZ_INDEX]

    private var currentIndex = 0
    private var currentQ     = currentDataQuiz.listQ[currentIndex]
    private var currentListA = currentDataQuiz.listA[currentIndex]
    private val randomIndex  = (0..4).shuffled()

    // Actors
    private val imgMain = Image(gdxGame.assetsAll.forma)
    private val btnBack = AButton(screen, AButton.Type.Back)
    private val btnNext = AButton(screen, AButton.Type.Next)

    private val lblQ        = Label(currentQ, ls_54_r)
    private val listAns     = List(5) { AAnswer(screen, ls_43, currentListA[randomIndex[it]]).also { ans ->
        ans.isWin = randomIndex[it] == 0
    } }

    private val lblCounter = Label("1 из 8", ls_54)

    override fun addActorsOnGroup() {
        color.a = 0f

        GameScreen.WIN_COUNT = 0

        addActor(imgMain)
        imgMain.setBounds(53f, 553f, 906f, 1295f)

        addBtnBack()
        addBtnNext()
        addLblTitle()
        addTimer()
        addLblQ()
        addListAns()

        addActor(lblCounter)
        lblCounter.setBounds(427f, 1741f, 158f, 39f)

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(53f, 374f, 906f, 123f)

        btnBack.setOnClickListener {
            currentIndex--
            if (currentIndex < 0) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            } else next()
        }
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(53f, 199f, 906f, 123f)
        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex >= 8) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
                }
            } else next()
        }
    }

    private fun addLblTitle() {
//        addActor(lblTitle)
//        lblTitle.setBounds(68f, 1916f, 703f, 75f)
//        lblTitle.setAlignment(Align.center or Align.left)
//        lblTitle.wrap = true
    }

    private fun addTimer() {
        //addActor(countDownTimer)
        //countDownTimer.setBounds(451f, 1781f, 99f, 47f)
        //countDownTimer.startTimer()
        //countDownTimer.restart()
    }

    private fun addLblQ() {
        addActor(lblQ)
        lblQ.setBounds(62f, 1507f, 888f, 237f)
        lblQ.setAlignment(Align.center)
        lblQ.wrap = true

        //lblQ.also { it.height = it.prefHeight }
        //if (lblQ.height > 78) {
        //    lblQ.y -= (lblQ.height - 78)
        //} else lblQ.y = 1189f
    }

    private var currentAns: AAnswer? = null

    private fun addListAns() {
        var ny = 1355f
        listAns.onEachIndexed { index, ans ->
            addActor(ans)
            ans.setBounds(80f, ny, 852f, 131f)
            ny -= 53 + 131

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
        lblCounter.setText("${currentIndex.inc()} из 8")

        currentQ = currentDataQuiz.listQ[currentIndex]

        val randomIndex = (0..4).shuffled()
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