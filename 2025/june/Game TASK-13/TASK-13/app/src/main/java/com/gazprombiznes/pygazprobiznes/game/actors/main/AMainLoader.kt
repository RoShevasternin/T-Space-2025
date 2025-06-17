package com.gazprombiznes.pygazprobiznes.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gazprombiznes.pygazprobiznes.game.actors.progress.AProgress
import com.gazprombiznes.pygazprobiznes.game.screens.LoaderScreen
import com.gazprombiznes.pygazprobiznes.game.utils.Acts
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedGroup
import com.gazprombiznes.pygazprobiznes.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo  = Image(gdxGame.assetsLoader.logo)
    private val imgStart = Image(gdxGame.assetsLoader.start)

    private val progress  = AProgress(screen)

    override fun addActorsOnGroup() {
        addImgLogoStart()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLogoStart() {
        addActors(imgLogo, imgStart)
        imgLogo.setBounds(201f, 619f, 207f, 207f)
        imgLogo.setOrigin(Align.center)

        imgLogo.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.975f, 0.975f, 0.25f),
            Acts.scaleTo(1f, 1f, 0.35f),
        )))

        imgStart.setBounds(101f, 496f, 407f, 91f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(101f, 496f, 405f, 16f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}