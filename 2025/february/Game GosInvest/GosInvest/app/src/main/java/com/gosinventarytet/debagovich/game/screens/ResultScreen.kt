package com.gosinventarytet.debagovich.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gosinventarytet.debagovich.game.actors.main.AMainResult
import com.gosinventarytet.debagovich.game.utils.Acts
import com.gosinventarytet.debagovich.game.utils.Block
import com.gosinventarytet.debagovich.game.utils.actor.setSizeScaled
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedMainScreen
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedStage
import com.gosinventarytet.debagovich.game.utils.gdxGame

class ResultScreen: AdvancedMainScreen() {

    override val aMain = AMainResult(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        val imgStar = Image(gdxGame.assetsAll.STAR)

        addActor(imgStar)
        imgStar.setSizeScaled(sizeScalerScreen, 793f, 967f)
        imgStar.y = viewportBack.screenHeight - imgStar.height

        imgStar.setOrigin(Align.center)

        imgStar.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(1.2f, 1.2f, 0.35f),
                Acts.scaleTo(1f, 1f, 0.35f),
            )
        ))
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}