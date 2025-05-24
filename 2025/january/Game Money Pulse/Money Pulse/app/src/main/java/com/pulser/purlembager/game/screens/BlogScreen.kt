package com.pulser.purlembager.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.actors.AAllList
import com.pulser.purlembager.game.actors.ADialogMenu
import com.pulser.purlembager.game.actors.AHomePulse
import com.pulser.purlembager.game.actors.APanel
import com.pulser.purlembager.game.actors.main.AMainBlog
import com.pulser.purlembager.game.actors.main.AMainMenu
import com.pulser.purlembager.game.utils.*
import com.pulser.purlembager.game.utils.actor.*
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class BlogScreen: AdvancedScreen() {

    private val aPanel = APanel(this, gdxGame.assetsAll.panel_blog)

    private val imgBackground = Image(drawerUtil.getTexture(GameColor.black_1A_85))
    private val dialogMenu    = ADialogMenu(this)

    private val aMain = AMainBlog(this, dialogMenu, aPanel)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.Blog.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addPanel()

        addBackground()
        addDialogMenu()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            runGDX {
                listOf(imgBackground, dialogMenu).onEach { it.animHide(TIME_ANIM_SCREEN) }
            }
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

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