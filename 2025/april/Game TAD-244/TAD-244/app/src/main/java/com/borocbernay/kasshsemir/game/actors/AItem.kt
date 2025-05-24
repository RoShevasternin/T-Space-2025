package com.borocbernay.kasshsemir.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.borocbernay.kasshsemir.game.data.DataItem
import com.borocbernay.kasshsemir.game.screens.AddTovarScreen
import com.borocbernay.kasshsemir.game.utils.GameColor
import com.borocbernay.kasshsemir.game.utils.actor.setOnTouchListener
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedGroup
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedScreen
import com.borocbernay.kasshsemir.game.utils.gdxGame
import com.borocbernay.kasshsemir.game.utils.toSeparateWithSymbol

class AItem(
    override val screen: AdvancedScreen,
    val dataItem: DataItem,
    val ls46: Label.LabelStyle,
    val ls36: Label.LabelStyle,
    val ls27: Label.LabelStyle,
): AdvancedGroup() {

    private val valueKilkist = if (dataItem.isProdano) "${dataItem.kilkistProdano}/${dataItem.kilkist}" else dataItem.kilkist.toString()
    private val valueSumma   = if (dataItem.isProdano) dataItem.kilkistProdano * dataItem.priceProdaja1 else dataItem.kilkist * dataItem.priceZakupu1

    private val lblName    = Label(dataItem.nName, ls46)
    private val lblKilkist = Label("$valueKilkist шт", ls36)
    private val lblSumma   = Label("$valueSumma ₽", ls36)
    private val lblDate    = Label(dataItem.date, ls27)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(if (dataItem.isProdano) gdxGame.assetsAll.item_prodaja else gdxGame.assetsAll.item_tovar))
        addActors(lblName, lblKilkist, lblSumma, lblDate)
        lblName.setBounds(34f, 239f, 124f, 55f)
        lblKilkist.setBounds(615f, 161f, 145f, 44f)
        lblSumma.setBounds(565f, 102f, 196f, 45f)
        lblDate.setBounds(322f, 306f, 149f, 28f)

        lblKilkist.setAlignment(Align.right)
        lblSumma.setAlignment(Align.right)
        lblDate.setAlignment(Align.center)


        val aEdit   = Actor()
        val aDelete = Actor()

        addActors(aEdit, aDelete)
        aEdit.apply {
            setBounds(19f, 16f, 213f, 72f)
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
            setBounds(565f, 16f, 213f, 72f)
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