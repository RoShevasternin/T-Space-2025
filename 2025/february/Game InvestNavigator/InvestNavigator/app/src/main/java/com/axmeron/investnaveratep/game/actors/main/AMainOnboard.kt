package com.axmeron.investnaveratep.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.axmeron.investnaveratep.game.actors.AOnboard
import com.axmeron.investnaveratep.game.screens.MenuScreen
import com.axmeron.investnaveratep.game.utils.Block
import com.axmeron.investnaveratep.game.utils.TIME_ANIM_SCREEN
import com.axmeron.investnaveratep.game.utils.actor.animHideSuspend
import com.axmeron.investnaveratep.game.utils.actor.animShowSuspend
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainOnboard(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgText = Image(gdxGame.assetsAll.text_onboard)
    private val onboard = AOnboard(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgText()
                addOnboard()

            }

            animShowMain()
            delay(1700)

            runGDX {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgText() {
        addActor(imgText)
        imgText.setBounds(64f, 1287f, 674f, 224f)
    }

    private fun addOnboard() {
        addActor(onboard)
        onboard.setBounds(0f, 0f, 802f, 1246f)
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