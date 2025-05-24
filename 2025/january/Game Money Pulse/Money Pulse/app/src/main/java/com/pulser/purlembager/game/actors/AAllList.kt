package com.pulser.purlembager.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.pulser.purlembager.game.actors.autoLayout.AVerticalGroup
import com.pulser.purlembager.game.actors.autoLayout.AutoLayout
import com.pulser.purlembager.game.dataStore.DataTransactionType
import com.pulser.purlembager.game.utils.GameColor
import com.pulser.purlembager.game.utils.actor.setBounds
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.font.FontParameter
import com.pulser.purlembager.game.utils.gdxGame

class AAllList(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val listTransaction = gdxGame.ds_Transaction.flow.value.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font50        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(50))

    private val ls50_W = Label.LabelStyle(font50, GameColor.white_FA)
    private val ls50_G = Label.LabelStyle(font50, GameColor.green_53)
    private val ls50_R = Label.LabelStyle(font50, GameColor.red_FF)

    private val aBack    = ABack(screen, "Все расходы и доходы", ABack.Type.White)
    private val listItem = List(listTransaction.size) {
        AItem(screen,
            listTransaction[it],
            ls50_W,
            if (listTransaction[it].type == DataTransactionType.Income) ls50_G else ls50_R
        )
    }

    private val verticalGroup = AVerticalGroup(screen, 126f, isWrap = true, alignmentHorizontal = AutoLayout.AlignmentHorizontal.CENTER)
    private val scroll        = AScrollPane(verticalGroup)

    var blockBack = {}

    override fun addActorsOnGroup() {
        addABack()
        addScroll()
    }

    // Actors -----------------------------------------------------

    private fun addABack() {
        addActor(aBack)
        aBack.apply {
            setBounds(0f, 2393f, 1176f, 63f)
            this.blockBack = { this@AAllList.blockBack() }
        }
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 0f, 1176f, 2232f)

        verticalGroup.apply {
            listItem.onEach { item ->
                item.setSize(956f, 64f)
                addActor(item)
            }
        }
    }

}