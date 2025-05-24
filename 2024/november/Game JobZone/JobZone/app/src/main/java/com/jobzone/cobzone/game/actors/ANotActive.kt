package com.jobzone.cobzone.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.jobzone.cobzone.game.utils.actor.animShow
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen

class ANotActive(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgGradic = Image(screen.game.assetsAll.gradik)
    private val imgGear   = Image(screen.game.assetsAll.gear)
    private val imgChel   = Image(screen.game.assetsAll.polu_chel)
    private val imgOclik  = Image(screen.game.assetsAll.oklik)

    override fun addActorsOnGroup() {
        addActors(imgGradic, imgGear, imgChel, imgOclik)

        imgGradic.setBounds(0f, -1440f, 731f, 1440f)
        imgGear.apply {
            color.a = 0f
            setBounds(57f, 79f, 586f, 586f)
            setOrigin(Align.center)
        }
        imgChel.apply {
            setBounds(191f, 161f, 468f, 476f)
            setScale(0f)
        }
        imgOclik.apply {
            setBounds(425f, 613f, 246f, 236f)
            setScale(0f)
        }
    }

    fun startAnim() {
        imgGradic.addAction(Actions.sequence(
            Actions.moveTo(0f, 0f, 0.3f, Interpolation.sineOut),
            Actions.run {
                imgGear.animShow(0.25f) {
                    imgGear.addAction(Actions.forever(Actions.rotateBy(360f, 5f)))
                    imgChel.addAction(Actions.sequence(
                        Actions.scaleTo(1f, 1f, 0.25f, Interpolation.sineOut),
                        Actions.run {
                            imgOclik.addAction(Actions.sequence(
                                Actions.scaleTo(1f, 1f, 0.25f, Interpolation.sineOut),
                                Actions.run { }
                            ))
                        }
                    ))
                }
            }
        ))
    }

}