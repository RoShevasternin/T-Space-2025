package com.gosinventarytet.debagovich.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gosinventarytet.debagovich.game.actors.button.AButton
import com.gosinventarytet.debagovich.game.screens.MenuScreen
import com.gosinventarytet.debagovich.game.screens.QuizScreen
import com.gosinventarytet.debagovich.game.utils.Block
import com.gosinventarytet.debagovich.game.utils.TIME_ANIM_SCREEN
import com.gosinventarytet.debagovich.game.utils.actor.animDelay
import com.gosinventarytet.debagovich.game.utils.actor.animHide
import com.gosinventarytet.debagovich.game.utils.actor.animShow
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedMainGroup
import com.gosinventarytet.debagovich.game.utils.gdxGame

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val imgTitle = Image(gdxGame.assetsAll.title)
    private val btnRandom  = AButton(screen, AButton.Type.Random)
    private val btnQuiz1   = AButton(screen, AButton.Type.Quiz_01)
    private val btnQuiz2   = AButton(screen, AButton.Type.Quiz_02)
    private val btnQuiz3   = AButton(screen, AButton.Type.Quiz_03)
    private val btnAll     = AButton(screen, AButton.Type.All)


    override fun addActorsOnGroup() {
        color.a = 0f

        addImgTitle()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(76f, 1165f, 535f, 184f)
    }

    private fun addBtns() {
        val listScreenName = listOf(
            QuizScreen::class.java.name,
            QuizScreen::class.java.name,
            QuizScreen::class.java.name,
            QuizScreen::class.java.name,
        )

        var nx = 76f
        var ny = 827f

        listOf(btnRandom, btnQuiz1, btnQuiz2, btnQuiz3).onEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(nx, ny, 318f, 276f)

            nx += 3 + 318
            if (index.inc() % 2 == 0) {
                nx = 76f
                ny -= 3 + 276
            }

            btn.setOnClickListener {
                AMainQuiz.quizType = AMainQuiz.QuizType.entries[index]

                screen.hideScreen {
                    gdxGame.navigationManager.navigate(listScreenName[index], screen::class.java.name)
                }
            }
        }

        addActor(btnAll)
        btnAll.apply {
            setBounds(80f, 374f, 636f, 170f)
            setOnClickListener {
                AMainQuiz.quizType = AMainQuiz.QuizType.All

                screen.hideScreen {
                    gdxGame.navigationManager.navigate(QuizScreen::class.java.name, screen::class.java.name)
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

}