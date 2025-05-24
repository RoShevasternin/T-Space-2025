package com.sukapital.saepital.game.actors.progress

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.sukapital.saepital.game.actors.Mask
import com.sukapital.saepital.game.utils.WIDTH_UI
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.toBalance

class AStatisticProgress(
    override val screen: AdvancedScreen,
    textureProgress: TextureRegion,
    summa: Int,
    ls26: LabelStyle,
): AdvancedGroup() {

    private val LENGTH = 487f

    private val imgProgress     = Image(textureProgress)
    private val mask            = Mask(screen, alphaWidth = WIDTH_UI.toInt())
    private val lblPercent      = Label(summa.toBalance + "₽", ls26)

    private val onePercentX = LENGTH / 100f

    override fun addActorsOnGroup() {
        addAndFillActor(mask)
        mask.addAndFillActor(imgProgress)
        addActor(lblPercent)
        lblPercent.setAlignment(Align.right)
        lblPercent.setBounds(LENGTH-24f, 12f,1f,18f)

        imgProgress.x = -LENGTH
    }

    fun setPercent(percent: Float) {
        imgProgress.x = (percent * onePercentX) - LENGTH - 65f
        lblPercent.x = (percent * onePercentX) + 100f
    }

    fun update(summa: Int) {
        lblPercent.setText(summa.toBalance + "₽")
    }

}