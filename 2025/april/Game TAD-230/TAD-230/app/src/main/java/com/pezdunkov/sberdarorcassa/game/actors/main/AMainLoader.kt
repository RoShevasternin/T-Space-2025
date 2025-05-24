package com.pezdunkov.sberdarorcassa.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.pezdunkov.sberdarorcassa.game.screens.LoaderScreen
import com.pezdunkov.sberdarorcassa.game.utils.Acts
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedGroup
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo   = Image(gdxGame.assetsLoader.logo)
    private val imgLoader = Image(gdxGame.assetsLoader.loader)

//    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActors(imgLoader, imgLogo)
        imgLogo.setBounds(176f, 588f, 360f, 361f)

        imgLoader.setBounds(62f, 472f, 586f, 594f)
        imgLoader.setOrigin(Align.center)
        imgLoader.addAction(Acts.forever(Acts.parallel(Acts.rotateBy(-360f, 12f, Interpolation.linear))))
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