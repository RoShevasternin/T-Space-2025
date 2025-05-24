package com.frusune.abvger.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.frusune.abvger.game.LibGDXGame
import com.frusune.abvger.game.actors.AButton
import com.frusune.abvger.game.actors.AHeader
import com.frusune.abvger.game.actors.ATopInvest
import com.frusune.abvger.game.actors.TmpGroup
import com.frusune.abvger.game.actors.dataPanel.ATopData
import com.frusune.abvger.game.actors.scroller.AScroller
import com.frusune.abvger.game.utils.TIME_ANIM
import com.frusune.abvger.game.utils.actor.animHide
import com.frusune.abvger.game.utils.actor.animShow
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.advanced.AdvancedStage
import com.frusune.abvger.game.utils.font.FontParameter

class MainScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirstShow = true
    }

    private val fontParameter = FontParameter()
    private val fontMedium37  = fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(37))

    private val ls37 = LabelStyle(fontMedium37, Color.BLACK)

    // Actors
    private val aHeader  = AHeader(this)
    private val tmpGroup = TmpGroup(this)

    private val aScroller  = AScroller(this)
    private val aTopInvest = ATopInvest(this)

    private val btnAddInvest = AButton(this, AButton.Static.Type.AddInvest)
    private val btnSeeAll    = AButton(this, AButton.Static.Type.SeeAll)

    private val topContribution = game.contributionUtil.contributionListFlow.value.maxByOrNull { it.percent }
    private val aTopData: ATopData? = if (topContribution != null) ATopData(this,
        topContribution.title,
        topContribution.summa,
        topContribution.period,
        topContribution.percent,
        ls37
    ) else null

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false
            aHeader.animHide()
        }
        tmpGroup.animHide()
        super.show()
        aHeader.animShow(TIME_ANIM)
        tmpGroup.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAHeader()

        addAndFillActors(tmpGroup)
        tmpGroup.apply {
            addAScroller()
            addATopInvest()
            addBtnAddInvest()
            addBtnSeeAll()
            addATopData()
        }
    }

    private fun AdvancedStage.addAHeader() {
        addActor(aHeader)
        aHeader.setBounds(12f,1470f,750f,186f)
    }

    private fun AdvancedGroup.addAScroller() {
        addActor(aScroller)
        aScroller.setBounds(5f,1205f,764f,253f)
    }

    private fun AdvancedGroup.addATopInvest() {
        addActor(aTopInvest)
        aTopInvest.setBounds(4f,0f,766f,933f)
        aTopInvest.checkHome()

        val listScreen = listOf(
            AddScreen::class.java.name,
            MainScreen::class.java.name,
            AllInvestScreen::class.java.name,
        )
        aTopInvest.blockNavTo = { type ->
            tmpGroup.animHide(TIME_ANIM) {
                game.navigationManager.navigate(listScreen[type.ordinal], MainScreen::class.java.name)
            }
        }
    }

    private fun AdvancedGroup.addBtnAddInvest() {
        addActor(btnAddInvest)

        val area = Actor()
        addActor(area)
        area.setBounds(56f,1021f,660f,115f)

        btnAddInvest.apply {
            setBounds(15f,971f,744f,199f)
            setArea(area,false)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AddScreen::class.java.name, MainScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedGroup.addBtnSeeAll() {
        addActor(btnSeeAll)

        val area = Actor()
        addActor(area)
        area.setBounds(178f,933f,419f,77f)

        btnSeeAll.apply {
            setBounds(276f,961f,221f,31f)
            setArea(area,false)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AllInvestScreen::class.java.name, MainScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedGroup.addATopData() {
        if (aTopData != null) {
            addActor(aTopData)
            aTopData.setBounds(56f, 215f, 662f, 541f)
            aTopData.blockRemove = {
                game.contributionUtil.update { contributions ->
                    contributions.apply { remove(contributions.first {
                        it.title  == aTopData.title &&
                                it.summa  == aTopData.summa &&
                                it.period == aTopData.period
                    }) }
                }
                aTopData.addAction(Actions.removeActor())
            }
            aTopData.blockEdit = {
                game.contributionUtil.update { contributions ->
                    contributions.apply { remove(contributions.first {
                        it.title  == aTopData.title &&
                                it.summa  == aTopData.summa &&
                                it.period == aTopData.period
                    }) }
                }
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AddScreen::class.java.name, AllInvestScreen::class.java.name)
                }
            }
        }
    }

}