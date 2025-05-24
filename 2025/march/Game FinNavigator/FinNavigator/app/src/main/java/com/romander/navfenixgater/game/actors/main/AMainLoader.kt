package com.romander.navfenixgater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.romander.navfenixgater.game.actors.progress.AProgressLoader
import com.romander.navfenixgater.game.screens.LoaderScreen
import com.romander.navfenixgater.game.utils.Acts
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgPanel  = Image(gdxGame.assetsLoader.PANEL)
    private val imgLogo   = Image(gdxGame.assetsLoader.loader)
    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgPanels()
        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanels() {
        addActors(imgPanel, imgLogo)
        imgPanel.setBounds(41f, 603f, 609f, 295f)
        imgLogo.setBounds(157f, 750f, 111f, 112f)
        imgLogo.setOrigin(Align.center)
        imgLogo.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.94f, 0.94f, 0.35f),
                Acts.scaleTo(1f, 1f, 0.35f),
            )
        ))
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(67f, 687f, 558f, 8f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}