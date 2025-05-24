package com.ayrym.inperdader.game.actors.main

import com.ayrym.inperdader.game.screens.MenuScreen
import com.ayrym.inperdader.game.utils.Block
import com.ayrym.inperdader.game.utils.TIME_ANIM_SCREEN
import com.ayrym.inperdader.game.utils.actor.animDelay
import com.ayrym.inperdader.game.utils.actor.animHide
import com.ayrym.inperdader.game.utils.actor.animShow
import com.ayrym.inperdader.game.utils.advanced.AdvancedMainGroup

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

//    private val imgTitle = Image(gdxGame.assetsAll.aurum)
//    private val btnRandom  = AButton(screen, AButton.Type.Back)


    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        //addImgTitle()
        //addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

//    private fun addImgTitle() {
//        addActor(imgTitle)
//        imgTitle.setBounds(76f, 1165f, 535f, 184f)
//    }
//
//    private fun addBtns() {
//        var nx = 76f
//        var ny = 827f
//
//        listOf(btnRandom, /*btnQuiz1, btnQuiz2, btnQuiz3*/).onEachIndexed { index, btn ->
//            addActor(btn)
//            btn.setBounds(nx, ny, 318f, 276f)
//
//            nx += 3 + 318
//            if (index.inc() % 2 == 0) {
//                nx = 76f
//                ny -= 3 + 276
//            }
//
//            btn.setOnClickListener {
//                //AMainQuiz.quizType = AMainQuiz.QuizType.entries[index]
//
//                screen.hideScreen {
//                    //gdxGame.navigationManager.navigate(listScreenName[index], screen::class.java.name)
//                }
//            }
//        }
//    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}