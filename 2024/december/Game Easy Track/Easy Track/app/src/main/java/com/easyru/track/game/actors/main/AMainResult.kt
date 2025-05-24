package com.easyru.track.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.easyru.track.game.actors.AResult_1
import com.easyru.track.game.actors.AResult_2
import com.easyru.track.game.actors.AResult_3
import com.easyru.track.game.actors.ATestPanel
import com.easyru.track.game.actors.ResultGroup
import com.easyru.track.game.actors.autoLayout.AVerticalGroup
import com.easyru.track.game.actors.autoLayout.AutoLayout
import com.easyru.track.game.actors.button.AButton
import com.easyru.track.game.screens.MenuScreen
import com.easyru.track.game.screens.TestScreen
import com.easyru.track.game.utils.Block
import com.easyru.track.game.utils.TIME_ANIM_SCREEN
import com.easyru.track.game.utils.actor.PosSize
import com.easyru.track.game.utils.actor.animHideSuspend
import com.easyru.track.game.utils.actor.animShowSuspend
import com.easyru.track.game.utils.actor.setBounds
import com.easyru.track.game.utils.advanced.AdvancedGroup
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.font.FontParameter
import com.easyru.track.game.utils.gdxGame
import com.easyru.track.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainResult(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val resultIndex = when {
        TestScreen.ANSWER_COUNT < 4 -> 2
        TestScreen.ANSWER_COUNT in 4..6 -> 1
        else -> 0
    }

    private val fontParameter = FontParameter().setCharacters("Ваш результат")
    private val fontSB_43     = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(43))

    private val lsSB_43 = Label.LabelStyle(fontSB_43, Color.WHITE)

    private val textureResult = listOf(
        gdxGame.assetsAll.a_result,
        gdxGame.assetsAll.b_result,
        gdxGame.assetsAll.c_result,
    )[resultIndex]

    private val resultPosSize = listOf(
        PosSize(100f, 293f, 602f, 242f),
        PosSize(99f, 285f, 603f, 284f),
        PosSize(100f, 288f, 603f, 250f),
    )[resultIndex]

    private val resultActor = listOf<ResultGroup>(
        AResult_1(screen),
        AResult_2(screen),
        AResult_3(screen),
    )[resultIndex]

    private val btnBack   = AButton(screen, AButton.Type.Back)
    private val lblTitle  = Label("Ваш результат", lsSB_43)
    private val btnNext   = AButton(screen, AButton.Type.Next)
    private val imgWhite  = Image(gdxGame.assetsAll.white_result)
    private val imgResult = Image(textureResult)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addBtnBack()
                addLblTitle()
                addBtnNext()

                addImgWhite()
                addImgResult()

                addAndFillActor(resultActor as AdvancedGroup)
            }

            animShowMain()
            runGDX { resultActor.startAnim() }
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(53f, 1529f, 75f, 75f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }

    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(203f, 1550f, 332f, 30f)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(189f, 83f, 424f, 96f)
        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.apply {
                    clearBackStack()
                    navigate(MenuScreen::class.java.name)
                }
            }
        }

    }

    private fun addImgWhite() {
        addActor(imgWhite)
        imgWhite.setBounds(53f, 239f, 696f, 1159f)
    }

    private fun addImgResult() {
        addActor(imgResult)
        imgResult.setBounds(resultPosSize)
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

}