package com.pezdunkov.sberdarorcassa.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pezdunkov.sberdarorcassa.game.screens.AddProdajaScreen
import com.pezdunkov.sberdarorcassa.game.screens.AddTovarScreen
import com.pezdunkov.sberdarorcassa.game.screens.S1Screen
import com.pezdunkov.sberdarorcassa.game.screens.S2Screen
import com.pezdunkov.sberdarorcassa.game.screens.S3Screen
import com.pezdunkov.sberdarorcassa.game.screens.S4Screen
import com.pezdunkov.sberdarorcassa.game.utils.*
import com.pezdunkov.sberdarorcassa.game.utils.actor.setBoundsScaled
import com.pezdunkov.sberdarorcassa.game.utils.actor.setOnClickListener
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedGroup
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedScreen
import com.pezdunkov.sberdarorcassa.game.utils.font.FontParameter

class ATop(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listDataItem = gdxGame.ds_ItemData.flow.value.filter { it.isProdano }.sumOf { it.kilkistProdano * it.priceProdaja1 }

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font68        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(68))

    private val ls68 = Label.LabelStyle(font68, Color.WHITE)

    private val lblBalance = Label("${listDataItem.toSeparateWithSymbol(' ')} ₽", ls68)

    private val aAdd = Actor()

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.TOP))

        addActor(lblBalance)
        lblBalance.setBoundsScaled(sizeScaler, 206f, 53f, 296f, 50f)
        lblBalance.setAlignment(Align.center)

        addActor(aAdd)
        aAdd.setBoundsScaled(sizeScaler, 464f, 232f, 208f, 75f)

        val toScreen = when (screen) {
            is S1Screen -> AddTovarScreen::class.java.name
            is S2Screen -> AddProdajaScreen::class.java.name
            is S3Screen -> AddTovarScreen::class.java.name
            is S4Screen -> AddProdajaScreen::class.java.name

            is AddProdajaScreen -> AddProdajaScreen::class.java.name
            else                -> AddTovarScreen::class.java.name
        }

        aAdd.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(toScreen, screen::class.java.name)
            }
        }
    }

    fun updateBalance(all: Int, income: Int, expense: Int) {
        lblBalance.setText(expense.toSeparateWithSymbol(' ') + " ₺")
    }

}