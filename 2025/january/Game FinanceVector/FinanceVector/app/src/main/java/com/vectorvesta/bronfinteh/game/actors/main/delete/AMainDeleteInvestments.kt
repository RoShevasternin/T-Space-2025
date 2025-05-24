package com.vectorvesta.bronfinteh.game.actors.main.delete

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.vectorvesta.bronfinteh.game.actors.AItemSimple
import com.vectorvesta.bronfinteh.game.actors.APanel
import com.vectorvesta.bronfinteh.game.actors.autoLayout.AVerticalGroup
import com.vectorvesta.bronfinteh.game.actors.button.AButton
import com.vectorvesta.bronfinteh.game.actors.resultItem.GLOBAL_listInvestments
import com.vectorvesta.bronfinteh.game.actors.resultItem.GLOBAL_listTitleLeasing
import com.vectorvesta.bronfinteh.game.dataStore.DataItems
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorInvestmentsScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorLeasingScreen
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteInvestmentsScreen
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteLeasingScreen
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.GameColor
import com.vectorvesta.bronfinteh.game.utils.TIME_ANIM_SCREEN
import com.vectorvesta.bronfinteh.game.utils.actor.animDelay
import com.vectorvesta.bronfinteh.game.utils.actor.animHide
import com.vectorvesta.bronfinteh.game.utils.actor.animShow
import com.vectorvesta.bronfinteh.game.utils.actor.setBounds
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainGroup
import com.vectorvesta.bronfinteh.game.utils.font.FontParameter
import com.vectorvesta.bronfinteh.game.utils.gdxGame

class AMainDeleteInvestments(
    override val screen: DeleteInvestmentsScreen,
): AdvancedMainGroup() {

    companion object {
        var dataItems: DataItems? = null
    }

    private val parameter43 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(43)
    private val parameter38 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(38)

    private val font43 = screen.fontGenerator_Medium.generateFont(parameter43)
    private val font38 = screen.fontGenerator_Medium.generateFont(parameter38)

    private val ls43   = LabelStyle(font43, GameColor.black_42)
    private val ls38_g = LabelStyle(font38, GameColor.gray_A2)
    private val ls38_b = LabelStyle(font38, GameColor.black_42)

    private val aPanel       = APanel(screen, gdxGame.assetsAll.hist)
    private val btnBack      = AButton(screen, AButton.Type.Back)
    private val lblTitle     = Label("Калькулятор Инвестиции", ls43)
    private val btnCalculate = AButton(screen, AButton.Type.Calculate)
    private val btnRemove    = AButton(screen, AButton.Type.Remove)

    private val verticalGroup  = AVerticalGroup(screen, 48f)
    private val listItemSimple = List(2) {
        AItemSimple(screen,
            GLOBAL_listInvestments[it],
            dataItems!!.listValues[it + 1],
            ls38_g,
            ls38_b,
        )
    }
    private val lblTitle2 = Label(dataItems!!.listValues.first(), ls43)


    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanel()
        addBtnBack()
        addLblTitle()
        addAScroll()
        addLblTitle2()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanel() {
        addActor(aPanel)
        aPanel.setBounds(216f, 1765f, 469f, 97f)
        aPanel.blockMain = { screen.hideScreen { gdxGame.navigationManager.back() } }
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(48f, 1616f, 101f, 101f)
        btnBack.setOnClickListener { screen.hideScreen { gdxGame.navigationManager.back() } }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(205f, 1636f, 489f, 61f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addAScroll() {
        addActor(verticalGroup)
        verticalGroup.setBounds(48f, 1066f, 805f, 377f)

        verticalGroup.apply {
            addItems()
        }
    }

    private fun addLblTitle2() {
        addActor(lblTitle2)
        lblTitle2.setBounds(345f, 1522f, 211f, 44f)
        lblTitle2.setAlignment(Align.center)
    }

    private fun addBtns() {
        addActor(btnCalculate)
        btnCalculate.setBounds(48f, 725f, 805f, 135f)

        btnCalculate.setOnClickListener {
            gdxGame.navigationManager.navigate(CalculatorInvestmentsScreen::class.java.name)
        }

        addActor(btnRemove)
        btnRemove.setBounds(48f, 895f, 805f, 135f)

        btnRemove.setOnClickListener {
            gdxGame.ds_DataItems.update { list ->
                val mList = list.toMutableList()
                mList.remove(dataItems)
                mList
            }
            gdxGame.navigationManager.back()
        }
    }

    // Actors VerticalGroup ------------------------------------------------------------------------

    private fun AVerticalGroup.addItems() {
        listItemSimple.onEach { item ->
            item.setSize(805f, 165f)
            this.addActor(item)
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}