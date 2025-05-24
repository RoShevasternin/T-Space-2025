package com.bobrevsc.tkarierraperbola.game.actors.panel

import com.bobrevsc.tkarierraperbola.game.screens.AbstractWorkScreen
import com.bobrevsc.tkarierraperbola.game.utils.Block
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedScreen

class AInvestments_2_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractInvestmentsPanel() {

    override val listItemSum     = listOf(1000, 1500, 2000)
    override val listItemResult  = listOf(1100, 1800, 4000)
    override val listItemSeconds = listOf(25, 30, 35)

}