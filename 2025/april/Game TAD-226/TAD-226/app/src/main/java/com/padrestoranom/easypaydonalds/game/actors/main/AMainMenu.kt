package com.padrestoranom.easypaydonalds.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.padrestoranom.easypaydonalds.game.actors.AItem
import com.padrestoranom.easypaydonalds.game.actors.AMenu
import com.padrestoranom.easypaydonalds.game.actors.ATop
import com.padrestoranom.easypaydonalds.game.screens.MenuScreen
import com.padrestoranom.easypaydonalds.game.screens.AddScreen
import com.padrestoranom.easypaydonalds.game.screens.HistoryScreen
import com.padrestoranom.easypaydonalds.game.utils.*
import com.padrestoranom.easypaydonalds.game.utils.actor.animDelay
import com.padrestoranom.easypaydonalds.game.utils.actor.animHide
import com.padrestoranom.easypaydonalds.game.utils.actor.animShow
import com.padrestoranom.easypaydonalds.game.utils.actor.setBounds
import com.padrestoranom.easypaydonalds.game.utils.actor.setOnClickListener
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedMainGroup
import com.padrestoranom.easypaydonalds.game.utils.font.FontParameter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AMainMenu(
    override val screen: MenuScreen,
    val aMenu: AMenu,
    val aTop: ATop,
): AdvancedMainGroup() {

    private val valueIncome  = gdxGame.ds_ItemData.flow.value.filter { it.isIncome }.sumOf { it.summa }
    private val valueExpense = gdxGame.ds_ItemData.flow.value.filter { it.isIncome.not() }.sumOf { it.summa }
    private val valueBalance = valueIncome - valueExpense

    private val lastDataItem = gdxGame.ds_ItemData.flow.value.lastOrNull()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font35        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(35))
    private val font31        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(31))
    private val font80        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(80))
    private val font44        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(44))
    private val font27        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(27))

    private val ls35 = Label.LabelStyle(font35, GameColor.black_1B)
    private val ls31 = Label.LabelStyle(font31, GameColor.black_1B)
    private val ls80 = Label.LabelStyle(font80, GameColor.blue_02)
    private val ls44 = Label.LabelStyle(font44, GameColor.black_1B_50)
    private val ls27 = Label.LabelStyle(font27, GameColor.black_1B_50)

    private val lblTitle1 = Label("Denge", ls35)
    private val lblTitle2 = Label("Harcama analitiği", ls35)

    private val lblBalance = Label(valueIncome.toSeparateWithSymbol(' ') + " ₺", ls80)
    private val lblDay     = Label("", ls44)
    private val lblMonth   = Label("", ls27)

    private val imgStovp = Image(gdxGame.assetsAll.stovp)
    private val imgTarih = Image(gdxGame.assetsAll.TARIH)

    override fun addActorsOnGroup() {
        aTop.updateBalance(valueIncome, valueBalance, valueExpense)

        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addLblTitles()
        addLblBalance()
        addImgStovp()
        addLblDate()
        addTarih()

        animShowMain()

        handlerAMenu()

    }

    // Actors ------------------------------------------------------------------------

    private fun addLblTitles() {
        addActors(lblTitle1, lblTitle2)
        lblTitle1.setBounds(35f, 1244f, 101f, 25f)
        lblTitle2.setBounds(35f, 1152f, 101f, 25f)
    }

    private fun addLblBalance() {
        addActor(lblBalance)
        lblBalance.setBounds(229f, 1229f, 373f, 57f)
        lblBalance.setAlignment(Align.center)
    }

    private fun addImgStovp() {
        addActor(imgStovp)
        imgStovp.setBounds(347f, 467f, 154f, 523f)
        imgStovp.color.a = 0f
    }

    private fun addLblDate() {
        addActors(lblDay, lblMonth)
        lblDay.setBounds(398f, 1066f, 51f, 53f)
        lblDay.setAlignment(Align.center)

        lblMonth.setBounds(395f, 1022f, 53f, 33f)
        lblMonth.setAlignment(Align.center)

        lblDay.setText(getDayOfMonth())
        lblMonth.setText(getTurkishMonthName())
    }

    private fun addTarih() {
        addActor(imgTarih)
        imgTarih.setBounds(33f, 0f, 767f, 436f)

        imgTarih.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }


        if (lastDataItem != null) {
            val aItem = AItem(screen, lastDataItem, ls31)
            addActor(aItem)
            aItem.setBounds(66f, 173f, 700f, 144f)

            imgStovp.color.a = 1f
        }
    }


    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ------------------------------------------------------------------------

    private fun handlerAMenu() {
        aMenu.blockHome = {}
        aMenu.blockPlus = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AddScreen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockHistory = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun getDayOfMonth(): String {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    private fun getTurkishMonthName(): String {
        val date = Date()
        val turkishLocale = Locale("tr")
        val monthFormat = SimpleDateFormat("MMMM", turkishLocale)
        return monthFormat.format(date)
    }


}