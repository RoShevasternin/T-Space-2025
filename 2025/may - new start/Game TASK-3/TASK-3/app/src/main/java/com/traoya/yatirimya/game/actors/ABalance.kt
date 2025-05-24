package com.traoya.yatirimya.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.traoya.yatirimya.game.utils.advanced.AdvancedGroup
import com.traoya.yatirimya.game.utils.advanced.AdvancedScreen
import com.traoya.yatirimya.game.utils.font.FontParameter
import com.traoya.yatirimya.game.utils.gdxGame
import com.traoya.yatirimya.game.utils.runGDX
import kotlinx.coroutines.launch

class ABalance(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font66        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(66))

    private val ls66 = Label.LabelStyle(font66, Color.WHITE)

    private val lblNeft = Label("0", ls66)
    private val lblGold = Label("0", ls66)

    private val imgBal = Image(gdxGame.assetsAll.toper)

    override fun addActorsOnGroup() {
        addAndFillActor(imgBal)

        addActors(lblNeft, lblGold)
        lblNeft.setBounds(265f, 29f, 129f, 67f)
        lblGold.setBounds(728f, 29f, 129f, 67f)

        coroutine?.launch {
            launch {
                gdxGame.ds_Gold.flow.collect {
                    runGDX { lblGold.setText(it) }
                }
            }
            launch {
                gdxGame.ds_Gel.flow.collect {
                    runGDX { lblNeft.setText(it) }
                }
            }
        }
    }

}