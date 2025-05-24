package com.vectorvesta.bronfinteh.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.vectorvesta.bronfinteh.game.screens.LoaderScreen
import com.vectorvesta.bronfinteh.game.utils.Acts
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.gdxGame
import com.vectorvesta.bronfinteh.util.log

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgTitle  = Image(gdxGame.assetsLoader.title)
    private val imgLogo   = Image(gdxGame.assetsLoader.logo)

    override fun addActorsOnGroup() {
        addImgTitle()
        addImgLogo()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(429f, 1060f, 324f, 200f)
    }

    private fun addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(170f, 1040f, 241f, 241f)

        imgLogo.setOrigin(0f, 241f / 2f)

        imgLogo.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.95f, 0.95f, 0.35f),
                Acts.scaleTo(1f, 1f, 0.35f),
                Acts.run {
                    log("Animation +++")
                }
            )
        ))
    }

}