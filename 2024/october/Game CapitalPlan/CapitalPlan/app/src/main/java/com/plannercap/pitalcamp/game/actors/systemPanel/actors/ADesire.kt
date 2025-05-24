package com.plannercap.pitalcamp.game.actors.systemPanel.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.plannercap.pitalcamp.game.data.Desire
import com.plannercap.pitalcamp.game.utils.GColor
import com.plannercap.pitalcamp.game.utils.actor.setOnClickListener
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedGroup
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedScreen
import com.plannercap.pitalcamp.game.utils.font.FontParameter
import com.plannercap.pitalcamp.game.utils.removeNonDigits
import com.plannercap.pitalcamp.game.utils.runGDX
import com.plannercap.pitalcamp.game.utils.toBalance

class ADesire(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val fontMedium39  = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(39))

    private val ls39 = LabelStyle(fontMedium39, GColor.dark)

    private val imgPanel = Image(screen.game.all.Desire)
    private val lblName  = Label("Название", ls39)
    private val lblSumma = Label("Сумма", ls39)

    private var inputName  = ""
    private var inputSumma = ""

    var blockRemove: () -> Unit = {}
    var blockDone  : () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLbls()
        addBtns()
    }

    private fun addLbls() {
        addActors(lblName, lblSumma)
        lblName.setBounds(160f,341f,186f,51f)
        lblSumma.setBounds(160f,219f,186f,51f)

        val aName  = Actor()
        val aSumma = Actor()
        addActors(aName, aSumma)
        aName.apply {
            setBounds(160f,325f,507f,67f)
            setOnClickListener(screen.game.soundUtil) {
                Gdx.input.getTextInput(
                    inputListenerName,
                    "Введите название",
                    "",
                    "Название",
                    Input.OnscreenKeyboardType.Default
                )
            }
        }
        aSumma.apply {
            setBounds(160f,203f,507f,67f)
            setOnClickListener(screen.game.soundUtil) {
                Gdx.input.getTextInput(
                    inputListenerSumma,
                    "Введите сумму",
                    "",
                    "Сумма",
                    Input.OnscreenKeyboardType.NumberPad
                )
            }
        }
    }

    private fun addBtns() {
        val aRemove = Actor()
        val aDone   = Actor()
        addActors(aRemove, aDone)
        aRemove.apply {
            setBounds(160f,66f,132f,81f)
            setOnClickListener(screen.game.soundUtil) {
                blockRemove()
            }
        }
        aDone.apply {
            setBounds(535f,66f,132f,81f)
            setOnClickListener(screen.game.soundUtil) {
                if (checkInputLbls()) {
                    screen.game.desireUtil.update { desireList ->
                        desireList.apply {
                            add(
                                Desire(
                                    System.currentTimeMillis(),
                                    nameDesire = inputName,
                                    summa      = inputSumma.removeNonDigits().toInt()
                                )
                            )
                        }
                    }
                    blockDone()
                } else screen.game.soundUtil.apply { play(fail_game, 0.15f) }
            }
        }
    }

    // Logic -------------------------------------------------------------------

    private val inputListenerName = object : Input.TextInputListener {
        override fun input(text: String) {
            runGDX {
                val result = text.take(20)
                inputName  = result
                lblName.setText(result)
            }
        }

        override fun canceled() {}
    }

    private val inputListenerSumma = object : Input.TextInputListener {
        override fun input(text: String) {
            runGDX {
                val result = text.take(7).removeNonDigits()
                inputSumma = result
                lblSumma.setText(result.toInt().toBalance)
            }
        }

        override fun canceled() {}
    }

    fun reset() {
        lblName.setText("Название")
        lblSumma.setText("Сумма")
    }

    private fun checkInputLbls(): Boolean {
        val resultName  = inputName.isNotBlank()
        val resultSumma = inputSumma.isNotBlank()
        return (resultName && resultSumma)
    }

}