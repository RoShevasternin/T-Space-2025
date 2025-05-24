package com.reinvestkapital.kapiterraw.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.TextInputListener
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.reinvestkapital.kapiterraw.game.actors.input.APercent
import com.reinvestkapital.kapiterraw.game.actors.input.APeriod
import com.reinvestkapital.kapiterraw.game.actors.input.ASumma
import com.reinvestkapital.kapiterraw.game.data.Contribution
import com.reinvestkapital.kapiterraw.game.utils.*
import com.reinvestkapital.kapiterraw.game.utils.actor.animHide
import com.reinvestkapital.kapiterraw.game.utils.actor.animShow
import com.reinvestkapital.kapiterraw.game.utils.actor.setOnClickListener
import com.reinvestkapital.kapiterraw.game.utils.advanced.AdvancedGroup
import com.reinvestkapital.kapiterraw.game.utils.advanced.AdvancedScreen
import com.reinvestkapital.kapiterraw.game.utils.font.FontParameter
import java.util.UUID

class AInputPanel(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val fontBold45  = screen.fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(45))
    private val fontBold53  = screen.fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(53))

    private val ls45 = LabelStyle(fontBold45, Color.BLACK)
    private val ls53 = LabelStyle(fontBold53, Color.BLACK)

    // Actors
    private val imgPanel   = Image(screen.game.all.PANEL_BIGGER)

    private val tmpGroup   = TmpGroup(screen)
    private val imgInput   = Image(screen.game.all.BIGGER_INPUT)
    private val lblName    = Label("--", ls45)
    private val lblPeriod  = Label("0", ls45)
    private val lblPercent = Label("0", ls45)
    private val lblSumma   = Label("0", ls45)
    private val btnRemove  = AButton(screen, AButton.Static.Type.Remove)
    private val btnDone    = AButton(screen, AButton.Static.Type.Done)

    private val aPeriod  = APeriod(screen, ls53)
    private val aPercent = APercent(screen, ls53)
    private val aSumma   = ASumma(screen, ls53)

    // Field
    var blockRemove = Block {}
    var blockDone   = Block {}

    override fun addActorsOnGroup() {
        addAndFillActors(imgPanel, tmpGroup)

        tmpGroup.apply {
            addImgInput()
            addLbls()
            addBtns()
        }

        addPanels()
    }

    private fun AdvancedGroup.addImgInput() {
        addActor(imgInput)
        imgInput.setBounds(114f,216f,517f,710f)
    }

    private fun AdvancedGroup.addLbls() {
        addActors(lblName,lblPeriod,lblPercent,lblSumma)
        lblName.apply {
            setAlignment(Align.center)
            setBounds(114f,787f,516f,95f)
            setOnClickListener(screen.game.soundUtil) {
                Gdx.input.getTextInput(object : TextInputListener {
                    override fun input(text: String?) {
                        val result = if (text.isNullOrEmpty()) "--" else text.take(18)
                        setText(result)
                    }
                    override fun canceled() {}
                }, "Название", "", "Введите название");
            }
        }
        lblPeriod.apply {
            setAlignment(Align.center)
            setBounds(114f,597f,206f,95f)
            setOnClickListener(screen.game.soundUtil) {
                tmpGroup.animHide(TIME_ANIM) { tmpGroup.disable() }
                aPeriod.enable()
                aPeriod.animShow(TIME_ANIM)
            }
        }
        lblPercent.apply {
            setAlignment(Align.center)
            setBounds(114f,407f,206f,95f)
            setOnClickListener(screen.game.soundUtil) {
                tmpGroup.animHide(TIME_ANIM) { tmpGroup.disable() }
                aPercent.enable()
                aPercent.animShow(TIME_ANIM)
            }
        }
        lblSumma.apply {
            setAlignment(Align.center)
            setBounds(114f,217f,413f,95f)
            setOnClickListener(screen.game.soundUtil) {
                tmpGroup.animHide(TIME_ANIM) { tmpGroup.disable() }
                aSumma.enable()
                aSumma.animShow(TIME_ANIM)
            }
        }
    }

    private fun AdvancedGroup.addBtns() {
        addActors(btnRemove, btnDone)
        btnRemove.apply {
            setBounds(114f, 61f, 166f, 94f)
            setOnClickListener { blockRemove.invoke() }
        }
        btnDone.apply {
            setBounds(464f, 61f, 166f, 94f)
            setOnClickListener {
                if (checkInputLbls()) {
                    screen.game.contributionUtil.update {
                        it.apply { add(Contribution(
                            uuid    = System.currentTimeMillis(),
                            title   = lblName.text.toString(),
                            period  = lblPeriod.text.toString().removeNonDigits().toInt(),
                            percent = lblPercent.text.toString().removeNonDigits().toInt(),
                            summa   = lblSumma.text.toString().removeNonDigits().toInt(),
                        )) }
                    }
                    blockDone.invoke()
                }
                else screen.game.soundUtil.apply { play(fail_game, 0.15f) }
            }
        }
    }

    private fun addPanels() {
        aPeriod.apply {
            disable()
            animHide()
        }
        aPercent.apply {
            disable()
            animHide()
        }
        aSumma.apply {
            disable()
            animHide()
        }
        addActors(aPeriod, aPercent, aSumma)
        aPeriod.apply {
            setBounds(114f,61f,517f,741f)
            blockProgress = { lblPeriod.setText(it) }
            blockDone = {
                aPeriod.animHide(TIME_ANIM) { aPeriod.disable() }
                tmpGroup.animShow(TIME_ANIM) { tmpGroup.enable() }
            }
        }
        aPercent.apply {
            setBounds(114f,61f,517f,741f)
            blockProgress = { lblPercent.setText(it) }
            blockDone = {
                aPercent.animHide(TIME_ANIM) { aPercent.disable() }
                tmpGroup.animShow(TIME_ANIM) { tmpGroup.enable() }
            }
        }
        aSumma.apply {
            setBounds(114f,61f,517f,741f)
            blockProgress = { lblSumma.setText(it.toBalance) }
            blockDone = {
                aSumma.animHide(TIME_ANIM) { aSumma.disable() }
                tmpGroup.animShow(TIME_ANIM) { tmpGroup.enable() }
            }
        }
    }

    // Logic -------------------------------------------------------------

    private fun checkInputLbls(): Boolean {
        val resultName = (lblName.text.toString() != "--")
        val resultAll  = listOf(lblPeriod,lblPercent,lblSumma).all { it.text.toString() != "0" }
        return (resultName && resultAll)
    }

}