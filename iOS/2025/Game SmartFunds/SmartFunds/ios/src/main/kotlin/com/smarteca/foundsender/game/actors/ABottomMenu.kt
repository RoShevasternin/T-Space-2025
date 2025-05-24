package com.smarteca.foundsender.game.actors

import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.utils.advanced.AdvancedGroup
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen

class ABottomMenu(
    override val screen: AdvancedScreen,
    val current: Type,
): AdvancedGroup() {

    private val listBtn = List(5) { AButton(screen, AButton.Type.listItem[it]) }

    var blockDashboard  = {}
    var blockSavings    = {}
    var blockCalculator = {}
    var blockGlossary   = {}
    var blockTest       = {}

    private val listBlock = listOf(
        ::blockDashboard,
        ::blockSavings,
        ::blockCalculator,
        ::blockGlossary,
        ::blockTest,
    )

    override fun addActorsOnGroup() {
        var nx = 0f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(nx, 0f, 237f, 148f)
            nx += -1f + 237f

            btn.setOnClickListener {
                btn.pressAndDisable()
                listBlock[index].get().invoke()
            }

            if (current.ordinal == index) btn.pressAndDisable()
        }
    }

    // class ------------------------------------------------------------

    enum class Type { Dashboard, Savings, Calculator, Glossary, Test, }

}
