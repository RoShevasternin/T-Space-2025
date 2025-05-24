package com.barabanovich.helowerskay.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.barabanovich.helowerskay.game.screens.LoaderScreen
import com.barabanovich.helowerskay.game.utils.Acts
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedGroup
import com.barabanovich.helowerskay.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLogo = Image(gdxGame.assetsLoader.logo)
    private val imgText = Image(gdxGame.assetsLoader.gos_res)

    override fun addActorsOnGroup() {
        addImgPanels()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanels() {
        addActors(imgText, imgLogo)
        imgLogo.setBounds(262f, 1070f, 180f, 180f)
        imgText.setBounds(208f, 1016f, 649f, 289f)

        imgLogo.setOrigin(Align.center)
        imgLogo.addAction(Acts.forever(
            Acts.sequence(
                Acts.scaleTo(0.8f, 0.8f, 0.5f),
                Acts.scaleTo(1f, 1f, 0.4f),
            )
        ))
    }

}