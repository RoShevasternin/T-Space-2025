package com.ayrym.inperdader.game.actors

import com.ayrym.inperdader.game.utils.SizeScaler
import com.ayrym.inperdader.game.utils.actor.setBoundsScaled
import com.ayrym.inperdader.game.utils.advanced.AdvancedGroup
import com.ayrym.inperdader.game.utils.advanced.AdvancedScreen
import com.ayrym.inperdader.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image

class ATop(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 803f)

    private val imgPanel = Image(gdxGame.assetsAll.top)
    private val imgTitle = Image(gdxGame.assetsAll.aurum)
    private val aLogo    = ALogo(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActors(aLogo, imgTitle)

        aLogo.setBoundsScaled(sizeScaler, 251f, 32f, 120f, 120f)
        imgTitle.setBoundsScaled(sizeScaler, 384f, 46f, 168f, 90f)
    }

}