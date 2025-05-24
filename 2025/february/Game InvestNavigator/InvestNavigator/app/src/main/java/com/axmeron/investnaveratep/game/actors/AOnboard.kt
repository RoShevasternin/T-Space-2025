package com.axmeron.investnaveratep.game.actors

import android.graphics.Interpolator
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame

class AOnboard(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgMain = Image(gdxGame.assetsAll.onboard_main)
    private val imgBlob = Image(gdxGame.assetsAll.onboard_blob)
    private val img_1   = Image(gdxGame.assetsAll._1)
    private val img_2   = Image(gdxGame.assetsAll._2)
    private val img_3   = Image(gdxGame.assetsAll._3)
    private val img_4   = Image(gdxGame.assetsAll._4)
    private val img_5   = Image(gdxGame.assetsAll._5)
    private val img_6   = Image(gdxGame.assetsAll._6)

    override fun addActorsOnGroup() {
        addImgBlob()
        addAndFillActor(imgMain)
        addItems()
    }

    // Actors ---------------------------------------------------

    private fun AdvancedGroup.addImgBlob() {
        addActor(imgBlob)
        imgBlob.setBounds(0f, 155f, 638f, 869f)

        imgBlob.apply {
            val time = 1f
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.1f, 0f, time),
                Actions.scaleBy(0.1f, -0.1f, time),
                Actions.scaleBy(0f, 0.1f, time),
            )))
        }
    }

    private fun AdvancedGroup.addItems() {
        addActors(img_1, img_2, img_3, img_4, img_5, img_6)
        img_1.setBounds(27f, 907f, 111f, 112f)
        img_2.setBounds(263f, 911f, 130f, 123f)
        img_3.setBounds(132f, 720f, 104f, 55f)
        img_4.setBounds(232f, 647f, 143f, 78f)
        img_5.setBounds(30f, 385f, 131f, 131f)
        img_6.setBounds(662f, 359f, 90f, 58f)

        img_1.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.3f, -0.1f, 0.5f),
                Actions.scaleBy(0.3f, 0.1f, 0.7f),
            )))
            addAction(Actions.forever(
                Actions.rotateBy(-360f, 7f)
            ))
        }
        img_2.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.3f, -0.3f, 0.7f),
                Actions.scaleBy(0.3f, 0.3f, 0.7f),
            )))
            addAction(Actions.forever(Actions.sequence(
                Actions.rotateBy(-30f, 0.7f),
                Actions.rotateBy(30f, 0.6f),
            )))
        }
        img_3.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.15f, -0.15f, 0.7f),
                Actions.scaleBy(0.15f, 0.15f, 1f),
            )))
            addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(-120f, -20f, 0.7f),
                Actions.moveBy(120f, 20f, 1f),
            )))
        }
        img_4.apply {
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.4f, -0.4f, 1f),
                Actions.scaleBy(0.4f, 0.4f, 2f),
            )))
        }
        img_5.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(0.15f, 0.15f, 0.35f),
                Actions.scaleBy(-0.15f, -0.15f, 0.25f),
            )))
            addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0f, 50f, 0.35f, Interpolation.sineOut),
                Actions.moveBy(0f, -50f, 0.25f, Interpolation.sineIn),
            )))
        }
        img_6.apply {
            addAction(Actions.forever(Actions.sequence(
                Actions.rotateBy(15f, 1f),
                Actions.rotateBy(-15f, 0.25f),
            )))
        }

    }

}