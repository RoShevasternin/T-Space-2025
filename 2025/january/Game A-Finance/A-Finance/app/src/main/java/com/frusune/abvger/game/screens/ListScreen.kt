package com.frusune.abvger.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.TypingAdapter
import com.github.tommyettinger.textra.TypingLabel
import com.frusune.abvger.R
import com.frusune.abvger.game.LibGDXGame
import com.frusune.abvger.game.utils.Block
import com.frusune.abvger.game.utils.GColor
import com.frusune.abvger.game.utils.TIME_ANIM
import com.frusune.abvger.game.utils.actor.animHide
import com.frusune.abvger.game.utils.actor.animShow
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.advanced.AdvancedStage
import com.frusune.abvger.game.utils.font.FontParameter
import com.frusune.abvger.game.utils.region

class ListScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val fontMedium45  = fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(45))

    private val font = Font(fontMedium45)

    private val text1 = "[#${GColor.red}]Добавляйте в калькулятор [#${GColor.gray}]срок, сумму и процент, а програма покажет вам прибыль"
    private val text2 = "[#${GColor.gray}]Держите все свои инвестиции [#${GColor.red}]в одном месте и под контролем"

    // Actors
    private val imgPanel = Image(game.splash.panel)
    private val imgLogo  = Image(game.splash.logo)
    private val lblText  = TypingLabel(text1, font)

    override fun show() {
        setBackBackground(game.splash.backg.region)
        super.show()
    }

    override fun dispose() {
        super.dispose()
        font.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPanel()
        addImgLogo()
        addLblText()

        animPanelAndLogo {
            lblText.resume()
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(211f,844f,353f,252f)
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(263f,893f,249f,152f)
    }

    private fun AdvancedStage.addLblText() {
        lblText.pause()
        addActor(lblText)
        lblText.setBounds(108f,664f,557f,207f)
        lblText.wrap      = true
        lblText.alignment = Align.center

        var counter = 0
        lblText.typingListener = object : TypingAdapter() {
            override fun end() {
                when (counter) {
                    0 -> {
                        lblText.animHide(TIME_ANIM) {
                            counter = 1
                            lblText.setText("")
                            lblText.animShow(TIME_ANIM) {
                                counter = 2
                                lblText.restart(text2)
                            }
                        }
                    }
                    2 -> {
                        stageUI.root.animHide(TIME_ANIM) {
                            game.activity.setNavBarColor(R.color.white)
                            game.backgroundColor = Color.WHITE
                            game.navigationManager.navigate(MainScreen::class.java.name)
                        }
                    }
                }
            }
        }
    }

    // Anim --------------------------------------------------------------

    private fun animPanelAndLogo(blockEnd: Block) {
        val duration = 0.5f
        imgPanel.addAction(Actions.parallel(
            Actions.sizeTo(640f,552f, duration, Interpolation.sine),
            Actions.moveTo(67f,564f, duration, Interpolation.sine),
        ))
        imgLogo.addAction(Actions.sequence(
            Actions.moveTo(263f,903f, duration, Interpolation.sine),
            Actions.run { blockEnd.invoke() }
        ))
    }

}