package com.cburaktev.klavregasd.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.cburaktev.klavregasd.game.actors.AItem
import com.cburaktev.klavregasd.game.actors.AScrollPane
import com.cburaktev.klavregasd.game.actors.ATmpGroup
import com.cburaktev.klavregasd.game.actors.autoLayout.AVerticalGroup
import com.cburaktev.klavregasd.game.actors.button.AButton
import com.cburaktev.klavregasd.game.actors.checkbox.ACheckBox
import com.cburaktev.klavregasd.game.screens.NewItemScreen
import com.cburaktev.klavregasd.game.screens.StatisticScreen
import com.cburaktev.klavregasd.game.utils.Block
import com.cburaktev.klavregasd.game.utils.GameColor
import com.cburaktev.klavregasd.game.utils.TIME_ANIM_SCREEN
import com.cburaktev.klavregasd.game.utils.actor.*
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedMainGroup
import com.cburaktev.klavregasd.game.utils.font.FontParameter
import com.cburaktev.klavregasd.game.utils.gdxGame
import java.util.*

class AMainStatistic(
    override val screen: StatisticScreen,
): AdvancedMainGroup() {

    private val listDataItem_Income  = gdxGame.ds_ItemData.flow.value.filter { it.isDohod }.reversed()
    private val listDataItem_Expense = gdxGame.ds_ItemData.flow.value.filter { it.isDohod.not() }.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font31        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(31))
    private val font51        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(51))
    private val font41        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(41))
    private val font33        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(33))

    private val ls31 = Label.LabelStyle(font31, GameColor.black_21)
    private val ls51 = Label.LabelStyle(font51, GameColor.black_21)
    private val ls41 = Label.LabelStyle(font41, GameColor.black_21)
    private val ls33 = Label.LabelStyle(font33, GameColor.gray_92)

    private val imgLoga   = Image(gdxGame.assetsAll.loga)
    private val lblDate   = Label(getCurrentMonthAndYear(), ls31)
    private val imgDate   = Image(gdxGame.assetsAll.top)
    private val lblTitle  = Label("Расходы", ls51)
    private val box       = ACheckBox(screen, ACheckBox.Type.RosDoh)
    private val aX        = Actor()
    private val btnPlus   = AButton(screen, AButton.Type.Plus)

    // Group Income
    private val tmpGroupIncome        = ATmpGroup(screen)
    private val listAItemIncome       = List(listDataItem_Income.size) { AItem(screen, listDataItem_Income[it], ls41, ls33) }
    private val aVerticalGroupIncome  = AVerticalGroup(screen, 20f, isWrap = true)
    private val aScrollIncome         = AScrollPane(aVerticalGroupIncome)

    // Group Expense
    private val tmpGroupExpense        = ATmpGroup(screen)
    private val listAItemExpense       = List(listDataItem_Expense.size) { AItem(screen, listDataItem_Expense[it], ls41, ls33) }
    private val aVerticalGroupExpense  = AVerticalGroup(screen, 20f, isWrap = true)
    private val aScrollExpense         = AScrollPane(aVerticalGroupExpense)

    // field
    private var isDohod = false

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgLoga()
        addDate()
        addTitle()
        addBox()
        addBtnPlus()

        addExpense()
        addIncome()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoga() {
        addActor(imgLoga)
        imgLoga.setBounds(65f, 1911f, 354f, 129f)
    }

    private fun addDate() {
        addActor(imgDate)
        imgDate.setBounds(45f, 1728f, 879f, 119f)
        addActor(lblDate)
        lblDate.setBounds(394f, 1763f, 180f, 47f)

        addActor(aX)
        aX.apply {
            setBounds(826f, 1727f, 120f, 120f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

    private fun addTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(378f, 1384f, 222f, 78f)
    }

    private fun addBox() {
        addActor(box)
        box.setBounds(34f, 1554f, 901f, 167f)

        box.setOnCheckListener { isDohod ->
            this@AMainStatistic.isDohod = isDohod
            val text = if (isDohod) "Доходы" else "Расходы"
            lblTitle.setText(text)

            if (isDohod) showIncome() else hideIncome()
        }
    }

    private fun addBtnPlus() {
        addActor(btnPlus)
        btnPlus.setBounds(752f, 61f, 151f, 162f)

        btnPlus.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(NewItemScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    // Actors Income ------------------------------------------------------------------------

    private fun addExpense() {
        addActor(tmpGroupExpense)
        tmpGroupExpense.setBounds(45f, 0f, 880f, 1318f)
        tmpGroupExpense.zIndex = 0

        tmpGroupExpense.addAndFillActor(aScrollExpense)

        aVerticalGroupExpense.setSize(880f, 1318f)

        listAItemExpense.forEachIndexed { index, item ->
            item.setSize(879f, 221f)
            aVerticalGroupExpense.addActor(item)
        }

    }

    private fun addIncome() {
        tmpGroupIncome.color.a = 0f
        tmpGroupIncome.disable()

        addActor(tmpGroupIncome)
        tmpGroupIncome.setBounds(45f, 0f, 880f, 1318f)
        tmpGroupIncome.zIndex = 0

        tmpGroupIncome.addAndFillActor(aScrollIncome)

        aVerticalGroupIncome.setSize(880f, 1318f)

        listAItemIncome.forEachIndexed { index, item ->
            item.setSize(879f, 221f)
            aVerticalGroupIncome.addActor(item)
        }

    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic -----------------------------------------------

    private fun showIncome() {
        tmpGroupExpense.animHide(0.2f)
        tmpGroupExpense.disable()

        tmpGroupIncome.animShow(0.2f)
        tmpGroupIncome.enable()
    }

    private fun hideIncome() {
        tmpGroupIncome.animHide(0.2f)
        tmpGroupIncome.disable()

        tmpGroupExpense.animShow(0.2f)
        tmpGroupExpense.enable()
    }

    fun getCurrentMonthAndYear(locale: Locale = Locale("ru")): String {
        val calendar = java.util.Calendar.getInstance()
        val year     = calendar.get(java.util.Calendar.YEAR)

        val monthName = calendar.getDisplayName(
            java.util.Calendar.MONTH,
            java.util.Calendar.LONG,
            locale
        )?.replaceFirstChar { it.titlecase(locale)[0] } ?: ""

        return "$monthName $year"
    }

}