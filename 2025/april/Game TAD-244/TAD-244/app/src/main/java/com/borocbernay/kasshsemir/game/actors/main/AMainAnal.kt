package com.borocbernay.kasshsemir.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.borocbernay.kasshsemir.game.actors.AMenu
import com.borocbernay.kasshsemir.game.actors.button.AButton
import com.borocbernay.kasshsemir.game.screens.AddTovarScreen
import com.borocbernay.kasshsemir.game.screens.TovarsScreen
import com.borocbernay.kasshsemir.game.screens.HistoryScreen
import com.borocbernay.kasshsemir.game.screens.AnalScreen
import com.borocbernay.kasshsemir.game.utils.Block
import com.borocbernay.kasshsemir.game.utils.GameColor
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedMainGroup
import com.borocbernay.kasshsemir.game.utils.font.FontParameter
import com.borocbernay.kasshsemir.game.utils.gdxGame
import com.borocbernay.kasshsemir.game.utils.toSeparateWithSymbol
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class AMainAnal(
    override val screen: AnalScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val valueSum = gdxGame.ds_ItemData.flow.value.filter { it.isProdano }.sumOf { it.kilkistProdano * it.priceProdaja1 }
    private val valueProdano = gdxGame.ds_ItemData.flow.value.filter { it.isProdano }.sumOf { it.kilkistProdano }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font46        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(46))
    private val font24        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(24))
    private val font46bld        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(46))
    private val font55        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(55))

    private val ls46b = Label.LabelStyle(font46, GameColor.black_26)
    private val ls46g = Label.LabelStyle(font46, GameColor.green_22_40)
    private val ls55 = Label.LabelStyle(font55, GameColor.green_22_40)
    private val ls24 = Label.LabelStyle(font24, Color.WHITE)
    private val ls46Bld = Label.LabelStyle(font46bld, GameColor.black_26)

    private val lblTitle = Label("История продаж", ls46Bld)
    private val btnPlus  = AButton(screen, AButton.Type.Plus)

    private val imgAnal = Image(gdxGame.assetsAll.DASHBOARD)
    private val lblSum  = Label("${valueSum.toSeparateWithSymbol(' ')} ₽", ls55)

    private val lblProdano = Label(valueProdano.toSeparateWithSymbol(' ') + " шт", ls46b)
    private val lblSklade  = Label((valueSum / 2).toSeparateWithSymbol(' ') + "₽", ls46g)

    override fun addActorsOnGroup() {
        addLblTitleAndPlus()
        addAnalitika()

        handlerAMenu()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblTitleAndPlus() {
        addActor(lblTitle)
        lblTitle.setBounds(34f, 1415f, 310f, 56f)

        addActor(btnPlus)
        btnPlus.setBounds(738f, 1397f, 94f, 94f)

        btnPlus.setOnClickListener {
            gdxGame.navigationManager.navigate(AddTovarScreen::class.java.name, screen::class.java.name)
        }
    }

    private fun addAnalitika() {
        addActor(imgAnal)
        imgAnal.setBounds(0f, 211f, 865f, 1140f)

        addActors(lblSum)
        lblSum.setBounds(532f, 1222f, 225f, 74f)

        addActors(lblProdano, lblSklade)
        lblProdano.setBounds(80f, 435f, 230f, 65f)
        lblSklade.setBounds(80f, 235f, 230f, 65f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        blockEnd.invoke()
    }

    override fun animHideMain(blockEnd: Block) {
        blockEnd.invoke()
    }

    // Logic ------------------------------------------------------

    private fun handlerAMenu() {
        aMenu.boxS3.check()

        aMenu.blockS1 = {
            gdxGame.navigationManager.navigate(TovarsScreen::class.java.name, screen::class.java.name)
        }
        aMenu.blockS2 = {
            gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
        }
        aMenu.blockS3 = {}
    }


}