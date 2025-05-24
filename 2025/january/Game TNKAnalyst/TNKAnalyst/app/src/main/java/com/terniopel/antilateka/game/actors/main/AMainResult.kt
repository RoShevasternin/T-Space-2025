package com.terniopel.antilateka.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.terniopel.antilateka.game.utils.Block
import com.terniopel.antilateka.game.utils.TIME_ANIM_SCREEN
import com.terniopel.antilateka.game.utils.actor.animHideSuspend
import com.terniopel.antilateka.game.utils.actor.animShowSuspend
import com.terniopel.antilateka.game.utils.actor.setOnClickListener
import com.terniopel.antilateka.game.utils.advanced.AdvancedGroup
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen
import com.terniopel.antilateka.game.utils.gdxGame
import com.terniopel.antilateka.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainResult(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgResult = Image(gdxGame.assetsAll.listR.random())
    private val btnResult = Actor()

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgResult()
                addBtnResult()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgResult() {
        addActor(imgResult)
        imgResult.setBounds(54f, 142f, 898f, 1769f)
    }

    private fun addBtnResult() {
        addActor(btnResult)
        btnResult.apply {
            setBounds(97f, 442f, 812f, 193f)
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
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