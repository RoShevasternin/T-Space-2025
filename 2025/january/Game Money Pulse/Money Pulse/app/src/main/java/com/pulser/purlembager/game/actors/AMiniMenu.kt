package com.pulser.purlembager.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pulser.purlembager.game.actors.checkbox.ACheckBox
import com.pulser.purlembager.game.utils.actor.PosSize
import com.pulser.purlembager.game.utils.actor.disable
import com.pulser.purlembager.game.utils.actor.setBounds
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class AMiniMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgMain   = Image(gdxGame.assetsAll.menu)
    private val imgYellow = Image(gdxGame.assetsAll.selected)

    private val listPosSizeYellow = listOf(
        PosSize(0f, 0f, 245f, 93f),
        PosSize(312f, 0f, 267f, 93f),
    )

    var blockMain   = {}
    var blockDesire = {}
    var blockAll    = {}

    override fun addActorsOnGroup() {
        addImgYellow()
        addAndFillActor(imgMain)
        addBtns()
    }

    // Actors ----------------------------------------------------------------------

    private fun addImgYellow() {
        addActor(imgYellow)
        imgYellow.setBounds(listPosSizeYellow.first())
        imgYellow.disable()
    }

    private fun addBtns() {
        val aMain   = Actor()
        val aDesire = Actor()
        val aAll    = Actor()

        addActors(aMain, aDesire, aAll)
        aMain.apply {
            setBounds(0f, 0f, 245f, 93f)
            setOnClickListener {
                animYellow(listPosSizeYellow[0])
                blockMain()
            }
        }
        aDesire.apply {
            setBounds(312f, 0f, 267f, 93f)
            setOnClickListener {
                animYellow(listPosSizeYellow[1])
                blockDesire()
            }
        }
        aAll.apply {
            setBounds(652f, 0f, 200f, 93f)
            setOnClickListener {
                blockAll()
            }
        }
    }

    // Logic ----------------------------------------------------------------------------

    private fun animYellow(posSize: PosSize) {
        imgYellow.clearActions()
        imgYellow.addAction(Actions.parallel(
            Actions.moveTo(posSize.x, posSize.y, 0.4f, Interpolation.sine),
            Actions.sizeTo(posSize.w, posSize.h, 0.4f, Interpolation.sine)
        ))
    }

}