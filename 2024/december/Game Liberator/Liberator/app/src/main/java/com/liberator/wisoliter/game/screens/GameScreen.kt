package com.liberator.wisoliter.game.screens

import com.liberator.wisoliter.game.actors.AGrid
import com.liberator.wisoliter.game.actors.dialog.ADialogArmy
import com.liberator.wisoliter.game.actors.dialog.ADialogAten_Neobhodimo
import com.liberator.wisoliter.game.actors.dialog.ADialogAten_Pozdravly
import com.liberator.wisoliter.game.actors.dialog.ADialogAten_Zakonchilis
import com.liberator.wisoliter.game.actors.dialog.ADialogMagaz
import com.liberator.wisoliter.game.actors.main.AMainGame
import com.liberator.wisoliter.game.actors.world.AWorldScrollable
import com.liberator.wisoliter.game.utils.Block
import com.liberator.wisoliter.game.utils.TIME_ANIM_SCREEN
import com.liberator.wisoliter.game.utils.actor.animHide
import com.liberator.wisoliter.game.utils.actor.disable
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.advanced.AdvancedStage
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.game.utils.runGDX
import com.liberator.wisoliter.util.log
import kotlinx.coroutines.launch

class GameScreen: AdvancedScreen() {

    // Back
    private val grid   = AGrid(this)
    private val world  = AWorldScrollable(this)

    // Top Back
    private val dialogArmy  = ADialogArmy(this)
    private val dialogMagaz = ADialogMagaz(this)

    private val dialogAten_Neobhodimo  = ADialogAten_Neobhodimo(this)
    private val dialogAten_Zakonchilis = ADialogAten_Zakonchilis(this)
    private val dialogAten_Pozdravly   = ADialogAten_Pozdravly(this)

    // UI
    private val aMain  = AMainGame(
        this,
        world,
        dialogArmy,
        dialogMagaz,

        dialogAten_Neobhodimo,
        dialogAten_Zakonchilis,
        dialogAten_Pozdravly,
    )

    override fun show() {
        super.show()
    }

    override fun pause() {
        super.pause()
        gdxGame.musicUtil.music?.pause()
    }

    override fun resume() {
        super.resume()
        gdxGame.musicUtil.music?.play()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addGrid()
        addWorld()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addDialogArmy()
        addDialogMagaz()

        addDialogAten_Neobhodimo()
        addDialogAten_Zakonchilis()
        addDialogAten_Pozdravly()
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
        addAndFillActor(world)
        world.color.a = 0f
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addDialogArmy() {
        addActor(dialogArmy)

        val vWidth  = viewportUI.screenWidth.toFloat()
        val vHeight = (vWidth * 0.996129f)
        val vX      = viewportUI.leftGutterWidth.toFloat()

        dialogArmy.setBounds(vX, -vHeight, vWidth, vHeight)
    }

    private fun AdvancedStage.addDialogMagaz() {
        addActor(dialogMagaz)

        val vWidth  = viewportUI.screenWidth.toFloat()
        val vHeight = (vWidth * 1.493556f)
        val vX      = viewportUI.leftGutterWidth.toFloat()

        dialogMagaz.setBounds(vX, -vHeight, vWidth, vHeight)
    }

    private fun AdvancedStage.addDialogAten_Neobhodimo() {
        addAndFillActor(dialogAten_Neobhodimo)
        dialogAten_Neobhodimo.apply {
            color.a = 0f
            disable()
        }
    }

    private fun AdvancedStage.addDialogAten_Zakonchilis() {
        addAndFillActor(dialogAten_Zakonchilis)
        dialogAten_Zakonchilis.apply {
            color.a = 0f
            disable()
        }
    }

    private fun AdvancedStage.addDialogAten_Pozdravly() {
        addAndFillActor(dialogAten_Pozdravly)
        dialogAten_Pozdravly.apply {
            color.a = 0f
            disable()
        }
    }

}