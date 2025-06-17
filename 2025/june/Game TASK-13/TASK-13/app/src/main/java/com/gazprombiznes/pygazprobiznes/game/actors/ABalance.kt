package com.gazprombiznes.pygazprobiznes.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.gazprombiznes.pygazprobiznes.game.utils.GameColor
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedGroup
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedScreen
import com.gazprombiznes.pygazprobiznes.game.utils.font.FontParameter
import com.gazprombiznes.pygazprobiznes.game.utils.gdxGame
import com.gazprombiznes.pygazprobiznes.game.utils.runGDX
import kotlinx.coroutines.launch

class ABalance(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "Уровень")
    private val font35        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(35))

    private val ls66 = Label.LabelStyle(font35, GameColor.blue)

    private val lblGaz   = Label("0", ls66)
    private val lblLevel = Label("1 Уровень", ls66)

    private val imgBalance = Image(gdxGame.assetsAll.balance)

    override fun addActorsOnGroup() {
        addAndFillActor(imgBalance)

        addActors(lblGaz, lblLevel)
        lblGaz.setBounds(90f, 16f, 87f, 39f)
        lblLevel.setBounds(321f, 16f, 164f, 39f)

        coroutine?.launch {
            launch {
                gdxGame.ds_Gaz.flow.collect {
                    runGDX { lblGaz.setText(it) }
                }
            }
            launch {
                gdxGame.ds_Level.flow.collect {
                    runGDX { lblLevel.setText("$it Уровень") }
                }
            }
        }
    }

}