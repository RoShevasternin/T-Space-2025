package com.cinvestor.crotcevni.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.cinvestor.crotcevni.game.actors.progress.AProgress
import com.cinvestor.crotcevni.game.screens.LoaderScreen
import com.cinvestor.crotcevni.game.utils.Acts
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedGroup
import com.cinvestor.crotcevni.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgPanel  = Image(gdxGame.assetsLoader.rbl)
    private val imgLogo   = Image(gdxGame.assetsLoader.c_logo)
    private val progress  = AProgress(screen)

    override fun addActorsOnGroup() {
        addImgPanels()
        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanels() {
        addActors(imgPanel, imgLogo)
        imgPanel.setBounds(127f, 694f, 679f, 530f)
        imgLogo.setBounds(297f, 965f, 374f, 59f)

        imgLogo.setOrigin(Align.center)

        imgLogo.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.93f, 0.93f, 0.5f),
                Acts.scaleTo(1f, 1f, 0.5f),
            )
        ))
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(209f, 820f, 514f, 21f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}