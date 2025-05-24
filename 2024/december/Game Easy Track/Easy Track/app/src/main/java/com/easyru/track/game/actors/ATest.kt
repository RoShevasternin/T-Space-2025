package com.easyru.track.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.easyru.track.game.actors.autoLayout.AVerticalGroup
import com.easyru.track.game.actors.autoLayout.AutoLayout
import com.easyru.track.game.screens.TestScreen
import com.easyru.track.game.utils.GameColor
import com.easyru.track.game.utils.actor.disable
import com.easyru.track.game.utils.actor.setOnClickListener
import com.easyru.track.game.utils.advanced.AdvancedGroup
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.font.FontParameter
import com.easyru.track.game.utils.gdxGame
import com.easyru.track.game.utils.runGDX

class ATest(
    override val screen: AdvancedScreen,
    val title     : String,
    val listAnswer: List<String>,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val fontR_43 = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(43))
    private val fontR_35 = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(35))

    private val lsR_43 = Label.LabelStyle(fontR_43, GameColor.black_24)
    private val lsR_35 = Label.LabelStyle(fontR_35, GameColor.black_24)

    private val imgPanel = Image(gdxGame.assetsAll.nine_white)

    private val vertical    = AVerticalGroup(screen, 43f, isWrap = true)
    private val lblTitle    = Label(title, lsR_43)
    private val listAAnswer = List(4) { AAnswer(screen, lsR_35) }

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addVertical()

        runGDX {
            height = (vertical.height + 103f)

            if (height > 775f) {
                y -= (height - 775f)
            } else {
                y += (775f - height)
            }
        }
    }

    override fun sizeChanged() {
        super.sizeChanged()
        imgPanel.setSize(width, height)
    }

    // Actors Vertical ---------------------------------------------------

    private fun addVertical() {
        addActor(vertical)
        vertical.apply {
            setPosition(49f, 53f)
            width = 597f

            addLblTitle()
            addSpace(21f)
            addListAnswer()
        }

    }

    private fun AVerticalGroup.addLblTitle() {
        addActor(lblTitle)

        lblTitle.apply {
            setAlignment(Align.center)
            wrap   = true
            width  = 597f
            height = prefHeight
        }

    }

    private fun AVerticalGroup.addListAnswer() {
        listAAnswer.onEachIndexed { index, answer ->
            if (index == 0) answer.isTrue = true
            answer.width  = 589f
            answer.height = 1f
            answer.setText(listAnswer[index])
            answer.setOnClickListener(gdxGame.soundUtil) {
                answer.setResult()
                listAAnswer.onEach { it.disable() }

                if (answer.isTrue) {
                    TestScreen.ANSWER_COUNT++
                }
            }
        }
        listAAnswer.shuffled().onEach {
            addActor(it)
            it.updateSize()
        }
    }

    private fun AVerticalGroup.addSpace(space: Float) {
        addActor(Actor().apply { height = space })
    }

}