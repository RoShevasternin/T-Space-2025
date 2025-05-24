package com.baleru.gamanecpidec.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.baleru.gamanecpidec.game.screens.LoaderScreen
import com.baleru.gamanecpidec.game.utils.Acts
import com.baleru.gamanecpidec.game.utils.TIME_ANIM_SCREEN
import com.baleru.gamanecpidec.game.utils.actor.animDelay
import com.baleru.gamanecpidec.game.utils.actor.animShow
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedGroup
import com.baleru.gamanecpidec.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo = Image(gdxGame.assetsLoader.logo)

//    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActors(imgLogo)
        imgLogo.setBounds(240f, 124f, 402f, 402f)
        imgLogo.setOrigin(Align.center)
        imgLogo.addAction(Acts.forever(
            Acts.parallel(
                Acts.sequence(
                    Acts.scaleTo(0.85f, 0.85f, 0.6f, Interpolation.fade),
                    Acts.scaleTo(1f, 1f, 0.45f, Interpolation.sine),
                ),
            )
        ))
    }

    private fun addProgress() {
        //addActor(progress)
        //progress.setBounds(67f, 687f, 558f, 8f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        //progress.progressPercentFlow.value = percent.toFloat()
    }

}