package com.vectorvesta.bronfinteh.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.vectorvesta.bronfinteh.game.utils.Acts
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedScreen
import com.vectorvesta.bronfinteh.game.utils.gdxGame

class AVision(override val screen: AdvancedScreen): AdvancedGroup() {

   private val imgVision = Image(gdxGame.assetsAll.VISION)
   private val imgSine   = Image(gdxGame.assetsAll.sine)
   private val imgCoin   = Image(gdxGame.assetsAll.coin)

    override fun addActorsOnGroup() {
        addAndFillActor(imgVision)
        addImgSine()
        addImgCoin()
    }

    private fun addImgSine() {
        addActor(imgSine)
        imgSine.apply {
            setBounds(64f, 406f, 85f, 79f)
            setOrigin(Align.center)
            addAction(Acts.forever(Acts.sequence(
                Acts.scaleTo(0.75f, 0.75f, 0.5f),
                Acts.scaleTo(1f, 1f, 0.35f),
            )))
        }
    }

    private fun addImgCoin() {
        addActor(imgCoin)
        imgCoin.apply {
            setBounds(501f, 451f, 64f, 64f)
            addAction(Acts.forever(Acts.sequence(
                Acts.moveTo(552f, 393f, 2f, Interpolation.sine),
                Acts.moveTo(501f, 451f, 2f, Interpolation.sine),
            )))
        }
    }

}