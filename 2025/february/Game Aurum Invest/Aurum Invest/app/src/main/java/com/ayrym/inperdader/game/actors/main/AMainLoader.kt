package com.ayrym.inperdader.game.actors.main

import com.ayrym.inperdader.game.actors.ALogo
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ayrym.inperdader.game.actors.progress.AProgress
import com.ayrym.inperdader.game.screens.LoaderScreen
import com.ayrym.inperdader.game.utils.advanced.AdvancedGroup
import com.ayrym.inperdader.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgProgressPanel  = Image(gdxGame.assetsLoader.prog_pan)
    private val imgPanel          = Image(gdxGame.assetsLoader.panel)
    private val progress          = AProgress(screen)
    private val aLogo             = ALogo(screen)

    override fun addActorsOnGroup() {
        addImgPanels()
        addProgress()
        addALogo()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanels() {
        addActors(imgPanel, imgProgressPanel)
        imgPanel.setBounds(166f, 858f, 472f, 240f)
        imgProgressPanel.setBounds(49f, 539f, 707f, 213f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(134f, 687f, 535f, 21f)
    }

    private fun addALogo() {
        addActor(aLogo)
        aLogo.setBounds(209f, 900f, 155f, 156f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}