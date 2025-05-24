package com.sukapital.saepital.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.sukapital.saepital.game.utils.Block
import com.sukapital.saepital.game.utils.actor.setOnClickListener
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.toBalance

class ADesire(
    override val screen: AdvancedScreen,
    val nameDesire: String,
    val summa     : Int,
    ls39: LabelStyle,
    ls35: LabelStyle,
) : AdvancedGroup() {

    private val period = (1..12).random()

    // Actors
    private val imgPanel   = Image(screen.game.loader.panel_9)
    private val imgLayout  = Image(screen.game.all.btn_desire)
    private val lblName    = Label(nameDesire, ls39)
    private val lblSumma   = Label(summa.toBalance + "₽", ls35)
    private val lblPeriod  = Label("$period ${getMonth()}", ls35)
    private val aRemove    = Actor()
    private val aEdit      = Actor()

    // Field
    var blockRemove = Block {}
    var blockEdit   = Block {}

    override fun addActorsOnGroup() {
        addAndFillActors(imgPanel)

        addImgLayout()
        addLbls()
        addBtns()

    }

    private fun AdvancedGroup.addImgLayout() {
        addActor(imgLayout)
        imgLayout.setBounds(115f,42f,452f,133f)
    }

    private fun AdvancedGroup.addLbls() {
        addActors(lblName,lblSumma,lblPeriod)
        lblName.apply {
            setAlignment(Align.center)
            setBounds(243f,194f,194f,28f)
        }
        lblSumma.apply {
            setAlignment(Align.left)
            setBounds(115f,114f,158f,25f)
        }
        lblPeriod.apply {
            setAlignment(Align.right)
            setBounds(393f,114f,175f,25f)
        }
    }

    private fun AdvancedGroup.addBtns() {
        addActors(aRemove, aEdit)
        aRemove.apply {
            setBounds(117f,44f,143f,26f)
            setOnClickListener(screen.game.soundUtil) { blockRemove.invoke() }
        }
        aEdit.apply {
            setBounds(335f, 44f, 230f, 26f)
            setOnClickListener(screen.game.soundUtil) { blockEdit.invoke() }
        }
    }

    // Logic -------------------------------------------------------------

    private fun getMonth() = when {
        period == 1            -> "месяц"
        period in (2..4) -> "месяца"
        else                   -> "месяцев"
    }

}