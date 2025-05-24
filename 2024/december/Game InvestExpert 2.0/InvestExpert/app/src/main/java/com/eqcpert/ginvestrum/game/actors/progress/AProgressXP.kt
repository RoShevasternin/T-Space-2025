package com.eqcpert.ginvestrum.game.actors.progress

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.eqcpert.ginvestrum.game.actors.Mask
import com.eqcpert.ginvestrum.game.utils.WIDTH_UI
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedGroup
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedScreen

class AProgressXP(
    override val screen: AdvancedScreen,
    textureProgress: TextureRegion,
): AdvancedGroup() {

    private val LENGTH = 445f

    private val imgProgress = Image(textureProgress)
    private val mask        = Mask(screen, screen.game.all.MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    override fun addActorsOnGroup() {
        addAndFillActors(Image(screen.game.all.bar), mask)
        mask.addAndFillActor(imgProgress)

        imgProgress.x = LENGTH
    }

    fun setPercent(percent: Float) {
        imgProgress.x = LENGTH - (percent * onePercentX)
    }

}