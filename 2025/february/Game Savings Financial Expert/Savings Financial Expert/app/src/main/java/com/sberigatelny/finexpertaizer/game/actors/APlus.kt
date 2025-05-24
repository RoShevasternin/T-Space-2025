package com.sberigatelny.finexpertaizer.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.sberigatelny.finexpertaizer.game.utils.GameColor
import com.sberigatelny.finexpertaizer.game.utils.HEIGHT_UI
import com.sberigatelny.finexpertaizer.game.utils.WIDTH_UI
import com.sberigatelny.finexpertaizer.game.utils.actor.disable
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedGroup
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedScreen
import com.sberigatelny.finexpertaizer.game.utils.font.FontParameter
import kotlin.random.Random

class APlus(
    override val screen: AdvancedScreen,
    symbol: Int,
): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "+")

    private val font = screen.fontGenerator_Bold.generateFont(parameter.setSize(144))
    private val ls   = Label.LabelStyle(font, Color.WHITE)

    private val listLbl = List(25) { Label("+$symbol", ls) }

    override fun addActorsOnGroup() {
        disable()
        addItems()
    }

    private fun addItems() {
        listLbl.onEach { lbl ->
            addActor(lbl)
            lbl.setBounds(WIDTH_UI, HEIGHT_UI, 144f, 173f)
            lbl.setAlignment(Align.center)
        }
    }

    private var currentIndex = 0

    fun animInPos(pos: Vector2) {
        if (currentIndex + 1 > listLbl.lastIndex) currentIndex = 0

        listLbl[currentIndex++].also { coin ->
            coin.clearActions()
            coin.addAction(Actions.sequence(
                Actions.alpha(1f),
                Actions.moveTo(pos.x, pos.y),

                Actions.delay(0.75f),
                Actions.fadeOut(0.5f),
                Actions.moveTo(WIDTH_UI, HEIGHT_UI),
            ))
        }
    }

    fun updateSymbol(symbol: Int) {
        listLbl.onEach { it.setText("+$symbol") }
    }

}