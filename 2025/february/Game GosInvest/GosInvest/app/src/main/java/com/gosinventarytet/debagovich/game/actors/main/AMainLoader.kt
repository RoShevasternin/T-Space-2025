package com.gosinventarytet.debagovich.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gosinventarytet.debagovich.game.actors.progress.AProgress
import com.gosinventarytet.debagovich.game.screens.LoaderScreen
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedGroup
import com.gosinventarytet.debagovich.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo  = Image(gdxGame.assetsLoader.goslogo)
    private val progress = AProgress(screen)

    override fun addActorsOnGroup() {
        addImgLogo()
        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(48f, 674f, 698f, 301f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(132f, 799f, 528f, 11f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}