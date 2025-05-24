package com.borocbernay.kasshsemir.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.borocbernay.kasshsemir.game.utils.GameColor
import com.borocbernay.kasshsemir.game.utils.actor.setOnClickListener
import com.borocbernay.kasshsemir.game.utils.actor.setOnTouchListener
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedGroup
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedScreen
import com.borocbernay.kasshsemir.game.utils.font.FontParameter
import com.borocbernay.kasshsemir.game.utils.gdxGame
import com.borocbernay.kasshsemir.game.utils.isNumTake

class AInputs(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font32        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(32))

    private val ls32 = Label.LabelStyle(font32, GameColor.green_22_40)

    private val imgInput = Image(gdxGame.assetsAll.INPUTS)
    val listInputs = List(6) { Label("", ls32) }

    var blockAdd = {}

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {
        addAndFillActor(imgInput)
        addListInputText()

        val btnAdd = Actor()
        addActor(btnAdd)
        btnAdd.setBounds(0f, 0f, 772f, 103f)
        btnAdd.setOnTouchListener(gdxGame.soundUtil) { blockAdd() }
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

        var ny = 1220f
        listInputs.forEachIndexed { index, input ->
            addActor(input)
            input.setAlignment(Align.center or Align.left)

            input.setBounds(27f, ny, 718f, 101f)

            ny -= 111 + 101

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