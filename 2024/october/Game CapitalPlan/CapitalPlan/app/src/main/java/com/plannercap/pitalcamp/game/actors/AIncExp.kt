package com.plannercap.pitalcamp.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.plannercap.pitalcamp.game.utils.GColor
import com.plannercap.pitalcamp.game.utils.actor.setOnClickListener
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedGroup
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedScreen
import com.plannercap.pitalcamp.game.utils.toBalance

class AIncExp(
    override val screen: AdvancedScreen,
    val nameDesire: String,
    val summa     : Int,
    ls39: LabelStyle,
    ls35: LabelStyle,
    val type: String = "+"
) : AdvancedGroup() {

    private val tmpLS_35 = if (type == "+") LabelStyle(ls35).apply { fontColor = GColor.green }
    else LabelStyle(ls35).apply { fontColor = GColor.red }

    // Actors
    private val btnRemove  = AButton(screen, AButton.Type.Delete)
    private val lblName    = Label(nameDesire, ls39)
    private val lblSumma   = Label(type + summa.toBalance + " ₽", tmpLS_35)

    override fun addActorsOnGroup() {
        addBtnRemove()
        addLbls()
    }

    private fun AdvancedGroup.addBtnRemove() {
        addActor(btnRemove)
        btnRemove.setSize(48f,52f)
        btnRemove.setOnClickListener {
            when(type) {
                "+" -> {
                    screen.game.incomeUtil.update { desireList ->
                        desireList.apply {
                            remove(desireList.first {
                                it.nameDesire == nameDesire && it.summa == summa
                            })
                        }
                    }
                    addAction(Actions.removeActor())
                }
                "-" -> {
                    screen.game.expenseUtil.update { desireList ->
                        desireList.apply {
                            remove(desireList.first {
                                it.nameDesire == nameDesire && it.summa == summa
                            })
                        }
                    }
                    addAction(Actions.removeActor())
                }
            }
        }
    }

    private fun AdvancedGroup.addLbls() {
        addActors(lblName,lblSumma)
        lblName.apply {
            setAlignment(Align.left)
            setBounds(68f,2f,165f,47f)
        }
        lblSumma.apply {
            setAlignment(Align.right)
            setBounds(484f,5f,177f,41f)
        }
    }

}