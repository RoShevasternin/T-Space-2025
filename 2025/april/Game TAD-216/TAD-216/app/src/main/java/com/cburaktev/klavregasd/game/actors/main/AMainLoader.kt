package com.cburaktev.klavregasd.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.cburaktev.klavregasd.game.screens.LoaderScreen
import com.cburaktev.klavregasd.game.utils.Acts
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedGroup
import com.cburaktev.klavregasd.game.utils.gdxGame

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
        addActors(imgLogo, imgLoader)
        imgLogo.setBounds(229f, 796f, 512f, 512f)

        imgLoader.setBounds(409f, 505f, 151f, 151f)
        imgLoader.setOrigin(Align.center)
        imgLoader.addAction(Acts.forever(Acts.rotateBy(-360f, 1.7f, Interpolation.circle)))
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