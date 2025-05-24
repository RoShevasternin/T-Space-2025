package com.padrestoranom.easypaydonalds.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.padrestoranom.easypaydonalds.game.utils.SizeScaler
import com.padrestoranom.easypaydonalds.game.utils.WIDTH_UI
import com.padrestoranom.easypaydonalds.game.utils.actor.setBoundsScaled
import com.padrestoranom.easypaydonalds.game.utils.actor.setOnClickListener
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGroup
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedScreen
import com.padrestoranom.easypaydonalds.game.utils.gdxGame

class AMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    private val aHome    = Actor()
    private val aPlus    = Actor()
    private val aHistory = Actor()

    var blockHome    = {}
    var blockPlus    = {}
    var blockHistory = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.menu))

        addActors(aHome, aPlus, aHistory)
        aHome.setBoundsScaled(sizeScaler, 97f, 46f, 166f, 80f)
        aPlus.setBoundsScaled(sizeScaler, 360f, 31f, 111f, 111f)
        aHistory.setBoundsScaled(sizeScaler, 568f, 46f, 166f, 80f)

        aHome.setOnClickListener(gdxGame.soundUtil) { blockHome() }
        aPlus.setOnClickListener(gdxGame.soundUtil) { blockPlus() }
        aHistory.setOnClickListener(gdxGame.soundUtil) { blockHistory() }
    }

}