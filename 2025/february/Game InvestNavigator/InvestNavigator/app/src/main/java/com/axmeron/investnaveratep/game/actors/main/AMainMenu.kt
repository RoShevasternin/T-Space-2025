package com.axmeron.investnaveratep.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.axmeron.investnaveratep.game.actors.button.AButton
import com.axmeron.investnaveratep.game.screens.MenuScreen
import com.axmeron.investnaveratep.game.screens.OnboardScreen
import com.axmeron.investnaveratep.game.screens.StartTestScreen
import com.axmeron.investnaveratep.game.utils.Block
import com.axmeron.investnaveratep.game.utils.TIME_ANIM_SCREEN
import com.axmeron.investnaveratep.game.utils.actor.animHideSuspend
import com.axmeron.investnaveratep.game.utils.actor.animShowSuspend
import com.axmeron.investnaveratep.game.utils.actor.setBounds
import com.axmeron.investnaveratep.game.utils.actor.setOnClickListener
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainMenu(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgMenu = Image(gdxGame.assetsAll.menu)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgMenu()

            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(0f, 129f, 804f, 1523f)

        var ny = 1214f
        List(4) { Actor() }.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(53f, ny, 696f, 319f)
            ny -= 43+319

            actor.setOnClickListener(gdxGame.soundUtil) {
                MenuScreen.SELECTED_TEST_INDEX = index
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(StartTestScreen::class.java.name, MenuScreen::class.java.name)
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