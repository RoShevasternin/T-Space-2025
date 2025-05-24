package com.finansoviy.gurochek.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.finansoviy.gurochek.game.screens.LoaderScreen
import com.finansoviy.gurochek.game.utils.Acts
import com.finansoviy.gurochek.game.utils.advanced.AdvancedGroup
import com.finansoviy.gurochek.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo = Image(gdxGame.assetsLoader.logo)
    private val imgText = Image(gdxGame.assetsLoader.text)

    override fun addActorsOnGroup() {
        addImgPanels()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanels() {
        addActors(imgLogo, imgText)
        imgLogo.setBounds(146f, 963f, 241f, 241f)
        imgText.setBounds(413f, 1011f, 440f, 132f)

        imgLogo.setOrigin(Align.center)
        imgLogo.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.8f, 0.8f, 0.5f),
                Acts.scaleTo(1f, 1f, 0.4f),
            )
        ))
    }

}