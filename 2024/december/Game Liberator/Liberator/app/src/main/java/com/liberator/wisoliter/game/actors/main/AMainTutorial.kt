package com.liberator.wisoliter.game.actors.main

import com.liberator.wisoliter.game.actors.dialog.ADialogBottom
import com.liberator.wisoliter.game.actors.tutorial.ATutorial_1
import com.liberator.wisoliter.game.actors.tutorial.ATutorial_2
import com.liberator.wisoliter.game.actors.tutorial.ATutorial_3
import com.liberator.wisoliter.game.actors.world.AWorld
import com.liberator.wisoliter.game.actors.world.AWorldScrollable
import com.liberator.wisoliter.game.screens.GameScreen
import com.liberator.wisoliter.game.screens.TutorialScreen
import com.liberator.wisoliter.game.utils.*
import com.liberator.wisoliter.game.utils.actor.*
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainTutorial(
    override val screen: AdvancedScreen,
    val aDialogBottom  : ADialogBottom,
    val aWorld         : AWorldScrollable,
): AdvancedGroup() {

    var currentTutorialIndex = 0

    private val tutorial_1 = ATutorial_1(screen)
    private val tutorial_2 = ATutorial_2(screen)
    private val tutorial_3 = ATutorial_3(screen)

    private val listTutorials = listOf(
        tutorial_1,
        tutorial_2,
        tutorial_3,
    )

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addTutorials()

                handlerDialogBottom()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addTutorials() {
        listTutorials.onEachIndexed { index, tut ->
            addAndFillActor(tut)
            if (index != 0) {
                tut.color.a = 0f
                tut.disable()
            }
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Logic ------------------------------------------------

    private fun handlerDialogBottom() {
        val listText = listOf(
            "Покупайте в магазине технику и пополняйте xp чтобы продолжать экспансию",
            "Чтобы начать освобождать страну, просто нажмите на нее"
        )
        val listY = listOf(194f, 244f)

        aDialogBottom.blockConty = {
            if (currentTutorialIndex < 2) {
                if (currentTutorialIndex == 1) {
                    screen.setBackBackground(TextureEmpty.region)
                    gdxGame.backgroundColor = GameColor.blue

                    aWorld.animShow(TIME_ANIM_SCREEN) {
                        aWorld.tutorial()
                        aWorld.scrollToPos()
                        aWorld.enable()
                    }
                }

                aDialogBottom.setNewTT(
                    title = "Обучение",
                    text  = listText[currentTutorialIndex],
                    newY  = listY[currentTutorialIndex]
                )

                listTutorials[currentTutorialIndex].apply {
                    animHide(TIME_ANIM_SCREEN)
                    disable()
                }

                currentTutorialIndex++

                listTutorials[currentTutorialIndex].apply {
                    animShow(TIME_ANIM_SCREEN)
                    enable()
                }
            } else {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }

    }
}