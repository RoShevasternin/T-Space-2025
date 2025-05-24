package com.tinfenker.capitalnoestroy.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.TypingAdapter
import com.github.tommyettinger.textra.TypingLabel
import com.tinfenker.capitalnoestroy.R
import com.tinfenker.capitalnoestroy.game.LibGDXGame
import com.tinfenker.capitalnoestroy.game.utils.Block
import com.tinfenker.capitalnoestroy.game.utils.GColor
import com.tinfenker.capitalnoestroy.game.utils.TIME_ANIM
import com.tinfenker.capitalnoestroy.game.utils.actor.animHide
import com.tinfenker.capitalnoestroy.game.utils.actor.animShow
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedScreen
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedStage
import com.tinfenker.capitalnoestroy.game.utils.font.FontParameter

class ListScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val fontMedium45  = fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(45))

    private val font = Font(fontMedium45)

    private val text1 = "[#${GColor.yellow}]Добавляйте в калькулятор [#${GColor.gray}]срок, сумму и процент, а програма покажет вам прибыль"
    private val text2 = "[#${GColor.gray}]Держите все свои инвестиции [#${GColor.yellow}]в одном месте и под контролем"

    // Actors
    private val imgPanel = Image(game.splash.panel)
    private val imgLogo  = Image(game.splash.logo)
    private val lblText  = TypingLabel(text1, font)

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
        imgPanel.setBounds(77f,889f,620f,160f)
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(180f,941f,414f,57f)
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
            Actions.moveTo(180f,950f, duration, Interpolation.sine),
            Actions.run { blockEnd.invoke() }
        ))
    }

}