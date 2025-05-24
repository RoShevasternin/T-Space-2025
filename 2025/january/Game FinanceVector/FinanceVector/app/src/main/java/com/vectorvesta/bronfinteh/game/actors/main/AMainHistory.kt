package com.vectorvesta.bronfinteh.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.vectorvesta.bronfinteh.game.actors.APanel
import com.vectorvesta.bronfinteh.game.actors.AScrollPane
import com.vectorvesta.bronfinteh.game.actors.ATmpGroup
import com.vectorvesta.bronfinteh.game.actors.autoLayout.AVerticalGroup
import com.vectorvesta.bronfinteh.game.actors.button.AButton
import com.vectorvesta.bronfinteh.game.actors.resultItem.AItemDeposit
import com.vectorvesta.bronfinteh.game.actors.resultItem.AItemInvestments
import com.vectorvesta.bronfinteh.game.actors.resultItem.AItemLeasing
import com.vectorvesta.bronfinteh.game.actors.resultItem.AItemLoan
import com.vectorvesta.bronfinteh.game.actors.resultItem.AItemMortgage
import com.vectorvesta.bronfinteh.game.dataStore.DataItemType
import com.vectorvesta.bronfinteh.game.screens.HistoryScreen
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.GameColor
import com.vectorvesta.bronfinteh.game.utils.TIME_ANIM_SCREEN
import com.vectorvesta.bronfinteh.game.utils.actor.animDelay
import com.vectorvesta.bronfinteh.game.utils.actor.animHide
import com.vectorvesta.bronfinteh.game.utils.actor.animShow
import com.vectorvesta.bronfinteh.game.utils.addSpace
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainGroup
import com.vectorvesta.bronfinteh.game.utils.font.FontParameter
import com.vectorvesta.bronfinteh.game.utils.gdxGame
import com.vectorvesta.bronfinteh.util.log

class AMainHistory(
    override val screen: HistoryScreen,
): AdvancedMainGroup() {

    private val listDataItems = gdxGame.ds_DataItems.flow.value.reversed()

    private val parameter43 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(43)
    private val parameter38 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(38)

    private val font43 = screen.fontGenerator_Medium.generateFont(parameter43)
    private val font38 = screen.fontGenerator_Medium.generateFont(parameter38)

    private val ls43   = LabelStyle(font43, GameColor.black_42)
    private val ls38_g = LabelStyle(font38, GameColor.gray_A2)
    private val ls38_b = LabelStyle(font38, GameColor.black_42)

    private val aPanel   = APanel(screen, gdxGame.assetsAll.hist)
    private val btnBack  = AButton(screen, AButton.Type.Back)

    private val verticalGroup  = AVerticalGroup(screen, 48f, isWrap = true)
    private val scroll         = AScrollPane(verticalGroup)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanel()
        addAScroll()
        addBtnBack()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanel() {
        addActor(aPanel)
        aPanel.setBounds(216f, 1765f, 469f, 97f)
        aPanel.blockMain = {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(48f, 1616f, 101f, 101f)
        btnBack.setOnClickListener { screen.hideScreen { gdxGame.navigationManager.back() } }
    }

    private fun addAScroll() {
        addActor(scroll)
        scroll.setBounds(48f, 0f, 805f, 1703f)
        verticalGroup.setSize(805f, 1703f)

        verticalGroup.addItems()

    }

    // Actors VerticalGroup ------------------------------------------------------------------------

    private fun AVerticalGroup.addItems() {
        listDataItems.onEach { data ->
            val aItem: AdvancedGroup = when(data.type) {
                DataItemType.Leasing -> {
                    AItemLeasing(data, screen, data.listValues, ls43, ls38_g, ls38_b).also { it.setSize(805f, 1166f) }
                }
                DataItemType.Loan -> {
                    AItemLoan(data, screen, data.listValues, ls43, ls38_g, ls38_b).also { it.setSize(805f, 953f) }
                }

                DataItemType.Deposit -> {
                    AItemDeposit(data, screen, data.listValues, ls43, ls38_g, ls38_b).also { it.setSize(805f, 526f) }
                }

                DataItemType.Investments -> {
                    AItemInvestments(data, screen, data.listValues, ls43, ls38_g, ls38_b).also { it.setSize(805f, 526f) }
                }

                DataItemType.Mortgage -> {
                    AItemMortgage(data, screen, data.listValues, ls43, ls38_g, ls38_b).also { it.setSize(805f, 952f) }
                }
            }

            this.addActor(aItem)
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