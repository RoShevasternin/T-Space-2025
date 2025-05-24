package com.borocbernay.kasshsemir.game.actors.main

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.borocbernay.kasshsemir.game.screens.LoaderScreen
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedGroup
import com.borocbernay.kasshsemir.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo  = Image(gdxGame.assetsLoader.logo)
    private val imgTitle = Image(gdxGame.assetsLoader.title)

    private val imgWhite = Image(gdxGame.assetsLoader.white)
    private val imgGreen = Image(gdxGame.assetsLoader.green)

//    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActor(imgWhite)
        imgWhite.setBounds(-119f, 382f, 1107f, 1107f)
        imgWhite.startPulseAnimation(2f, 1f, 0.8f)

        addActor(imgGreen)
        imgGreen.setBounds(16f, 520f, 833f, 833f)
        imgGreen.startPulseAnimation(1.3f, 1f, 0.75f)



        addActors(imgLogo, imgTitle)
        imgLogo.setBounds(124f, 820f, 232f, 232f)

        imgTitle.setBounds(366f, 874f, 455f, 124f)
        //imgTitle.setOrigin(Align.center)
        //imgTitle.addAction(Acts.forever(Acts.parallel(Acts.rotateBy(-360f, 12f, Interpolation.linear))))
    }

    private fun addProgress() {
        //addActor(progress)
        //progress.setBounds(67f, 687f, 558f, 8f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        //progress.progressPercentFlow.value = percent.toFloat()
    }

    private fun Image.startPulseAnimation(
        minScale: Float = 0.9f,
        maxScale: Float = 1.1f,
        duration: Float = 0.5f
    ) {
        val pulseAction = Actions.forever(
            Actions.sequence(
                Actions.scaleTo(maxScale, maxScale, duration),
                Actions.scaleTo(minScale, minScale, duration)
            )
        )
        this.setOrigin(Align.center)
        this.addAction(pulseAction)
    }

}