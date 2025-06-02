package com.rugosbon.pybonesrus.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.rugosbon.pybonesrus.game.screens.LoaderScreen
import com.rugosbon.pybonesrus.game.utils.Acts
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedGroup
import com.rugosbon.pybonesrus.game.utils.gdxGame

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
        imgLoader.addAction(Acts.forever(Acts.rotateBy(-360f, 0.7f, Interpolation.linear)))
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