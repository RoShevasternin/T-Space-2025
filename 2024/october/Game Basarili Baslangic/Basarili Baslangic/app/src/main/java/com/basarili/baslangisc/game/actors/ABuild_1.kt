package com.basarili.baslangisc.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.basarili.baslangisc.game.utils.advanced.AdvancedGroup
import com.basarili.baslangisc.game.utils.advanced.AdvancedScreen

class ABuild_1(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    val imgD_1 = Image(screen.game.all.listDetails[0])
    val imgD_2 = Image(screen.game.all.listDetails[1])
    val imgD_3 = Image(screen.game.all.listDetails[2])

    override fun addActorsOnGroup() {
        addAndFillActor(imgD_1)
        addActors(imgD_3, imgD_2)

        imgD_2.apply {
            setBounds(115f, 201f, 223f, 188f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.rotateBy(-30f, 0.5f, Interpolation.sineOut),
                Actions.rotateBy(42f, 0.75f, Interpolation.sine),
                Actions.rotateBy(-12f, 0.25f, Interpolation.sineIn),
            )))
        }
        imgD_3.apply {
            setBounds(277f, 0f, 72f, 382f)
            addAction(Actions.forever(Actions.sequence(
                Actions.sizeBy(0f, -82f, 0.5f),
                Actions.sizeBy(0f, 82f, 0.5f),
                Actions.delay(0.5f)
            )))
        }
    }

}