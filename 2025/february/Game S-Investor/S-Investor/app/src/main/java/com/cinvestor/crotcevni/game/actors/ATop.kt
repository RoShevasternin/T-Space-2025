package com.cinvestor.crotcevni.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.cinvestor.crotcevni.game.actors.progress.AProgressGame
import com.cinvestor.crotcevni.game.utils.GameColor
import com.cinvestor.crotcevni.game.utils.SizeScaler
import com.cinvestor.crotcevni.game.utils.actor.setBoundsScaled
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedGroup
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedScreen
import com.cinvestor.crotcevni.game.utils.font.FontParameter
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.cinvestor.crotcevni.game.utils.runGDX
import kotlinx.coroutines.launch

var progress: AProgressGame? = null
    private set

class ATop(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 933f)

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font      = screen.fontGenerator_Bold.generateFont(parameter.setSize(50))
    private val ls        = Label.LabelStyle(font, GameColor.black_44)

    private val imgPanel   = Image(gdxGame.assetsAll.TOP)
    private val lblBalance = Label("", ls)

    override fun addActorsOnGroup() {
        val progFlow = progress?.progressPercentFlow?.value ?: 0f
        progress = AProgressGame(screen)
        progress?.progressPercentFlow?.value = progFlow

        addAndFillActor(imgPanel)
        addActors(lblBalance, progress!!)

        lblBalance.setBoundsScaled(sizeScaler, 112f, 45f, 83f, 34f)
        progress?.setBoundsScaled(sizeScaler, 503f, 39f, 373f, 38f)

        coroutine?.launch {
            launch {
                gdxGame.ds_Balance.flow.collect { balance ->
                    runGDX { lblBalance.setText(balance) }
                }
            }
        }
    }

}