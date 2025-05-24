package com.pulser.purlembager.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.actors.AAllList
import com.pulser.purlembager.game.actors.ADialogMenu
import com.pulser.purlembager.game.actors.AHomePulse
import com.pulser.purlembager.game.actors.APanel
import com.pulser.purlembager.game.actors.main.AMainMenu
import com.pulser.purlembager.game.utils.Block
import com.pulser.purlembager.game.utils.GameColor
import com.pulser.purlembager.game.utils.SizeScaler
import com.pulser.purlembager.game.utils.TIME_ANIM_SCREEN
import com.pulser.purlembager.game.utils.actor.animHide
import com.pulser.purlembager.game.utils.actor.animShow
import com.pulser.purlembager.game.utils.actor.disable
import com.pulser.purlembager.game.utils.actor.enable
import com.pulser.purlembager.game.utils.actor.setBoundsScaled
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.advanced.AdvancedStage
import com.pulser.purlembager.game.utils.gdxGame
import com.pulser.purlembager.game.utils.runGDX
import com.pulser.purlembager.util.log
import kotlinx.coroutines.launch

class MenuScreen: AdvancedScreen() {

    private val aHomePulse = AHomePulse(this)
    private val aPanel     = APanel(this, gdxGame.assetsAll.panel_home)

    private val imgBackground = Image(drawerUtil.getTexture(GameColor.black_1A_85))
    private val dialogMenu    = ADialogMenu(this)
    private val aAllList      = AAllList(this)

    private val aMain = AMainMenu(this, dialogMenu, aPanel, aAllList)

    override fun AdvancedStage.addActorsOnStageBack() {
        addHomePulse()
        addPanel()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addBackground()
        addDialogMenu()
    }

    override fun AdvancedStage.addActorsOnStageTopUI() {
        addAAllList()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            runGDX {
                listOf(aHomePulse, imgBackground, dialogMenu).onEach { it.animHide(TIME_ANIM_SCREEN) }
            }
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addHomePulse() {
        addActor(aHomePulse)
        aHomePulse.setBoundsScaled(sizeScalerScreen, 0f, 1385f, 1176f, 1167f)
    }

    private fun AdvancedStage.addPanel() {
        addActor(aPanel)

        val panelSizeScaler = SizeScaler(SizeScaler.Axis.X, 1176f)
        panelSizeScaler.calculateScale(viewportUI.screenWidth.toFloat(), 0f)

        aPanel.setBoundsScaled(panelSizeScaler, 0f, 0f, 1176f, 267f)
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addBackground() {
        addAndFillActor(imgBackground)
        imgBackground.apply {
            color.a = 0f
            disable()
        }
    }

    private fun AdvancedStage.addDialogMenu() {
        addActor(dialogMenu)

        val panelSizeScaler = SizeScaler(SizeScaler.Axis.X, 1176f)
        panelSizeScaler.calculateScale(viewportUI.screenWidth.toFloat(), 0f)

        dialogMenu.setBoundsScaled(panelSizeScaler, 115f, 81f, 946f, 327f)

        dialogMenu.apply {
            color.a = 0f
            disable()
        }
    }

    // Actors Top UI------------------------------------------------------------------------

    private fun AdvancedStage.addAAllList() {
        addAndFillActor(aAllList)
        aAllList.color.a = 0f
        aAllList.disable()
    }

    // Logic -------------------------------------------------------------------------

    fun showBackground() {
        listOf(stageBack, stageUI).onEach { it.root.disable() }
        imgBackground.enable()
        imgBackground.animShow(TIME_ANIM_SCREEN)
    }

    fun hideBackground() {
        listOf(stageBack, stageUI).onEach { it.root.enable() }
        imgBackground.disable()
        imgBackground.animHide(TIME_ANIM_SCREEN)
    }

}