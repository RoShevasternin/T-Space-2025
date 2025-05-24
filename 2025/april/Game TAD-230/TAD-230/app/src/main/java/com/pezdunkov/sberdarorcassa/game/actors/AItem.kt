package com.pezdunkov.sberdarorcassa.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pezdunkov.sberdarorcassa.game.data.DataItem
import com.pezdunkov.sberdarorcassa.game.screens.AddTovarScreen
import com.pezdunkov.sberdarorcassa.game.utils.GameColor
import com.pezdunkov.sberdarorcassa.game.utils.actor.setOnTouchListener
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedGroup
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedScreen
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame
import com.pezdunkov.sberdarorcassa.game.utils.toSeparateWithSymbol

class AItem(
    override val screen: AdvancedScreen,
    val dataItem: DataItem,
    val ls37: Label.LabelStyle,
    val ls30: Label.LabelStyle,
    val ls22: Label.LabelStyle,
): AdvancedGroup() {

    private val valueKilkist = if (dataItem.isProdano) "${dataItem.kilkistProdano}/${dataItem.kilkist}" else dataItem.kilkist.toString()
    private val valueSumma   = if (dataItem.isProdano) dataItem.kilkistProdano * dataItem.priceProdaja1 else dataItem.kilkist * dataItem.priceZakupu1

    private val lblName    = Label(dataItem.nName, ls37)
    private val lblKilkist = Label("$valueKilkist шт", ls30)
    private val lblSumma   = Label("$valueSumma ₽", ls30)
    private val lblDate    = Label(dataItem.date, ls22)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(if (dataItem.isProdano) gdxGame.assetsAll.ITEM_PRODANO else gdxGame.assetsAll.ITEM))
        addActors(lblName, lblKilkist, lblSumma, lblDate)
        lblName.setBounds(28f, 142f, 123f, 46f)
        lblKilkist.setBounds(441f, 76f, 98f, 37f)
        lblSumma.setBounds(384f, 28f, 155f, 37f)
        lblDate.setBounds(235f, 228f, 123f, 23f)

        lblKilkist.setAlignment(Align.right)
        lblSumma.setAlignment(Align.right)
        lblDate.setAlignment(Align.center)


        val aEdit   = Actor()
        val aDelete = Actor()

        addActors(aEdit, aDelete)
        aEdit.apply {
            setBounds(0f, 226f, 149f, 28f)
            setOnTouchListener {
                gdxGame.ds_ItemData.update {
                    val ml = it.toMutableList()
                    ml.remove(dataItem)
                    ml
                }

                gdxGame.navigationManager.navigate(AddTovarScreen::class.java.name, screen::class.java.name)
            }
        }
        aDelete.apply {
            setBounds(445f, 228f, 122f, 25f)
            setOnTouchListener {
                gdxGame.ds_ItemData.update {
                    val ml = it.toMutableList()
                    ml.remove(dataItem)
                    ml
                }

                gdxGame.navigationManager.navigate(screen::class.java.name)
            }
        }
    }

}