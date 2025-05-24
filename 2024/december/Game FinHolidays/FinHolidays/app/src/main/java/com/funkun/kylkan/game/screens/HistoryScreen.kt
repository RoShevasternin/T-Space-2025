package com.funkun.kylkan.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.funkun.kylkan.game.actors.main.AMainHistory
import com.funkun.kylkan.game.actors.main.AMainMain
import com.funkun.kylkan.game.utils.Block
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.advanced.AdvancedStage
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.region
import com.funkun.kylkan.game.utils.runGDX
import kotlinx.coroutines.launch

class HistoryScreen: AdvancedScreen() {

    private val imgTop = Image(gdxGame.assetsAll.top)
    private val aMain  = AMainHistory(this)

    override fun show() {
        setBackBackground(drawerUtil.getRegion(Color.WHITE))
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addTop()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addTop() {
        addActor(imgTop)

        val nHeight = (viewportUI.screenWidth * 0.266798f)

        imgTop.setBounds(
            viewportUI.leftGutterWidth.toFloat(),
            viewportBack.screenHeight - nHeight,
            viewportUI.screenWidth.toFloat(),
            nHeight
        )
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}