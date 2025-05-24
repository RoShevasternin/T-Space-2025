package com.smarteca.foundsender.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.smarteca.foundsender.game.actors.ABottomMenu
import com.smarteca.foundsender.game.actors.ALogo
import com.smarteca.foundsender.game.actors.AScrollPane
import com.smarteca.foundsender.game.actors.autoLayout.AVerticalGroup
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.button.AButtonText
import com.smarteca.foundsender.game.screens.CalculatorScreen
import com.smarteca.foundsender.game.screens.DashboardScreen
import com.smarteca.foundsender.game.screens.GlossaryScreen
import com.smarteca.foundsender.game.screens.savings.SavingsScreen
import com.smarteca.foundsender.game.screens.test.TestScreen
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.animDelay
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter

class AMainGlossary(
    override val screen: GlossaryScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font102 = screen.fontGenerator_Bold.generateFont(parameter.setSize(102))

    private val ls102 = LabelStyle(font102, Color.WHITE)

    private val aLogo       = ALogo(screen)
    private val lblTitle    = Label("Glossary", ls102)
    private val aBottomMenu = ABottomMenu(screen, ABottomMenu.Type.Glossary)

    private val verticalGroup = AVerticalGroup(screen, isWrap = true)
    private val scrollPane    = AScrollPane(verticalGroup)

    override fun addActorsOnGroup() {
        color.a = 0f

        addALogo()
        addTitle()
        addABottomMenu()
        addScroll()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALogo() {
        addActor(aLogo)
        aLogo.setBounds(364f, 2217f, 452f, 200f)
    }

    private fun addTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(60f, 1977f, 501f, 124f)
    }

    private fun addABottomMenu() {
        addActor(aBottomMenu)
        aBottomMenu.setBounds(0f, 107f, 1181f, 148f)

        aBottomMenu.apply {
            blockDashboard = { navTo(DashboardScreen::class.java.name) }
            blockSavings = { navTo(SavingsScreen::class.java.name) }
            blockCalculator = { navTo(CalculatorScreen::class.java.name) }
            blockGlossary = {}
            blockTest = { navTo(TestScreen::class.java.name) }
        }
    }

    private fun addScroll() {
        addActor(scrollPane)
        scrollPane.setBounds(66f, 306f, 1048f, 1611f)
        verticalGroup.setSize(1048f, 1611f)

        val listHeight = listOf(1964f, 1897f, 1731f, 1123f)
        gdxGame.assetsAll.listGlossary.forEachIndexed { index, texture ->
            verticalGroup.addActor(Image(texture).apply {
                setSize(1078f, listHeight[index])
            })
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
