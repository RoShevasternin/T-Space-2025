package com.cburaktev.klavregasd.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cburaktev.klavregasd.game.actors.AAnswer
import com.cburaktev.klavregasd.game.actors.button.AButton
import com.cburaktev.klavregasd.game.screens.TestResultScreen
import com.cburaktev.klavregasd.game.screens.TestScreen
import com.cburaktev.klavregasd.game.screens.TestSelectScreen
import com.cburaktev.klavregasd.game.utils.Block
import com.cburaktev.klavregasd.game.utils.GLOBAL_listCategoryName
import com.cburaktev.klavregasd.game.utils.GLOBAL_listDataTest
import com.cburaktev.klavregasd.game.utils.GameColor
import com.cburaktev.klavregasd.game.utils.TIME_ANIM_SCREEN
import com.cburaktev.klavregasd.game.utils.actor.animDelay
import com.cburaktev.klavregasd.game.utils.actor.animHide
import com.cburaktev.klavregasd.game.utils.actor.animShow
import com.cburaktev.klavregasd.game.utils.actor.disable
import com.cburaktev.klavregasd.game.utils.actor.enable
import com.cburaktev.klavregasd.game.utils.actor.setOnClickListener
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedMainGroup
import com.cburaktev.klavregasd.game.utils.font.FontParameter
import com.cburaktev.klavregasd.game.utils.gdxGame
import kotlin.collections.get

class AMainTest(
    override val screen: TestScreen,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(51))
    private val font62        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(62))
    private val font46        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(46))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_21)
    private val ls62 = Label.LabelStyle(font62, GameColor.black_21)
    private val ls46 = Label.LabelStyle(font46, GameColor.black_21)

    private val currentDataTest = GLOBAL_listDataTest[TestSelectScreen.SELECTED_TEST_INDEX]

    private var currentIndex = 0
    private var currentQ     = currentDataTest.listQ[currentIndex]
    private var currentListA = currentDataTest.listA[currentIndex]
    private val randomIndex  = (0..3).shuffled()

    private val imgSepar    = Image(screen.drawerUtil.getTexture(GameColor.black_21))
    private val lblCounter  = Label("1/10", ls51)
    private val btnX        = AButton(screen, AButton.Type.X)

    private val lblQ        = Label(currentQ, ls62)
    private val listAns     = List(4) { AAnswer(screen, ls46, currentListA[randomIndex[it]]).also { ans ->
        ans.isWin = randomIndex[it] == 0
    } }

    override fun addActorsOnGroup() {
        color.a = 0f

        TestScreen.WIN_COUNT = 0

        addImgSepar()
        addLblCounterAndX()
        addLblQ()
        addListAns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgSepar() {
        addActor(imgSepar)
        imgSepar.setBounds(58f, 1545f, 854f, 3f)
    }

    private fun addLblCounterAndX() {
        addActor(lblCounter)
        lblCounter.setBounds(58f, 1833f, 118f, 83f)

        addActor(btnX)
        btnX.apply {
            setBounds(853f, 1837f, 77f, 77f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

    private fun addLblQ() {
        addActor(lblQ)
        lblQ.setBounds(58f, 1649f, 853f, 99f)
        lblQ.setAlignment(Align.center)
        lblQ.wrap = true
    }

    private var currentAns: AAnswer? = null

    private fun addListAns() {
        var ny = 1300f
        listAns.onEachIndexed { index, ans ->
            addActor(ans)
            ans.setBounds(47f, ny, 875f, 159f)
            ny -= 20 + 159

            ans.setOnClickListener {
                currentAns?.unselect()
                currentAns = ans
                currentAns?.select()

                ans.disable()

                ans.animDelay(0.5f) {
                    ans.enable()

                    currentIndex++
                    if (currentIndex >= 10) {
                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(TestResultScreen::class.java.name)
                    }
                    } else next()
                }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ----------------------------------------------

    private fun next() {
        lblCounter.setText("${currentIndex.inc()}/10")

        currentQ = currentDataTest.listQ[currentIndex]

        val randomIndex = (0..3).shuffled()
        currentListA = currentDataTest.listA[currentIndex]

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