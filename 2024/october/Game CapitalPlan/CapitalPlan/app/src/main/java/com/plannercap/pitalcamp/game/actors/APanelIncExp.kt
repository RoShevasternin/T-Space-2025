package com.plannercap.pitalcamp.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.plannercap.pitalcamp.game.data.Income
import com.plannercap.pitalcamp.game.utils.GColor
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedGroup
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedScreen
import com.plannercap.pitalcamp.game.utils.font.FontParameter

class APanelIncExp(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val fontRegular39 = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(39))
    private val fontMedium35  = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(35))

    private val ls39 = LabelStyle(fontRegular39, GColor.dark)
    private val ls35 = LabelStyle(fontMedium35, GColor.green)

    private val imgPanel      = Image(screen.game.all.IncomeExpensePanel)
    private val verticalGroup = NewVerticalGroup(screen, 65f, 65f, 65f, isWrap = true)
    private val scroll        = ScrollPane(verticalGroup)


    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        addActor(scroll)
        scroll.setBounds(31f,0f,664f,1006f)
        verticalGroup.setSize(664f,1006f)

        update()
    }

    fun update() {
        verticalGroup.disposeChildren()

        val listIncome  = screen.game.incomeUtil.incomeListFlow.value
        val listExpense = screen.game.expenseUtil.expenseListFlow.value
        val listAll     = (listIncome + listExpense).sortedByDescending { it.uuid }

        val listIncExp = List(listAll.size) {
            AIncExp(
                screen,
                listAll[it].nameDesire,
                listAll[it].summa,
                ls39, ls35,
                if (listAll[it] is Income) "+" else "-"
            )
        }

        listIncExp.onEachIndexed { _, aIncExp ->
            aIncExp.setSize(664f,52f)
            verticalGroup.addActor(aIncExp)
        }
    }

}