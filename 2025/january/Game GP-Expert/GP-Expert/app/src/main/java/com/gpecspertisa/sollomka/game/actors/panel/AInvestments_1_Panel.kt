package com.gpecspertisa.sollomka.game.actors.panel

import com.gpecspertisa.sollomka.game.screens.AbstractWorkScreen
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedScreen

class AInvestments_1_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractInvestmentsPanel() {

    override val listItemSum     = listOf(300, 500, 1000)
    override val listItemResult  = listOf(330, 750, 2000)
    override val listItemSeconds = listOf(10, 15, 20)

}