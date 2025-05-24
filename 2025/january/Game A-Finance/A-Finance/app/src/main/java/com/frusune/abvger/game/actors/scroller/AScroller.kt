package com.frusune.abvger.game.actors.scroller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.frusune.abvger.R
import com.frusune.abvger.appContext
import com.frusune.abvger.game.actors.layout.AHorizontalGroup
import com.frusune.abvger.game.utils.GColor
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.font.FontParameter

class AScroller(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val fontRegular33 = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(33))
    private val fontBold53    = screen.fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS_RUB).setSize(53))

    private val lsRegular33 = Label.LabelStyle(fontRegular33, GColor.grayLight)
    private val lsBold53    = Label.LabelStyle(fontBold53, Color.BLACK)

    private val listPeriod = listOf(1, 12, 52)
    private val allSumma = screen.game.contributionUtil.contributionListFlow.value.sumOf { it.summa }
    private val listBanner = List(3) { ABanner(
        screen,
        appContext.resources.getStringArray(R.array.banner_titles)[it],
        allSumma / listPeriod[it],
        lsRegular33,
        lsBold53
    ) }

    private val horizontalGroup = AHorizontalGroup(screen,5f, isWrapH = true)
    private val scroll          = ScrollPane(horizontalGroup)
    private val aPoints         = APoints(screen)

    // Field
    private val listPercentX = listOf(0f, 0.5f, 1f)

    override fun addActorsOnGroup() {
        addScroll()
        addPoints()
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f,12f,764f,241f)
        horizontalGroup.setSize(764f,241f)
        horizontalGroup.addListBanner()
    }

    private fun AHorizontalGroup.addListBanner() {
        listBanner.onEach { banner ->
            banner.setSize(764f,241f)
            addActor(banner)
        }
    }

    private fun addPoints() {
        addActor(aPoints)
        aPoints.setBounds(305f,0f,155f,31f)

        aPoints.blockCheck = { indexBox ->
            scroll.scrollPercentX = listPercentX[indexBox]
        }
    }

    private var time = 0f
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        time += Gdx.graphics.deltaTime
        if (time >= 0.075) {
            time = 0f

            scroll.scrollPercentX.also { percentX ->
                when (percentX) {
                    in 0f..0.40f    -> 0
                    in 0.40f..0.94f -> 1
                    in 0.95f..1f    -> 2
                    else                  -> 0
                }.also { indexPoint -> aPoints.check(indexPoint) }
            }
        }
    }
}