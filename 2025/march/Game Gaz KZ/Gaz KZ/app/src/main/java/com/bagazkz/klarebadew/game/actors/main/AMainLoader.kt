package com.bagazkz.klarebadew.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.bagazkz.klarebadew.game.actors.progress.AProgressLoader
import com.bagazkz.klarebadew.game.screens.LoaderScreen
import com.bagazkz.klarebadew.game.utils.Acts
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedGroup
import com.bagazkz.klarebadew.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgPanel  = Image(gdxGame.assetsLoader.LOADER)
    private val imgLogo   = Image(gdxGame.assetsLoader.kzgz)
    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgPanels()
        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanels() {
        addActors(imgPanel, imgLogo)
        imgPanel.setBounds(63f, 825f, 831f, 526f)
        imgLogo.setBounds(160f, 1029f, 638f, 284f)

        imgLogo.setOrigin(Align.center)

        imgLogo.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.93f, 0.93f, 0.45f),
                Acts.scaleTo(1f, 1f, 0.45f),
            )
        ))
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(159f, 953f, 638f, 25f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}