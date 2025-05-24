package com.simsim.capitalsim.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.simsim.capitalsim.game.screens.MenuScreen
import com.simsim.capitalsim.game.screens.SimScreen
import com.simsim.capitalsim.game.utils.Block
import com.simsim.capitalsim.game.utils.TIME_ANIM_SCREEN
import com.simsim.capitalsim.game.utils.actor.animHideSuspend
import com.simsim.capitalsim.game.utils.actor.animShowSuspend
import com.simsim.capitalsim.game.utils.actor.setOnClickListener
import com.simsim.capitalsim.game.utils.advanced.AdvancedGroup
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.game.utils.runGDX
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