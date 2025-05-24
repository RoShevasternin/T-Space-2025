package com.liberator.wisoliter.game.screens

import com.liberator.wisoliter.game.actors.AGrid
import com.liberator.wisoliter.game.actors.dialog.ADialogBottom
import com.liberator.wisoliter.game.actors.main.AMainTutorial
import com.liberator.wisoliter.game.actors.world.AWorldScrollable
import com.liberator.wisoliter.game.utils.Block
import com.liberator.wisoliter.game.utils.HEIGHT_UI
import com.liberator.wisoliter.game.utils.TIME_ANIM_SCREEN
import com.liberator.wisoliter.game.utils.actor.animHide
import com.liberator.wisoliter.game.utils.actor.disable
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.advanced.AdvancedStage
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.game.utils.region
import com.liberator.wisoliter.game.utils.runGDX
import kotlinx.coroutines.launch

class TutorialScreen: AdvancedScreen() {

    // Back
    private val grid   = AGrid(this)
    private val world  = AWorldScrollable(this)

    // Top Back
    private val dialog = ADialogBottom(this)

    // UI
    private val aMain  = AMainTutorial(this, dialog, world)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addGrid()
        addWorld()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addDialog()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            runGDX {
                world.animHide(TIME_ANIM_SCREEN)
                topStageBack.root.animHide(TIME_ANIM_SCREEN)
            }
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addGrid() {
        addActor(grid)
        grid.setBoundsScaled(sizeScalerScreen, -128f, 0f, 904f, 1955f)
        grid.animMove()
    }

    private fun AdvancedStage.addWorld() {
        world.apply {
            color.a = 0f
            disable()
        }
        addAndFillActor(world)
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addDialog() {
        addActor(dialog)

        val vWidth  = viewportUI.screenWidth.toFloat()
        val vHeight = (vWidth * 0.80f)
        val vX      = viewportUI.leftGutterWidth.toFloat()
        val vY      = 0f

        dialog.setBounds(vX, vY, vWidth, vHeight)

        val text = "Тапайте чтобы заработывать монеты за которые вы можете купить технику. Так же есть вкладка “Армия” где вы можете увидеть свою технику"
        dialog.setNewTT("Обучение", text)

    }

}