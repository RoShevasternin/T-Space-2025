package com.smarteca.foundsender.game.actors.main.test

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.ATestWithProgress
import com.smarteca.foundsender.game.actors.autoLayout.AVerticalGroup
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.SelectedTestScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.animDelay
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.actor.setOnClickListener
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter

class AMainTest(
    override val screen: TestScreen,
): AdvancedMainGroup() {

    companion object {
        var SELECTED_TEST_INDEX = 0
            private set
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font102 = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))
    private val font51  = screen.fontGenerator_Regular.generateFont(parameter.setSize(51))

    private val ls102 = LabelStyle(font102, Color.WHITE)
    private val ls51  = LabelStyle(font51, Color.WHITE)

    private val aLogo       = ALogo(screen)
    private val lblTitle    = Label("Tests", ls102)
    private val aBottomMenu = ABottomMenu(screen, ABottomMenu.Type.Test)

    private val verticalGroup = AVerticalGroup(screen, 89f)
    private val listLabel     = List(4) { Label(GLOBAL_listTestTitle[it], ls51) } //Label("TEST ${it.inc()}", ls51) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addALogo()
        addTitle()
        addABottomMenu()
        addVerticalGroup()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALogo() {
        addActor(aLogo)
        aLogo.setBounds(364f, 2217f, 452f, 200f)
    }

    private fun addTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(60f, 2037f, 501f, 124f)
    }

    private fun addABottomMenu() {
        addActor(aBottomMenu)
        aBottomMenu.setBounds(0f, 107f, 1181f, 148f)

        aBottomMenu.apply {
            blockDashboard = { navTo(DashboardScreen::class.java.name) }
            blockSavings = { navTo(SavingsScreen::class.java.name) }
            blockCalculator = { navTo(CalculatorScreen::class.java.name) }
            blockGlossary = { navTo(GlossaryScreen::class.java.name) }
            blockTest = {}
        }
    }

    private fun addVerticalGroup() {
        addActor(verticalGroup)
        verticalGroup.setBounds(66f, 452f, 1049f, 1466f)

        gdxGame.ds_TestProgressData.flow.value.forEachIndexed { index, progress ->
            if (progress == -1) {
                val btn = AButton(screen, AButton.Type.Test_Simple)
                btn.setSize(1049f, 194f)
                btn.setOnClickListener {
                    SELECTED_TEST_INDEX = index
                    navTo(SelectedTestScreen::class.java.name)
                }
                verticalGroup.addActor(btn)

                val lbl = listLabel[index]
                val pos = btn.localToStageCoordinates(Vector2())
                lbl.setBounds(pos.x + 48, pos.y + 63, 153f, 66f)
                this.addActor(lbl)
            } else {
                val btn = ATestWithProgress(screen)
                btn.setProgress(progress.toFloat())
                btn.setSize(1049f, 235f)
                btn.blockClick = {
                    SELECTED_TEST_INDEX = index
                    navTo(SelectedTestScreen::class.java.name)
                }
                verticalGroup.addActor(btn)

                val lbl = listLabel[index]
                val pos = btn.localToStageCoordinates(Vector2())
                lbl.setBounds(pos.x + 48, pos.y + 135, 153f, 66f)
                this.addActor(lbl)
            }
        }

    }


    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Runnable) {
        //children.onEach { it.clearActions() }
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.run() }
    }

    override fun animHideMain(blockEnd: Runnable) {
        //children.onEach { it.clearActions() }
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.run() }
    }

    // Logic -------------------------------------------------

    private fun navTo(screenName: String) {
        screen.hideScreen {
            gdxGame.navigationManager.navigate(screenName, screen::class.java.name)
        }
    }

}
