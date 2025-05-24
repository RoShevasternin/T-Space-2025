package com.busiknesik.pomeshnek.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.busiknesik.pomeshnek.game.utils.GameColor
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedGroup
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.font.FontParameter
import com.busiknesik.pomeshnek.game.utils.gdxGame
import com.busiknesik.pomeshnek.game.utils.toSeparate

class APanel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val count = gdxGame.ds_Tovar.flow.value.sumOf { it.count }
    private val marge = gdxGame.ds_Tovar.flow.value.sumOf { it.marge }
    private val summa = gdxGame.ds_Tovar.flow.value.sumOf { it.summa }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "₽%")
    private val font120       = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(120))

    private val ls120 = Label.LabelStyle(font120, GameColor.black_38)

    private val lblCol1  = Label(count.toString().take(5).toSeparate(), ls120)
    private val lblCol2  = Label("${marge.toString().take(5)}%", ls120)
    private val lblSumma = Label(summa.toSeparate() + " ₽", ls120)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.panel))
        addLbls()
    }

    // Actors ----------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblCol1, lblCol2, lblSumma)
        lblCol1.apply {
            setBounds(140f, 1072f, 213f, 86f)
            setAlignment(Align.center)
        }
        lblCol2.apply {
            setBounds(692f, 1072f, 220f, 86f)
            setAlignment(Align.center)
        }
        lblSumma.apply {
            setBounds(192f, 662f, 665f, 86f)
            setAlignment(Align.center)
        }
    }

}