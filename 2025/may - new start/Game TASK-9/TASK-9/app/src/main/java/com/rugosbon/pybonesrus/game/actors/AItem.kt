package com.rugosbon.pybonesrus.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.rugosbon.pybonesrus.game.data.DataItem
import com.rugosbon.pybonesrus.game.utils.GLOBAL_listCategoryName
import com.rugosbon.pybonesrus.game.utils.GameColor
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedGroup
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedScreen
import com.rugosbon.pybonesrus.game.utils.gdxGame

class AItem(
    override val screen: AdvancedScreen,
    val dataItem: DataItem,
    val ls41: Label.LabelStyle,
    val ls33: Label.LabelStyle,
): AdvancedGroup() {

    private val lsSumma: Label.LabelStyle = if (dataItem.isDohod) Label.LabelStyle(ls41.font, GameColor.green) else Label.LabelStyle(ls41.font, GameColor.red)
    private val summaSymbol = if (dataItem.isDohod) "+" else "-"

    private val imgCategory = Image(gdxGame.assetsAll.listIcon[dataItem.categoryIndex])
    private val lblCategory = Label(GLOBAL_listCategoryName[dataItem.categoryIndex], ls41)
    private val lblComent   = Label(dataItem.coment, ls33)
    private val lblDate     = Label(dataItem.date, ls33)
    private val lblSumma    = Label(summaSymbol + dataItem.summa, lsSumma)

    override fun addActorsOnGroup() {
        addActors(imgCategory, lblCategory, lblComent, lblDate, lblSumma)
        imgCategory.setBounds(44f, 37f, 149f, 149f)
        lblCategory.setBounds(216f, 115f, 185f, 57f)
        lblComent.setBounds(216f, 52f, 89f, 46f)
        lblDate.setBounds(738f, 52f, 99f, 46f)
        lblSumma.setBounds(701f, 115f, 136f, 57f)

        lblDate.setAlignment(Align.right)
        lblSumma.setAlignment(Align.right)
    }

}