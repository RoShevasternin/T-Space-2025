package com.smarters.moneyes.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.smarters.moneyes.game.actors.button.AButton
import com.smarters.moneyes.game.screens.MenuScreen
import com.smarters.moneyes.game.utils.Block
import com.smarters.moneyes.game.utils.TIME_ANIM_SCREEN
import com.smarters.moneyes.game.utils.actor.PosSize
import com.smarters.moneyes.game.utils.actor.animHideSuspend
import com.smarters.moneyes.game.utils.actor.animShowSuspend
import com.smarters.moneyes.game.utils.actor.setBounds
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.gdxGame
import com.smarters.moneyes.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainResult(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val randomResultIndex = (0..2).random()
    private val texture = listOf(
        gdxGame.assetsAll.r1,
        gdxGame.assetsAll.r5,
        gdxGame.assetsAll.r10,
    )[randomResultIndex]

    private val imgResult = Image(texture)
    private val btnNext   = AButton(screen, AButton.Type.Next)

    // Field
    private val posSize = listOf(
        PosSize(112f, 468f, 926f, 1828f),
        PosSize(208f, 440f, 737f, 1792f),
        PosSize(116f, 440f, 921f, 1773f),
    )[randomResultIndex]
    private val vBalance = listOf(0, 250, 500)[randomResultIndex]

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addResult()
                addBtnNext()

                balance += vBalance
                range++
                listLevel[MenuScreen.SELECTED_LEVEL_INDEX] = true
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addResult() {
        addActor(imgResult)
        imgResult.setBounds(posSize)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(69f, 223f, 1013f, 154f)

        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
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

}