package com.inveanst.litka.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.TypingLabel
import com.inveanst.litka.game.actors.layout.AVerticalGroup
import com.inveanst.litka.game.actors.layout.Layout
import com.inveanst.litka.game.utils.*
import com.inveanst.litka.game.utils.actor.animHide
import com.inveanst.litka.game.utils.actor.animShow
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.font.FontParameter
import com.inveanst.litka.util.log

class ATestPanel(
    override val screen: AdvancedScreen,
    val title     : String,
    val question  : String,
    val answerList: List<String>
) : AdvancedGroup() {

    private var counter = 1

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL)
    private val font20        = screen.fontGenerator_Raleway_SemiBold.generateFont(fontParameter.setSize(20 * FONT_SCALE))
    private val font16        = screen.fontGenerator_Raleway_SemiBold.generateFont(fontParameter.setSize(16 * FONT_SCALE))

    private val ls20 = LabelStyle(font20, GColor.text)

    private val imgPanel = Image(screen.game.all.panel_9)

    private val verticalGroup = AVerticalGroup(screen,30f, endGap = 0f,
        alignmentH = Layout.AlignmentHorizontal.CENTER,
    )

    private val header      = AHeader(screen, title, 0f)
    private val lblQuestion = Label(question, ls20)
    private val lblCounter  = TypingLabel("{COLOR=#${GColor.green}}$counter{COLOR=#${GColor.text}}/5", Font(font16))

    private val listAnswer = MutableList(4) { AAnswer(screen,it == 0, answerList[it]) }

    var blockBack  = {}
    var blockCheck: (Boolean) -> Unit = { }

    override fun addActorsOnGroup() {
        addImgPanel()
        addVerticalGroup()
        addLblCounter()
    }

    private fun addImgPanel() {
        addAndFillActor(imgPanel)
    }

    private fun addVerticalGroup() {
        addAndFillActor(verticalGroup)
        verticalGroup.apply {
            addHeader()
            addLblQuestion()
            addListAnswer()
        }
    }

    private fun addLblCounter() {
        addActor(lblCounter)
        lblCounter.alignment = Align.center
        lblCounter.font.scale(1f / FONT_SCALE)
        lblCounter.setBounds(146f,38f,27f,11f)
    }

    private fun AVerticalGroup.addHeader() {
        header.width = width
        addActor(header)
        header.blockBack = { this@ATestPanel.blockBack() }
    }

    private fun AVerticalGroup.addLblQuestion() {
        lblQuestion.setAlignment(Align.center)
        lblQuestion.wrap = true
        lblQuestion.width = 270f
        lblQuestion.setFontScale(1f / FONT_SCALE)
        lblQuestion.invalidate()
        lblQuestion.height = lblQuestion.prefHeight
        addActor(lblQuestion)
    }

    private fun AVerticalGroup.addListAnswer() {
        listAnswer.shuffle()
        listAnswer.onEach { aAnswer ->
            aAnswer.width = 270f
            addActor(aAnswer)

            aAnswer.blockCheck = { blockCheck(aAnswer.isWin) }
        }
    }

    // Logic ---------------------------------------------------

    fun next(
        question  : String,
        answerList: List<String>
    ) {
        listAnswer.onEach { it.disable() }
        counter += 1

        clearActions()
        addAction(Actions.sequence(
            Actions.delay(0.5f),
            Actions.run {
                lblQuestion.animHide(TIME_ANIM)
                listAnswer.onEach { it.animHide{ it.reset() } }
            },
            Actions.delay(TIME_ANIM),
            Actions.run {
                lblQuestion.setText(question)
                listAnswer.shuffle()
                listAnswer.onEachIndexed { index, aAnswer -> aAnswer.update(index == 0, answerList[index]) }

                verticalGroup.update()

                listAnswer.onEach { it.animShow(TIME_ANIM) }
                lblQuestion.animShow(TIME_ANIM) {
                    listAnswer.onEach { it.enable() }
                    lblCounter.setText("{COLOR=#${GColor.green}}$counter{COLOR=#${GColor.text}}/5")
                }
            }
        ))

    }


}