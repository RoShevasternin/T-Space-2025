package com.pezdunkov.sberdarorcassa.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pezdunkov.sberdarorcassa.game.actors.AMenu
import com.pezdunkov.sberdarorcassa.game.screens.S1Screen
import com.pezdunkov.sberdarorcassa.game.screens.S2Screen
import com.pezdunkov.sberdarorcassa.game.screens.S3Screen
import com.pezdunkov.sberdarorcassa.game.screens.S4Screen
import com.pezdunkov.sberdarorcassa.game.utils.Block
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedMainGroup
import com.pezdunkov.sberdarorcassa.game.utils.font.FontParameter
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame
import com.pezdunkov.sberdarorcassa.game.utils.toSeparateWithSymbol
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class AMainS4(override val screen: S4Screen): AdvancedMainGroup() {

    private val valueSum = gdxGame.ds_ItemData.flow.value.filter { it.isProdano }.sumOf { it.kilkistProdano * it.priceProdaja1 }
    private val valueProdano = gdxGame.ds_ItemData.flow.value.filter { it.isProdano }.sumOf { it.kilkistProdano }
    private val valueSklade  = gdxGame.ds_ItemData.flow.value.filter { it.isProdano.not() }.sumOf { it.kilkist }

    private val sumInK = (valueSum / 1000f).roundToInt()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font37        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(37))
    private val font24        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(24))

    private val ls37 = Label.LabelStyle(font37, Color.WHITE)
    private val ls24 = Label.LabelStyle(font24, Color.WHITE)

    private val aMenu = AMenu(screen)

    private val imgAnal = Image(gdxGame.assetsAll.SALES)
    private val lblSum  = Label(sumInK.toString() + "k", ls24)
    private val lblMes  = Label(getShortRussianMonth(), ls24)

    private val lblProdano = Label(valueProdano.toSeparateWithSymbol(' ') + " шт", ls37)
    private val lblSklade  = Label(valueSklade.toSeparateWithSymbol(' ') + " шт", ls37)

    override fun addActorsOnGroup() {
        addAMenu()
        addAnalitika()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(37f, 892f, 636f, 189f)

        aMenu.boxS4.check()

        aMenu.blockS1 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(S1Screen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockS2 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(S2Screen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockS3 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(S3Screen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockS4 = { }
    }

    private fun addAnalitika() {
        addActor(imgAnal)
        imgAnal.setBounds(37f, 87f, 636f, 768f)

        addActors(lblSum, lblMes)
        lblSum.setBounds(115f, 346f, 41f, 35f)
        lblMes.setBounds(114f, 111f, 44f, 35f)

        lblMes.setAlignment(Align.center)
        lblSum.setAlignment(Align.center)

        addActors(lblProdano, lblSklade)
        lblProdano.setBounds(75f, 727f, 189f, 54f)
        lblSklade.setBounds(75f, 536f, 188f, 54f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        blockEnd.invoke()
    }

    override fun animHideMain(blockEnd: Block) {
        blockEnd.invoke()
    }


    private fun getShortRussianMonth(): String {
        val dateFormat = SimpleDateFormat("MMM", Locale("ru"))
        return dateFormat.format(Date()).replaceFirstChar { it.uppercaseChar() }
    }


}