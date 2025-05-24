package com.ayrym.inperdader.game.actors

import com.ayrym.inperdader.game.utils.Acts
import com.ayrym.inperdader.game.utils.SizeScaler
import com.ayrym.inperdader.game.utils.actor.PosSize
import com.ayrym.inperdader.game.utils.actor.animDelay
import com.ayrym.inperdader.game.utils.actor.setBoundsScaled
import com.ayrym.inperdader.game.utils.advanced.AdvancedGroup
import com.ayrym.inperdader.game.utils.advanced.AdvancedScreen
import com.ayrym.inperdader.game.utils.gdxGame
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align

class ALogo(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 155f)

    private val imgLogo     = Image(gdxGame.assetsLoader.logo)
    private val imgCoin     = Image(gdxGame.assetsLoader.coin)
    private val listImgStar = List(3) { Image(gdxGame.assetsLoader.blesk) }

    override fun addActorsOnGroup() {
        addAndFillActor(imgLogo)
        //addActors(listImgStar)
        //addActor(imgCoin)

        imgCoin.apply {
            setBoundsScaled(sizeScaler, 56f, 0f, 85f, 85f)
            setOrigin(Align.center)
            addAction(Acts.forever(Acts.sequence(
                Acts.rotateBy(-360f, 2.3f, Interpolation.linear)
            )))
        }

        val listPosSize = listOf(
            PosSize(33f, 123f, 21f, 21f),
            PosSize(11f, 120f, 15f, 15f),
            PosSize(20f, 103f, 17f, 17f),
        )
        listImgStar.onEachIndexed { index, img ->
            img.setBoundsScaled(sizeScaler, listPosSize[index])

            val time = (3..8).random() / 10f
            img.animDelay((4..20).random() / 10f) {
                img.addAction(Acts.forever(Acts.sequence(
                    Acts.scaleTo(1.5f, 1.5f, time),
                    Acts.scaleTo(1f, 1f, time),
                )))
            }
        }
    }

}