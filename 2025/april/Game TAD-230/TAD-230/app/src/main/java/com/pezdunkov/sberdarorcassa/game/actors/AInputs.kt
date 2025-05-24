package com.pezdunkov.sberdarorcassa.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pezdunkov.sberdarorcassa.game.utils.actor.setOnClickListener
import com.pezdunkov.sberdarorcassa.game.utils.actor.setOnTouchListener
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedGroup
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedScreen
import com.pezdunkov.sberdarorcassa.game.utils.font.FontParameter
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame
import com.pezdunkov.sberdarorcassa.game.utils.isNumTake

class AInputs(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font26        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(26))

    private val ls26 = Label.LabelStyle(font26, Color.WHITE)

    private val imgInput   = Image(gdxGame.assetsAll.INPUTES)
    val listInputs = List(6) { Label("", ls26) }

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {
        addAndFillActor(imgInput)
        addListInputText()
    }

    private fun addListInputText() {
        val listMaxLength = listOf(15, 5, 6, 6, 2, 2)
        val listNames = listOf(
            "Название товара",
            "Количество (шт)",
            "Цена закупки за 1 Шт",
            "Цена продажи за 1 Шт",
            "Налог",
            "Маржа",
        )

        var ny = 871f
        listInputs.forEachIndexed { index, input ->
            addActor(input)
            input.setAlignment(Align.center or Align.left)

            input.setBounds(21f, ny, 593f, 83f)

            ny -= 91 + 83

            input.setOnTouchListener {
                Gdx.input.getTextInput(object : Input.TextInputListener {
                    override fun input(text: String) {
                        input.setText(if (index == 0) text.take(listMaxLength[0]) else text.isNumTake(listMaxLength[index]).toString())
                    }

                    override fun canceled() {}
                }, listNames[index], "", "", if (index == 0) Input.OnscreenKeyboardType.Default else Input.OnscreenKeyboardType.NumberPad)
            }
        }
    }

}