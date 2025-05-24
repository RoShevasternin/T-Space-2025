package com.tinfenker.capitalnoestroy.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.tinfenker.capitalnoestroy.game.LibGDXGame
import com.tinfenker.capitalnoestroy.game.actors.ABottom
import com.tinfenker.capitalnoestroy.game.actors.AHeader
import com.tinfenker.capitalnoestroy.game.actors.TmpGroup
import com.tinfenker.capitalnoestroy.game.actors.dataPanel.ABigData
import com.tinfenker.capitalnoestroy.game.actors.layout.AVerticalGroup
import com.tinfenker.capitalnoestroy.game.actors.scroller.AScroller
import com.tinfenker.capitalnoestroy.game.utils.TIME_ANIM
import com.tinfenker.capitalnoestroy.game.utils.actor.animHide
import com.tinfenker.capitalnoestroy.game.utils.actor.animShow
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedGroup
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedScreen
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedStage
import com.tinfenker.capitalnoestroy.game.utils.font.FontParameter

class AllInvestScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val fontMedium37  = fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(37))

    private val ls37 = LabelStyle(fontMedium37, Color.BLACK)

    // Actors
    private val aHeader  = AHeader(this)
    private val tmpGroup = TmpGroup(this)

    private val aScroller = AScroller(this)
    private val aBottom   = ABottom(this)

    private val verticalGroup = AVerticalGroup(this,-16f, isWrap = true, endGap = 100f)
    private val scroll        = ScrollPane(verticalGroup)

    private val listContribution = game.contributionUtil.contributionListFlow.value.reversed()
    private val listBigData = List(listContribution.size) {
        ABigData(this,
            listContribution[it].title,
            listContribution[it].summa,
            listContribution[it].period,
            listContribution[it].percent,
            ls37
        )
    }

    override fun show() {
        tmpGroup.animHide()
        super.show()
        tmpGroup.animShow(TIME_ANIM)
    }

    override fun dispose() {
        super.dispose()
        verticalGroup.dispose()
        //fontMediumBold.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAHeader()

        addAndFillActors(tmpGroup)
        tmpGroup.apply {
            addScroll()
            addAScroller()
            addABottom()
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

    private fun AdvancedGroup.addABottom() {
        addActor(aBottom)
        aBottom.setBounds(12f,4f,750f,196f)
        aBottom.checkInvest()

        val listScreen = listOf(
            AddScreen::class.java.name,
            MainScreen::class.java.name,
            AllInvestScreen::class.java.name,
        )
        aBottom.blockNavTo = { type ->
            tmpGroup.animHide(TIME_ANIM) {
                game.navigationManager.navigate(listScreen[type.ordinal], AllInvestScreen::class.java.name)
            }
        }
    }

    private fun AdvancedGroup.addScroll() {
        addActor(scroll)
        scroll.setBounds(5f, 102f, 764f,1068f)
        verticalGroup.apply {
            setSize(764f,1068f)
            addListBigData()
        }
    }

    private fun AVerticalGroup.addListBigData() {
        listBigData.onEach { aBigData ->
            aBigData.setSize(764f,542f)
            addActor(aBigData)
            aBigData.blockRemove = {
                game.contributionUtil.update { contributions ->
                    contributions.apply { remove(contributions.first {
                        it.title  == aBigData.title &&
                        it.summa  == aBigData.summa &&
                        it.period == aBigData.period
                    }) }
                }
                aBigData.addAction(Actions.removeActor())
            }
            aBigData.blockEdit = {
                game.contributionUtil.update { contributions ->
                    contributions.apply { remove(contributions.first {
                        it.title  == aBigData.title &&
                                it.summa  == aBigData.summa &&
                                it.period == aBigData.period
                    }) }
                }
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AddScreen::class.java.name, AllInvestScreen::class.java.name)
                }
            }
        }
    }

}