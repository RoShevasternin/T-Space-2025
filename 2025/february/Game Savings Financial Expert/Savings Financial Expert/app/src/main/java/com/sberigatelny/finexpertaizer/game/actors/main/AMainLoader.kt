package com.sberigatelny.finexpertaizer.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.sberigatelny.finexpertaizer.game.actors.progress.AProgress
import com.sberigatelny.finexpertaizer.game.screens.LoaderScreen
import com.sberigatelny.finexpertaizer.game.utils.Acts
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedGroup
import com.sberigatelny.finexpertaizer.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo  = Image(gdxGame.assetsLoader.check)
    private val imgBar   = Image(gdxGame.assetsLoader.loader_bar)
    private val progress = AProgress(screen)

    override fun addActorsOnGroup() {
        addAll()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAll() {
        addActors(imgLogo, imgBar, progress)
        imgLogo.setBounds(344f, 1042f, 300f, 301f)
        imgBar.setBounds(172f, 845f, 642f, 106f)
        progress.setBounds(218f, 872f, 551f, 53f)

        imgLogo.setOrigin(Align.center)
        imgLogo.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.95f, 0.95f, 0.3f),
                Acts.scaleTo(1f, 1f, 0.4f),
            )
        ))
    }

    fun updatePercent(progress: Float) {
        this.progress.progressPercentFlow.value = progress
    }

}