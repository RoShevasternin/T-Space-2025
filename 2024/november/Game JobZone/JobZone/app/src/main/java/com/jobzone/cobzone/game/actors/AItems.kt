package com.jobzone.cobzone.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.jobzone.cobzone.game.utils.actor.PosSize
import com.jobzone.cobzone.game.utils.actor.setBounds
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen

class AItems(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listPosSize = listOf(
        PosSize(0f, 27f, 88f, 94f),
        PosSize(101f, 158f, 82f, 73f),
        PosSize(192f, 97f, 176f, 128f),
        PosSize(177f, 0f, 58f, 70f),
        PosSize(413f, 19f, 83f, 85f),
        PosSize(495f, 181f, 59f, 53f),
    )
    private val listTime = listOf(
        0.6f, 0.75f, 0.9f,
        1f, 1.3f, 1.4f
    ).shuffled()

    override fun addActorsOnGroup() {
        screen.game.assetsAll.listItems.onEachIndexed { index, textureRegion ->
            Image(textureRegion).also { img ->
                addActor(img)
                img.setBounds(listPosSize[index])
                img.setOrigin(Align.center)
                img.setScale(0f)

                img.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1f, 1f, listTime[index], Interpolation.sine),
                    Actions.scaleTo(0.5f, 0.5f, listTime[index], Interpolation.sine),
                )))
            }
        }
    }

}