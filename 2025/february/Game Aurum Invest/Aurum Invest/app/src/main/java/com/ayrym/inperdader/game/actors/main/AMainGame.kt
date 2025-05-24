package com.ayrym.inperdader.game.actors.main

import com.ayrym.inperdader.game.actors.AAnswer
import com.ayrym.inperdader.game.actors.ABoxCenter
import com.ayrym.inperdader.game.actors.ACountdownTimer
import com.ayrym.inperdader.game.actors.ATmpGroup
import com.ayrym.inperdader.game.actors.button.AButton
import com.ayrym.inperdader.game.screens.GameScreen
import com.ayrym.inperdader.game.screens.MenuScreen
import com.ayrym.inperdader.game.utils.Block
import com.ayrym.inperdader.game.utils.GLOBAL_listDataQuiz
import com.ayrym.inperdader.game.utils.GameColor
import com.ayrym.inperdader.game.utils.TIME_ANIM_SCREEN
import com.ayrym.inperdader.game.utils.actor.animDelay
import com.ayrym.inperdader.game.utils.actor.animHide
import com.ayrym.inperdader.game.utils.actor.animShow
import com.ayrym.inperdader.game.utils.actor.setOnClickListener
import com.ayrym.inperdader.game.utils.advanced.AdvancedMainGroup
import com.ayrym.inperdader.game.utils.font.FontParameter
import com.ayrym.inperdader.game.utils.gdxGame
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.uxo.monaxa.game.actors.checkbox.ACheckBoxGroup

class AMainGame(
    override val screen: GameScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font_30  = screen.fontGenerator_Bold.generateFont(parameter.setSize(30))
    private val font_34  = screen.fontGenerator_Medium.generateFont(parameter.setSize(34))
    private val fontB_38 = screen.fontGenerator_Bold.generateFont(parameter.setSize(38))
    private val fontM_38 = screen.fontGenerator_Medium.generateFont(parameter.setSize(38))

    private val ls_30  = Label.LabelStyle(font_30, GameColor.black_36)
    private val ls_34  = Label.LabelStyle(font_34, Color.WHITE)
    private val lsB_38 = Label.LabelStyle(fontB_38, Color.WHITE)
    private val lsM_38 = Label.LabelStyle(fontM_38, GameColor.black_36)
    private val lsM_38W = Label.LabelStyle(fontM_38, Color.WHITE)

    private val currentDataQuiz = GLOBAL_listDataQuiz[MenuScreen.CURRENT_QUIZ_INDEX]

    private var currentIndex = 0
    private var currentQ     = currentDataQuiz.listQ[currentIndex]
    private var currentListA = currentDataQuiz.listA[currentIndex]

    private val cbg = ACheckBoxGroup()
    private val listName = listOf("А", "Б", "В", "Г")
    private val randomIndex = (0..3).shuffled()

    private val btnBack    = AButton(screen, AButton.Type.Back)
    private val btnLeft    = AButton(screen, AButton.Type.Left)
    private val btnRight   = AButton(screen, AButton.Type.Right)
    private val imgTimer   = Image(gdxGame.assetsAll.timer)
    private val lblTitle   = Label(currentDataQuiz.title, lsB_38)
    private val lblQ       = Label(currentQ, lsM_38)
    private val listLevel  = List(5) { ABoxCenter(screen, it.inc().toString(), ls_34, cbg) }
    private val imgLine    = Image(gdxGame.assetsAll.line)

    private val tmpAnsGroup = ATmpGroup(screen)
    private val listAns     = List(4) { AAnswer(screen, listName[it], lsM_38W, ls_30, currentListA[randomIndex[it]]).also { ans ->
        ans.isWin = randomIndex[it] == 0
    } }

    override fun addActorsOnGroup() {
        screen.stageBack.root.color.a = 0f
        color.a = 0f

        addBtnBack()
        addBtnNext()
        addLblTitle()
        addTimer()
        addLblQ()
        addListLevel()
        addListAns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(17f, 1572f, 107f, 107f)

        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addBtnNext() {
        addActors(btnLeft, btnRight)
        btnLeft.setBounds(50f, 85f, 108f, 108f)
        btnRight.setBounds(643f, 85f, 108f, 108f)

        btnLeft.setOnClickListener {
            currentIndex--
            if (currentIndex < 0) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            } else next()
        }
        btnRight.setOnClickListener {
            currentIndex++
            if (currentIndex >= 5) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            } else next()
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(137f, 1585f, 444f, 98f)
        lblTitle.setAlignment(Align.center or Align.left)
        lblTitle.wrap = true
    }

    private fun addTimer() {
        addActor(imgTimer)
        imgTimer.setBounds(596f, 1600f, 155f, 52f)

        val countDownTimer = ACountdownTimer(screen, currentDataQuiz.time)
        addActor(countDownTimer)
        countDownTimer.setBounds(657f, 1607f, 76f, 34f)
        countDownTimer.startTimer()
        //countDownTimer.restart()
    }

    private fun addLblQ() {
        addActor(lblQ)
        lblQ.setBounds(51f, 1189f, 718f, 78f)
        lblQ.wrap = true
        lblQ.also { it.height = it.prefHeight }

        if (lblQ.height > 78) {
            lblQ.y -= (lblQ.height - 78)
        } else lblQ.y = 1189f
    }

    private fun addListLevel() {
        addActor(imgLine)
        imgLine.setBounds(50f, 1308f, 691f, 6f)

        var nx = 71f
        listLevel.onEachIndexed { index, box ->
            addActor(box)
            box.setBounds(nx, 1357f, 98f, 98f)
            nx += 40 + 98
            if (index == 0) box.select()
        }
    }

    private var currentAns: AAnswer? = null

    private fun addListAns() {
        addActor(tmpAnsGroup)
        val tmgY = lblQ.y
        tmpAnsGroup.setBounds(50f, tmgY - (41 + 446), 719f, 446f)

        var ny = 360f
        listAns.onEachIndexed { index, ans ->
            tmpAnsGroup.addActor(ans)
            ans.setBounds(0f, ny, 719f, 85f)
            ny -= 35 + 85

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
        currentQ     = currentDataQuiz.listQ[currentIndex]

        val randomIndex = (0..3).shuffled()
        currentListA = currentDataQuiz.listA[currentIndex]

        listLevel[currentIndex].select()

        lblQ.setText(currentQ)
        lblQ.also { it.height = it.prefHeight }
        if (lblQ.height > 78) lblQ.y -= (lblQ.height - 78) else lblQ.y = 1189f

        val tmgY = lblQ.y
        tmpAnsGroup.y = tmgY - (41 + 446)

        listAns.onEachIndexed { index, it ->
            it.isWin = randomIndex[index] == 0
            it.updateText(currentListA[randomIndex[index]])
        }

        currentAns?.unselect()
        currentAns = null

    }

}