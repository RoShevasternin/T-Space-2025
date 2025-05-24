package com.sberigatelny.finexpertaizer.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedGroup
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedScreen
import com.sberigatelny.finexpertaizer.game.utils.font.FontParameter
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.game.utils.runGDX
import kotlinx.coroutines.launch

class ATop(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + ":")
    private val font      = screen.fontGenerator_Bold.generateFont(parameter.setSize(52))
    private val ls        = Label.LabelStyle(font, Color.WHITE)

    private val imgPanel   = Image(gdxGame.assetsAll.time_and_gold)
    private val lblBalance = Label("", ls)

    val aTimer = FastMinuteTimer(ls)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActors(aTimer, lblBalance)

        aTimer.setBounds(126f, 18f, 141f, 63f)
        lblBalance.setBounds(703f, 18f, 145f, 63f)
        lblBalance.setAlignment(Align.center)

        coroutine?.launch {
            gdxGame.ds_Balance.flow.collect { balance ->
                runGDX {
                    lblBalance.setText(balance)
                }
            }
        }
    }

}