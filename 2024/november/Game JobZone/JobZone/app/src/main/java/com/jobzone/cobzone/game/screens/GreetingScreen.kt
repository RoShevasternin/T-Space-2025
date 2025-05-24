package com.jobzone.cobzone.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jobzone.cobzone.game.GDXGame
import com.jobzone.cobzone.game.actors.APanelTop
import com.jobzone.cobzone.game.actors.main.AMainGreeting
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.GameColor
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.advanced.AdvancedStage
import com.jobzone.cobzone.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GreetingScreen(override val game: GDXGame): AdvancedScreen() {

    private val aPanelTop = APanelTop(this)
    private val aMain     = AMainGreeting(this)

    override fun show() {
        super.show()
        coroutine?.launch {
            delay(1200)
            hideScreen {
                game.navigationManager.navigate(VacanciesScreen::class.java.name)
            }
        }
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addPanelTop()
        addBottom()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanelTop() {
        addActor(aPanelTop)

        val w = sizeScaler_Ui_Back.scaled(732f)
        val h = sizeScaler_Ui_Back.scaled(201f)
        val x = (viewportBack.worldWidth / 2) - (w / 2)
        val y = (viewportBack.worldHeight - h)
        aPanelTop.setBounds(x, y, w, h)

        aPanelTop.addImgLogo()
    }

    private fun AdvancedStage.addBottom() {
        val bottomPadding = (viewportBack.screenHeight - viewportUI.screenHeight) / 2f

        if (bottomPadding > 0) {
            val imgBottom = Image(drawerUtil.getTexture(GameColor.bottom1))
            addActor(imgBottom)
            imgBottom.setBounds(0f, 0f, viewportBack.worldWidth, bottomPadding)
        }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}