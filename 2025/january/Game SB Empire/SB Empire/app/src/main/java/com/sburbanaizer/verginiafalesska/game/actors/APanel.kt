package com.sburbanaizer.verginiafalesska.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.sburbanaizer.verginiafalesska.game.utils.GameColor
import com.sburbanaizer.verginiafalesska.game.utils.SizeScaler
import com.sburbanaizer.verginiafalesska.game.utils.actor.setBoundsScaled
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedGroup
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedScreen
import com.sburbanaizer.verginiafalesska.game.utils.font.FontParameter
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame
import com.sburbanaizer.verginiafalesska.game.utils.runGDX
import com.sburbanaizer.verginiafalesska.game.utils.toSeparate
import kotlinx.coroutines.launch

class APanel(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 713f)
    
    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font38        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(38))

    private val ls38 = Label.LabelStyle(font38, GameColor.black_44)

    private val lblBalance = Label("", ls38)
    private val lblMaslo   = Label("", ls38)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.top))
        addLbls()
    }

    // Actors ----------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblBalance, lblMaslo)
        lblBalance.setBoundsScaled(sizeScaler, 86f, 35f, 63f, 26f)
        lblMaslo.setBoundsScaled(sizeScaler, 572f, 35f, 95f, 26f)

        coroutine?.launch {
            launch {
                gdxGame.ds_Balance.flow.collect { balance ->
                    runGDX {
                        lblBalance.setText(balance.toSeparate())
                    }
                }
            }
            launch {
                gdxGame.ds_Maslo.flow.collect { maslo ->
                    runGDX {
                        lblMaslo.setText(maslo.toSeparate())
                    }
                }
            }
        }
    }

}