package com.ekobioznaher.sugertogerra.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.ekobioznaher.sugertogerra.game.actors.APanel
import com.ekobioznaher.sugertogerra.game.actors.AScrollPane
import com.ekobioznaher.sugertogerra.game.actors.autoLayout.AVerticalGroup
import com.ekobioznaher.sugertogerra.game.actors.button.AButton
import com.ekobioznaher.sugertogerra.game.actors.scroller.AScroller
import com.ekobioznaher.sugertogerra.game.actors.scroller.level.AScrollerLevel
import com.ekobioznaher.sugertogerra.game.screens.MenuScreen
import com.ekobioznaher.sugertogerra.game.screens.QuestionScreen
import com.ekobioznaher.sugertogerra.game.utils.Block
import com.ekobioznaher.sugertogerra.game.utils.GameColor
import com.ekobioznaher.sugertogerra.game.utils.TIME_ANIM_SCREEN
import com.ekobioznaher.sugertogerra.game.utils.actor.animHideSuspend
import com.ekobioznaher.sugertogerra.game.utils.actor.animShowSuspend
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedGroup
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedScreen
import com.ekobioznaher.sugertogerra.game.utils.font.FontParameter
import com.ekobioznaher.sugertogerra.game.utils.gdxGame
import com.ekobioznaher.sugertogerra.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var balance = 0
var range   = 0
var listLevel = MutableList(12) { false }

class AMainMenu(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font58       = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(58))

    private val ls58 = Label.LabelStyle(font58, GameColor.text)

    private val aPanel         = APanel(screen)
    private val lblSelect      = Label("Выберите уровень", ls58)
    private val aScroller      = AScroller(screen)
    private val aScrollerLevel = AScrollerLevel(screen)
    private val btnStart       = AButton(screen, AButton.Type.Start)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addPanel()
                addLbl()
                addScroller()
                addScrollerLevel()
                addBtnStart()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addPanel() {
        addActor(aPanel)
        aPanel.setBounds(61f, 2035f, 1028f, 231f)
    }

    private fun addLbl() {
        addActor(lblSelect)
        lblSelect.setBounds(61f, 1135f, 554f, 71f)
    }

    private fun addScroller() {
        addActor(aScroller)
        aScroller.setBounds(0f, 1266f, 1150f, 708f)
    }

    private fun addScrollerLevel() {
        addActor(aScrollerLevel)
        aScrollerLevel.setBounds(0f, 273f, 1150f, 831f)

        aScrollerLevel.blockSelectedLevel = { lvlIndex ->
            MenuScreen.SELECTED_LEVEL_INDEX = lvlIndex
            if (btnStart.touchable == Touchable.disabled) btnStart.enable()
        }
    }

    private fun addBtnStart() {
        addActor(btnStart)
        btnStart.setBounds(68f, 59f, 1013f, 154f)
        btnStart.disable()

        btnStart.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(QuestionScreen::class.java.name, screen::class.java.name)
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