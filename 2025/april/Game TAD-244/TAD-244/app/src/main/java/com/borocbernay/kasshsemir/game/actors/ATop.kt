package com.borocbernay.kasshsemir.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.borocbernay.kasshsemir.game.utils.*
import com.borocbernay.kasshsemir.game.utils.actor.setBoundsScaled
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedGroup
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedScreen
import com.borocbernay.kasshsemir.game.utils.font.FontParameter

class ATop(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listDataItem = gdxGame.ds_ItemData.flow.value.filter { it.isProdano }.sumOf { it.kilkistProdano * it.priceProdaja1 }

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font83        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(63))

    private val ls83 = Label.LabelStyle(font83, Color.WHITE)

    private val lblBalance = Label("${listDataItem.toSeparateWithSymbol(' ')} ₽", ls83)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.topor))

        addActor(lblBalance)
        lblBalance.setBoundsScaled(sizeScaler, 251f, 69f, 360f, 60f)
        lblBalance.setAlignment(Align.center)
    }

//    fun updateBalance(all: Int, income: Int, expense: Int) {
//        lblBalance.setText(expense.toSeparateWithSymbol(' ') + " ₺")
//    }

}