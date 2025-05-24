package com.ayrym.inperdader.game.actors.main

import com.ayrym.inperdader.game.actors.button.AButton
import com.ayrym.inperdader.game.screens.GameScreen
import com.ayrym.inperdader.game.screens.MenuScreen
import com.ayrym.inperdader.game.screens.QuizScreen
import com.ayrym.inperdader.game.utils.Block
import com.ayrym.inperdader.game.utils.GLOBAL_listDataQuiz
import com.ayrym.inperdader.game.utils.GameColor
import com.ayrym.inperdader.game.utils.TIME_ANIM_SCREEN
import com.ayrym.inperdader.game.utils.actor.animDelay
import com.ayrym.inperdader.game.utils.actor.animHide
import com.ayrym.inperdader.game.utils.actor.animShow
import com.ayrym.inperdader.game.utils.advanced.AdvancedMainGroup
import com.ayrym.inperdader.game.utils.font.FontParameter
import com.ayrym.inperdader.game.utils.gdxGame
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align

class AMainQuiz(
    override val screen: QuizScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val fontB_38 = screen.fontGenerator_Bold.generateFont(parameter.setSize(38))
    private val fontM_38 = screen.fontGenerator_Medium.generateFont(parameter.setSize(38))

    private val lsB_38 = Label.LabelStyle(fontB_38, Color.WHITE)
    private val lsM_38 = Label.LabelStyle(fontM_38, GameColor.black_36)

    private val currentDataQuiz = GLOBAL_listDataQuiz[MenuScreen.CURRENT_QUIZ_INDEX]

    private val btnBack    = AButton(screen, AButton.Type.Back)
    private val btnToTest  = AButton(screen, AButton.Type.ToTest)
    private val lblTitle   = Label(currentDataQuiz.title, lsB_38)
    private val lblDescr   = Label(currentDataQuiz.description, lsM_38)
    private val scroll     = ScrollPane(lblDescr)

    override fun addActorsOnGroup() {
        screen.stageBack.root.color.a = 0f
        color.a = 0f

        addBtnBack()
        addBtnToTest()
        addLblTitle()
        addScroll()

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

    private fun addBtnToTest() {
        addActor(btnToTest)
        btnToTest.setBounds(165f, 1367f, 471f, 92f)

        btnToTest.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(137f, 1585f, 623f, 98f)
        lblTitle.setAlignment(Align.center or Align.left)
        lblTitle.wrap = true
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(43f, 0f, 717f, 1340f)
        lblDescr.width = 717f
        lblDescr.wrap = true
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

}