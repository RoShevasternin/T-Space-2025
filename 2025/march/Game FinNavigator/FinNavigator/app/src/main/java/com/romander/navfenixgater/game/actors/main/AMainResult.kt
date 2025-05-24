package com.romander.navfenixgater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.romander.navfenixgater.game.actors.ABottom
import com.romander.navfenixgater.game.actors.button.AButton
import com.romander.navfenixgater.game.dataStore.CalculationData
import com.romander.navfenixgater.game.dataStore.DataCredit
import com.romander.navfenixgater.game.dataStore.DataDeposit
import com.romander.navfenixgater.game.dataStore.DataIpoteka
import com.romander.navfenixgater.game.dataStore.DataLizing
import com.romander.navfenixgater.game.screens.HistoryScreen
import com.romander.navfenixgater.game.screens.MenuScreen
import com.romander.navfenixgater.game.screens.ResultScreen
import com.romander.navfenixgater.game.utils.Block
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.TIME_ANIM_SCREEN
import com.romander.navfenixgater.game.utils.actor.animDelay
import com.romander.navfenixgater.game.utils.actor.animHide
import com.romander.navfenixgater.game.utils.actor.animShow
import com.romander.navfenixgater.game.utils.actor.setBounds
import com.romander.navfenixgater.game.utils.advanced.AdvancedMainGroup
import com.romander.navfenixgater.game.utils.font.FontParameter
import com.romander.navfenixgater.game.utils.gdxGame

class AMainResult(
    override val screen: ResultScreen,
    val aBottom: ABottom,
): AdvancedMainGroup() {

    companion object {
        var SHOWED_ITEM: CalculationData? = null
    }

    private var valueTitle = ""
    private var fieldList  = listOf<String>()
    private var valueList  = listOf<String>()

    private val getMethod = fillValues()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))
    private val font30        = screen.fontGenerator_Reg.generateFont(fontParameter.setSize(33))
    private val font36        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(36))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_39)
    private val ls30 = Label.LabelStyle(font30, GameColor.black_39)

    private val ls51_Yellow = Label.LabelStyle(font51, GameColor.yellow_FE)
    private val ls36_Yellow = Label.LabelStyle(font36, GameColor.yellow_FE)

    private val lblTitle     = Label("Калькулятор", ls51)
    private val lblItemTitle = Label(valueTitle, ls51_Yellow)
    private val btnReset     = AButton(screen, AButton.Type.Reset)
    private val listLblField = List(fieldList.size) { Label(fieldList[it], ls30) }
    private val listLblValue = List(valueList.size) { Label(valueList[it], ls36_Yellow) }
    private val btnSave      = AButton(screen, AButton.Type.Save)
    private val btnRemove    = AButton(screen, AButton.Type.Remove)


    override fun addActorsOnGroup() {
        color.a = 0f

        addLblTitle()
        addBtnReset()
        addValues()
        addBtns()

        animShowMain()

        handlerABottom()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnReset() {
        addActor(btnReset)
        btnReset.setBounds(591f, 1192f, 91f, 91f)
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(240f, 1200f, 212f, 76f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addValues() {
        addActor(lblItemTitle)
        lblItemTitle.setBounds(41f, 928f, 220f, 67f)

        var fNY = 844f
        listLblField.forEach {
            addActor(it)
            it.setBounds(41f, fNY, 232f, 43f)
            fNY -= 37 + 43
        }

        var vNY = 844f
        listLblValue.forEach {
            addActor(it)
            it.setBounds(502f, vNY, 147f, 48f)
            it.setAlignment(Align.right)
            vNY -= 37 + 48
        }
    }

    private fun addBtns() {
        addActors(btnSave, btnRemove)
        btnSave.setBounds(41f, 330f, 250f, 75f)
        btnSave.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }

        btnRemove.setBounds(401f, 330f, 250f, 75f)
        btnRemove.setOnClickListener {
            removeItem()

            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
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

    // Logic ------------------------------------------------

    private fun handlerABottom() {
        aBottom.blockGlav = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name, screen::class.java.name)
            }
        }
        aBottom.blockCalc = {}
        aBottom.blockHist = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun fillValues() {
        when(SHOWED_ITEM) {
            is DataLizing -> {
                val data = (SHOWED_ITEM as DataLizing)
                valueTitle = data.nName
                fieldList = listOf(
                    "Сумма выплат",
                    "Стоимость объекта",
                    "Итого",
                    "Комисии",
                )
                valueList = listOf(
                    "${data.monthlyPayment} ₽",
                    "${data.objectCostFinal} ₽",
                    "${data.totalPayments} ₽",
                    "${data.commission} ₽",
                )
            }

            is DataCredit -> {
                val data = (SHOWED_ITEM as DataCredit)
                valueTitle = data.nName
                fieldList = listOf(
                    "Сумма выплат",
                    "Тело кредита",
                    "В том числе %",
                    "Комисии",
                )
                valueList = listOf(
                    "${data.totalPayment} ₽",
                    "${data.creditBody} ₽",
                    "${data.interestAmount} ₽",
                    "${data.commission} ₽",
                )
            }

            is DataIpoteka -> {
                val data = (SHOWED_ITEM as DataIpoteka)
                valueTitle = data.nName
                fieldList = listOf(
                    "Ежемесячный платеж",
                    "Процентные расходы",
                    "Переплата по ипотеке",
                    "Эфективная ставка",
                )
                valueList = listOf(
                    "${data.totalPayment} ₽",
                    "${data.loanBody} ₽",
                    "${data.interestPaid} ₽",
                    "${data.commissions} %",
                )
            }

            is DataDeposit -> {
                val data = (SHOWED_ITEM as DataDeposit)
                valueTitle = data.nName
                fieldList = listOf(
                    "Тело депозита",
                    "Проценты",
                )
                valueList = listOf(
                    "${data.totalInterest} ₽",
                    "${data.totalBody} ₽",
                )
            }
        }
    }

    private fun removeItem() {
        when(SHOWED_ITEM) {
            is DataLizing -> {
                val data = (SHOWED_ITEM as DataLizing)

                gdxGame.ds_LizingData.update {
                    val mData = it.toMutableList()
                    mData.remove(data)
                    mData
                }
            }
            is DataCredit -> {
                val data = (SHOWED_ITEM as DataCredit)

                gdxGame.ds_CreditData.update {
                    val mData = it.toMutableList()
                    mData.remove(data)
                    mData
                }
            }
            is DataIpoteka -> {
                val data = (SHOWED_ITEM as DataIpoteka)

                gdxGame.ds_IpotekaData.update {
                    val mData = it.toMutableList()
                    mData.remove(data)
                    mData
                }
            }
            is DataDeposit -> {
                val data = (SHOWED_ITEM as DataDeposit)

                gdxGame.ds_DepositData.update {
                    val mData = it.toMutableList()
                    mData.remove(data)
                    mData
                }
            }
        }
    }

}