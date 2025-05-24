package com.baleru.gamanecpidec.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.baleru.gamanecpidec.game.data.DataItem
import com.baleru.gamanecpidec.game.utils.GLOBAL_listCategoryName_Expense
import com.baleru.gamanecpidec.game.utils.GLOBAL_listCategoryName_Income
import com.baleru.gamanecpidec.game.utils.GameColor
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedGroup
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedScreen
import com.baleru.gamanecpidec.game.utils.gdxGame
import com.baleru.gamanecpidec.game.utils.toSeparateWithSymbol

class AItem(
    override val screen: AdvancedScreen,
    val dataItem: DataItem,
    val ls31: Label.LabelStyle,
): AdvancedGroup() {

    private val lsSumma: Label.LabelStyle = if (dataItem.isIncome) Label.LabelStyle(ls31.font, GameColor.blue_06) else Label.LabelStyle(ls31.font, GameColor.blue_06)
    private val summaSymbol = if (dataItem.isIncome) "+" else "-"

    private val listIcon = if (dataItem.isIncome) gdxGame.assetsAll.listINCOME else gdxGame.assetsAll.listEXPENSE
    private val listName = if (dataItem.isIncome) GLOBAL_listCategoryName_Income else GLOBAL_listCategoryName_Expense

    private val imgCategory = Image(listIcon[dataItem.categoryIndex])
    private val lblCategory = Label(listName[dataItem.categoryIndex], ls31)
    private val lblSumma    = Label(summaSymbol + (dataItem.summa.toSeparateWithSymbol(' ')) + " ₺", lsSumma)

    override fun addActorsOnGroup() {
        addActors(imgCategory, lblCategory, lblSumma)
        imgCategory.setBounds(0f, 22f, 100f, 100f)
        lblCategory.setBounds(153f, 54f, 160f, 35f)
        lblSumma.setBounds(550f, 54f, 149f, 36f)

        lblSumma.setAlignment(Align.right)

        val separ = Image(screen.drawerUtil.getTexture(GameColor.blue_06))
        addActor(separ)
        separ.setSize(width, 3f)
    }

}