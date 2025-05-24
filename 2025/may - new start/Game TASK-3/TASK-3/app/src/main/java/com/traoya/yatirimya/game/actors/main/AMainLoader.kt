package com.traoya.yatirimya.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.traoya.yatirimya.game.actors.progress.AProgress
import com.traoya.yatirimya.game.screens.LoaderScreen
import com.traoya.yatirimya.game.utils.Acts
import com.traoya.yatirimya.game.utils.advanced.AdvancedGroup
import com.traoya.yatirimya.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo = Image(gdxGame.assetsLoader.logo)
    private val imgTrao = Image(gdxGame.assetsLoader.trao)

    private val progress  = AProgress(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActors(imgLogo, imgTrao)
        imgLogo.setBounds(312f, 818f, 417f, 417f)
        imgLogo.setOrigin(Align.center)

        imgLogo.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.875f, 0.875f, 0.45f),
            Acts.scaleTo(1f, 1f, 0.35f),
        )))

        imgTrao.setBounds(263f, 648f, 514f, 130f)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(263f, 648f, 513f, 40f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}