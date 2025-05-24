package com.borubashka.arsemajeg.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.borubashka.arsemajeg.game.screens.LoaderScreen
import com.borubashka.arsemajeg.game.utils.Acts
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedGroup
import com.borubashka.arsemajeg.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgR    = Image(gdxGame.assetsLoader.r)
    private val imgPlus = Image(gdxGame.assetsLoader.plus)

//    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActors(imgR, imgPlus)
        imgR.setBounds(241f, 827f, 288f, 349f)
        //imgR.setOrigin(Align.center)
        imgR.addAction(Acts.forever(Acts.sequence(
            Acts.rotateBy(5f, 1f),
            Acts.rotateTo(0f, 0.5f),
        )))

        imgPlus.setBounds(528f, 904f, 156f, 194f)
        imgPlus.setOrigin(Align.center)
        imgPlus.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.85f, 0.85f, 1f),
            Acts.scaleTo(1f, 1f, 0.5f),
        )))
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