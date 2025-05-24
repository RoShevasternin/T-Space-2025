package com.liberator.wisoliter.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.liberator.wisoliter.game.actors.progress.AProgressXP
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.font.FontParameter
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.game.utils.runGDX
import com.liberator.wisoliter.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ABalance(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "Мощь xp").setSize(41)
    private val font41        = screen.fontGenerator_PusiaBold.generateFont(fontParameter)

    private val ls41 = LabelStyle(font41, GameColor.white)

    val imgPanel   = Image(gdxGame.assetsAll.balance)
    val lblBalance = Label("", ls41)
    val lblXP      = Label("", ls41)
    val progress   = AProgressXP(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblBalance()
        addLblXP()
        addProgress()
    }

    // Actors -----------------------------------------------------------------------------------

    private fun addLblBalance() {
        addActor(lblBalance)
        lblBalance.setBounds(88f, 38f, 68f, 28f)

        coroutine?.launch(Dispatchers.IO) {
            gdxGame.ds_Balance.flow.collect {
                runGDX { lblBalance.setText(it) }
            }
        }
    }

    private fun addLblXP() {
        addActor(lblXP)
        lblXP.setBounds(380f, 102f, 302f, 49f)
        lblXP.setAlignment(Align.right)

        coroutine?.launch(Dispatchers.IO) {
            var oldValue    = 0
            var percent10xp = 0f

            gdxGame.ds_XP.flow.collect { xp ->
                runGDX {
                    lblXP.setText("Мощь $xp xp")

                    if (xp > oldValue) {
                        percent10xp = 0f
                        progress.progressPercentFlow.value = 100f
                    } else {
                        if (percent10xp == 0f) percent10xp = 100f / ((xp + 10) / 10)
                        progress.progressPercentFlow.value -= percent10xp
                    }

                    oldValue = xp
                }
            }
        }
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(372f, 33f, 310f, 41f)
    }

}