package com.rayscaya.nasjajdenye.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.rayscaya.nasjajdenye.game.screens.LoaderScreen
import com.rayscaya.nasjajdenye.game.utils.Acts
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedGroup
import com.rayscaya.nasjajdenye.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgPanel = Image(gdxGame.assetsLoader.logo_panel)
    private val imgLogo  = Image(gdxGame.assetsLoader.logo)

    override fun addActorsOnGroup() {
        addImgLogo()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLogo() {
        addActors(imgPanel, imgLogo)
        imgPanel.setBounds(353f, 841f, 224f, 315f)
        imgLogo.setBounds(381f, 960f, 169f, 169f)

        imgLogo.apply {
            setOrigin(Align.center)

            addAction(
                Acts.forever(
                    Acts.sequence(
                        Acts.scaleTo(0.9f, 0.9f, 0.4f, Interpolation.sine),
                        Acts.scaleTo(1f, 1f, 0.4f, Interpolation.fastSlow),
                    )
                )
            )
        }
    }

}