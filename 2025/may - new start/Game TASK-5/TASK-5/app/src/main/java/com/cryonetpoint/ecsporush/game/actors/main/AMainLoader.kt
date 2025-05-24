package com.cryonetpoint.ecsporush.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.cryonetpoint.ecsporush.game.screens.LoaderScreen
import com.cryonetpoint.ecsporush.game.utils.Acts
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedGroup
import com.cryonetpoint.ecsporush.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgGlow = Image(gdxGame.assetsLoader.GLOW)
    private val imgLogo = Image(gdxGame.assetsLoader.logo)

//    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActors(imgGlow, imgLogo)
        imgGlow.setBounds(0f, 167f, 1068f, 1983f)
        imgGlow.setOrigin(Align.center)

        imgGlow.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(1.3f, 1.3f, 0.5f),
            Acts.scaleTo(1f, 1f, 0.5f),
        )))

        imgLogo.setBounds(276f, 1055f, 515f, 207f)
        imgLogo.setOrigin(Align.center)
        imgLogo.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.95f, 0.95f, 0.4f),
            Acts.scaleTo(1f, 1f, 0.4f),
        )))
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