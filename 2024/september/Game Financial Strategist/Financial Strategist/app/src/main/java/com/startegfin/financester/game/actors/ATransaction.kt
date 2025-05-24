package com.startegfin.financester.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Styles.LabelStyle
import com.startegfin.financester.game.data.Transaction
import com.startegfin.financester.game.data.TransactionType
import com.startegfin.financester.game.global.toBalance
import com.startegfin.financester.game.utils.GColor
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen

class ATransaction(
    override val screen: AdvancedScreen,
    val transaction: Transaction,
    val transactionType: TransactionType,
    lsSemiBold32: Label.LabelStyle,
    lsSemiBold24: Label.LabelStyle,
) : AdvancedGroup() {

    private val icons      = listOf(screen.game.all.listRoshodIcon, screen.game.all.listDohodIcon)[transactionType.ordinal]
    private val categories = listOf(screen.game.listCategoryRoshod, screen.game.listCategoryDohod)[transactionType.ordinal]
    private val symbol     = listOf("-", "+")[transactionType.ordinal]
    private val colorSuma  = listOf(GColor.red, GColor.green)[transactionType.ordinal]

    private val imgIcon  = Image(icons[transaction.categoryIndex])
    private val lblTitle = Label(categories[transaction.categoryIndex], lsSemiBold32)
    private val lblSuma  = Label("$symbol${transaction.suma.toBalance} ₽", lsSemiBold32)
    private val lblDate  = Label(transaction.date, lsSemiBold24)

    override fun addActorsOnGroup() {
        addActors(imgIcon, lblTitle, lblDate, lblSuma)
        imgIcon.setBounds(20f,20f,40f,40f)
        lblTitle.setBounds(100f,38f,132f,39f)
        lblDate.setBounds(100f,3f,128f,29f)
        lblSuma.setBounds(465f,20f,127f,39f)

        lblSuma.apply {
            setAlignment(Align.right)
            style = Label.LabelStyle(style.font, colorSuma)
        }
    }

}