package com.pulser.purlembager.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.utils.SizeScaler
import com.pulser.purlembager.game.utils.actor.PosSize
import com.pulser.purlembager.game.utils.actor.disable
import com.pulser.purlembager.game.utils.actor.setBounds
import com.pulser.purlembager.game.utils.actor.setBoundsScaled
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class ADialogMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 946f)

    private val imgYellow = Image(gdxGame.assetsAll.selected)
    private val aMain     = Image(gdxGame.assetsAll.add_button)

    private val listPosSizeYellow = listOf(
        PosSize(0f, 220f, 276f, 107f),
        PosSize(290f, 220f, 335f, 107f),
        PosSize(650f, 220f, 295f, 107f),
    )

    var blockIncome  = {}
    var blockDesire  = {}
    var blockExpense = {}
    var blockPlus    = {}

    override fun addActorsOnGroup() {
        addAndFillActor(aMain)
        addImgYellow()
        addBtns()
    }

    private fun addImgYellow() {
        addActor(imgYellow)
        val posSize = listPosSizeYellow.first()
        imgYellow.setBoundsScaled(sizeScaler, posSize.x, posSize.y, posSize.w, posSize.h)
        imgYellow.disable()
        imgYellow.color.a = 0f
    }

    private fun addBtns() {
        val aIncome  = Actor()
        val aDesire  = Actor()
        val aExpense = Actor()
        val aPlus    = Actor()

        addActors(aIncome, aDesire, aExpense, aPlus)
        aIncome.apply {
            setBoundsScaled(sizeScaler, 0f, 220f, 276f, 107f)
            setOnClickListener {
                animYellow(listPosSizeYellow[0])
                blockIncome()
            }
        }
        aDesire.apply {
            setBoundsScaled(sizeScaler, 290f, 220f, 335f, 107f)
            setOnClickListener {
                animYellow(listPosSizeYellow[1])
                blockDesire()
            }
        }
        aExpense.apply {
            setBoundsScaled(sizeScaler, 650f, 220f, 295f, 107f)
            setOnClickListener {
                animYellow(listPosSizeYellow[2])
                blockExpense()
            }
        }
        aPlus.apply {
            setBoundsScaled(sizeScaler, 380f, 0f, 188f, 188f)
            setOnClickListener {
                blockPlus()
            }
        }
    }

    // Logic ----------------------------------------------------------------------------

    private fun animYellow(posSize: PosSize) {
        imgYellow.clearActions()
        imgYellow.color.a = 0.25f
        imgYellow.addAction(Actions.parallel(
            Actions.moveTo(posSize.x.scaled, posSize.y.scaled),
            Actions.sizeTo(posSize.w.scaled, posSize.h.scaled)
        ))
    }

}