package com.jobzone.cobzone.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.jobzone.cobzone.game.utils.actor.animShow
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen

class AListki(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgList1 = Image(screen.game.assetsAll.list_1)
    private val imgList2 = Image(screen.game.assetsAll.list_2)
    private val imgChel  = Image(screen.game.assetsAll.pers)

    override fun addActorsOnGroup() {
        addActors(imgList1, imgList2, imgChel)

        imgList1.setBounds(-250f, 2f, 250f, 784f)
        imgList2.setBounds(-500f, 0f, 494f, 451f)
        imgChel.setBounds(-320f, 16f, 300f, 665f)
    }

    fun startAnim() {
        imgChel.addAction(Actions.sequence(
            Actions.moveTo(220f, 16f, 0.3f, Interpolation.sineOut),
            Actions.run {
                imgList2.addAction(Actions.sequence(
                    Actions.moveTo(2f, 0f, 0.3f, Interpolation.sineOut),
                    Actions.run {
                        imgList2.clearActions()
                        imgList2.addAction(Actions.forever(Actions.sequence(
                            Actions.rotateBy(10f, 2f, Interpolation.sine),
                            Actions.rotateBy(-20f, 2f, Interpolation.sine),
                            Actions.rotateBy(10f, 2f, Interpolation.sine),
                        )))
                    }
                ))
                imgList1.addAction(Actions.sequence(
                    Actions.moveTo(0f, 2f, 0.3f, Interpolation.sineOut),
                    Actions.run {
                        imgList1.clearActions()
                        imgList1.addAction(Actions.forever(Actions.sequence(
                            Actions.rotateBy(10f, 2f, Interpolation.sine),
                            Actions.rotateBy(-10f, 2f, Interpolation.sine),
                        )))

                    }
                ))
            }
        ))
    }

}