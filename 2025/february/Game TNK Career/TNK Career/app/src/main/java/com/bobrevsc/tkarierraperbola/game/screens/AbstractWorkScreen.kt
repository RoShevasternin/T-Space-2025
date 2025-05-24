package com.bobrevsc.tkarierraperbola.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.bobrevsc.tkarierraperbola.game.actors.AButton
import com.bobrevsc.tkarierraperbola.game.actors.ACoinPlus
import com.bobrevsc.tkarierraperbola.game.actors.TmpGroup
import com.bobrevsc.tkarierraperbola.game.actors.dialog.ADialogInvest
import com.bobrevsc.tkarierraperbola.game.actors.panel.AAbstractImprovementsPanel
import com.bobrevsc.tkarierraperbola.game.actors.panel.AAbstractInvestmentsPanel
import com.bobrevsc.tkarierraperbola.game.actors.progress.AProgressXP
import com.bobrevsc.tkarierraperbola.game.actors.systemUI.AHeader
import com.bobrevsc.tkarierraperbola.game.utils.*
import com.bobrevsc.tkarierraperbola.game.utils.actor.animHide
import com.bobrevsc.tkarierraperbola.game.utils.actor.animShow
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedGroup
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedScreen
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedStage
import com.bobrevsc.tkarierraperbola.game.utils.font.FontParameter
import com.bobrevsc.tkarierraperbola.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class AbstractWorkScreen(val screenType: ScreenType) : AdvancedScreen() {

    companion object {
        val LIST_RESERVE_INVEST = mutableListOf<ReserveInvest>()
    }

    data class ReserveInvest(val screenTypeIndex: Int, val investmentIndex: Int, val investment: Int)
    enum class ScreenType {
        _1, _2, _3, _4
    }

    abstract val currentContainerXP     : ContainerXP
    abstract val currentContainerBlockXP: ContainerBlockXP

    abstract val panelImprovements: AAbstractImprovementsPanel
    abstract val panelInvestments : AAbstractInvestmentsPanel

    private val fontParameter = FontParameter()
    private val font59        = fontGenerator_Pusia.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(59))

    private val ls59 = LabelStyle(font59, GColor.balance)

    private val typeIndex = screenType.ordinal

    private val balanceStore by lazy { game.dataStore }

    // Actors
    private val aHeader     by lazy { AHeader(this) }
    private val headerGroup by lazy { TmpGroup(this) }
    private val mainGroup   by lazy { TmpGroup(this) }

    private val imgBalance    by lazy { Image(game.all.balance) }
    private val imgProfession by lazy { Image(game.all.listProfession[typeIndex]) }
    private val btnWork       by lazy { AButton(this, AButton.Static.listWork[typeIndex]) }
    private val btnInvest     by lazy { AButton(this, AButton.Type.Invest) }
    private val btnImprove    by lazy { AButton(this, AButton.Static.listImprove[typeIndex]) }
    private val btnPP         by lazy { AButton(this, AButton.Static.listPP[typeIndex]) }
    private val panelCoinPlus by lazy { ACoinPlus(this) }
    private val progressXP    by lazy { AProgressXP(this, game.all.listProgress[typeIndex]) }
    protected val lblBalance  by lazy { Label(balanceStore.balance.toString(), ls59) }

    override fun show() {
        mainGroup.animHide()
        super.show()
        mainGroup.animShow(TIME_ANIM)

        currentContainerBlockXP.block = Block {
            progressXP.setPercent(currentContainerXP.valueXP)
        }
        checkListReserveInvest()
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        stageBack.addSystemUI()
        addAndFillActors(headerGroup, mainGroup)
        listNavigationGroup.add(mainGroup)
        addPanels()

        headerGroup.apply {
            addImgBalance()
            addProgressXP()
            addLblBalance()
        }

        mainGroup.apply {
            addImgProfession()
            addBtnWork()
            addBtnInvest()
            addBtnImprove()
            addBtnPP()
            addPanelCoinPlus()

            addActorsOnMainGroup()
        }
    }

    override fun dispose() {
        currentContainerBlockXP.block = Block { }
        super.dispose()
    }

    abstract fun AdvancedGroup.addActorsOnMainGroup()

    // System UI ------------------------------------------------------------------
    private fun AdvancedStage.addSystemUI() {
        addActor(aHeader)
        aHeader.setBounds(0f, 2057f, 1f, 1f)
    }

    // System UI ------------------------------------------------------------------
    private fun AdvancedStage.addPanels() {
        val panels = listOf<AdvancedGroup>(
            panelImprovements,
            panelInvestments,
        )
        addAndFillActors(panels)

        panels.onEach {
            it.disable()
            it.color.a = 0f
        }

        panelImprovements.apply {
            blockBack = Block { backGroup() }
            blockItem = { itemSum, itemXP ->
                if (itemSum <= balanceStore.balance) {
                    screen.game.soundUtil.apply { play(buy, 0.18f) }

                    balanceStore.updateBalance { it - itemSum }
                    lblBalance.setText(balanceStore.balance)

                    if (currentContainerXP.valueXP + itemXP <= 100) currentContainerXP.valueXP += itemXP else currentContainerXP.valueXP = 100f
                    progressXP.setPercent(currentContainerXP.valueXP)

                } else {
                    screen.game.soundUtil.apply { play(fail, 0.18f) }
                }
            }
        }
        panelInvestments.apply {
            blockBack = Block { backGroup() }
            blockItem = { index, itemSum, itemResult, itemSeconds ->
                if (itemSum <= balanceStore.balance) {
                    screen.game.soundUtil.apply { play(buy, 0.18f) }

                    balanceStore.updateBalance { it - itemSum }
                    lblBalance.setText(balanceStore.balance)

                    log("click $itemSum")
                    game.workerInvestUtil.startWorkerInvest(typeIndex, index, itemResult, itemSeconds.toLong())
                } else {
                    screen.game.soundUtil.apply { play(fail, 0.18f) }
                }
            }
        }
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedGroup.addImgBalance() {
        addActor(imgBalance)
        imgBalance.setBounds(66f, 2100f, 980f, 177f)
    }

    private fun AdvancedGroup.addImgProfession() {
        addActor(imgProfession)
        imgProfession.setBounds(124f, 1190f, 865f, 779f)
    }

    private fun AdvancedGroup.addPanelCoinPlus() {
        addActor(panelCoinPlus)
        panelCoinPlus.setBounds(24f, 779f, 1066f, 293f)
    }

    private fun AdvancedGroup.addProgressXP() {
        addActor(progressXP)
        progressXP.setBounds(601f, 2105f, 445f, 59f)

        progressXP.setPercent(currentContainerXP.valueXP)
    }

    private fun AdvancedGroup.addBtnWork() {
        addActor(btnWork)
        btnWork.setBounds(205f, 752f, 713f, 162f)

        btnWork.setOnClickListener(null) {
            if (currentContainerXP.valueXP - 2.5f >= 0f) {

                game.soundUtil.apply { play(listCoin.random(), 0.18f) }

                panelCoinPlus.startAnim()
                currentContainerXP.valueXP -= 2.5f
                progressXP.setPercent(currentContainerXP.valueXP)
                balanceStore.updateBalance { it + 10 }
                lblBalance.setText(balanceStore.balance)
            }
        }
    }

    private fun AdvancedGroup.addBtnImprove() {
        addActor(btnImprove)
        btnImprove.setBounds(111f, 430f, 382f, 216f)

        btnImprove.setOnClickListener {
            navigateToGroup(mainGroup, panelImprovements)
        }
    }

    private fun AdvancedGroup.addBtnInvest() {
        addActor(btnInvest)
        btnInvest.setBounds(638f, 430f, 365f, 216f)

        btnInvest.setOnClickListener {
            navigateToGroup(mainGroup, panelInvestments)
        }
    }

    private fun AdvancedGroup.addBtnPP() {
        addActor(btnPP)
        btnPP.setBounds(111f, 177f, 892f, 135f)

        btnPP.setOnClickListener {
            game.activity.showUrl("https://boldvisionwork.site/", false)
        }
    }

    private fun AdvancedGroup.addLblBalance() {
        addActor(lblBalance)
        lblBalance.setBounds(135f, 2111f, 98f, 41f)
    }

    // Logic ------------------------------------------------------------------

    protected fun infinityUpdateProgressXP() {
        game.coroutine.launch {
            while (isActive) {
                delay(75L)

                if (currentContainerXP.valueXP + 0.25f <= 100f) {
                    currentContainerXP.valueXP += 0.25f

                    currentContainerBlockXP.block.invoke()
                }
            }
        }
    }

    private fun checkListReserveInvest() {
        LIST_RESERVE_INVEST.onEach {
            showDialogInvest(it.screenTypeIndex, it.investmentIndex, it.investment)
        }
        LIST_RESERVE_INVEST.clear()
    }

    // Dialog -------------------------------------------------------------------

    fun showDialogInvest(screenTypeIndex: Int, investmentIndex: Int, investment: Int) {
        val dialog = ADialogInvest(this, screenTypeIndex, investmentIndex)
        stageUI.addActor(dialog)
        dialog.setBounds(202f, 898f, 710f, 615f)
        dialog.animShow(TIME_ANIM)

        balanceStore.updateBalance { it + investment }
        lblBalance.setText(balanceStore.balance)
    }

}