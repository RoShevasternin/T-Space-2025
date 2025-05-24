package com.borubashka.arsemajeg.game.screens

import com.borubashka.arsemajeg.game.actors.AMap
import com.borubashka.arsemajeg.game.actors.AScrollPane
import com.borubashka.arsemajeg.game.actors.main.AMainLevels
import com.borubashka.arsemajeg.game.utils.Block
import com.borubashka.arsemajeg.game.utils.HEIGHT_UI
import com.borubashka.arsemajeg.game.utils.WIDTH_UI
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedMainScreen
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedStage
import com.borubashka.arsemajeg.util.log

class LevelsScreen: AdvancedMainScreen() {

    companion object {
        var SELECTED_LVL_INDEX = 0
    }

    private val aMap    = AMap(this)
    private val aScroll = AScrollPane(aMap)

    override val aMain = AMainLevels(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        addMap()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_LOADER.region)
        //addAndFillActor(Image(drawerUtil.getTexture(Color.BLACK)))
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addMap() {
        addActor(aScroll)

        val groupW = 3216f
        val groupH = 2005f

        val groupRatio  = groupH / groupW
        val screenRatio = viewportBack.screenHeight / viewportBack.screenWidth

        val scale = if (screenRatio > groupRatio) groupH / viewportBack.screenHeight else groupW / viewportBack.screenWidth

        aScroll.setSize(viewportBack.screenWidth.toFloat(), viewportBack.screenHeight.toFloat())
        aMap.setSize(groupW / scale, groupH / scale)
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}