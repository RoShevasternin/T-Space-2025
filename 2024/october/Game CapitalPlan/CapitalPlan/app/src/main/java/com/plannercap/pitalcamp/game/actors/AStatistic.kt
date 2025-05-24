package com.plannercap.pitalcamp.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.plannercap.pitalcamp.game.actors.progress.AStatisticProgress
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedGroup
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedScreen
import com.plannercap.pitalcamp.game.utils.font.FontParameter
import kotlin.math.max
import kotlin.math.roundToInt

class AStatistic(
    override val screen: AdvancedScreen,
    val income : Int,
    val expense: Int,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val fontRegular26 = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS_RUB).setSize(26))

    private val ls26 = LabelStyle(fontRegular26, Color.WHITE)

    private val imgPanel  = Image(screen.game.loader.panel_9)
    private val imgText   = Image(screen.game.all.desc)

    private val expensePercent = (15..55).random() / 100f
    private val expenseAny     = (expense * expensePercent).roundToInt()
    private val listSumma      = listOf(income, expense, expenseAny)
    private val listProgress = List(3) { AStatisticProgress(screen, screen.game.all.listColor[it], listSumma[it], ls26) }

    fun update(_income: Int, _expense: Int) {
        val _expensePercent = (15..55).random() / 100f
        val _expenseAny     = (_expense * _expensePercent).roundToInt()

        val _maxSumma   = max(_income, _expense)
        val _onePercent = _maxSumma / 100f

        val listPercent = listOf(
            _income / _onePercent,
            _expense / _onePercent,
            _expenseAny / _onePercent,
        )

        val listSumma = listOf(_income, _expense, _expenseAny)
        listProgress.onEachIndexed { index, aStatisticProgress ->
            aStatisticProgress.update(listSumma[index])
            aStatisticProgress.setPercent(listPercent[index])
        }
    }

    // Field
    private val maxSumma   = max(income, expense)
    private val onePercent = maxSumma / 100f

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActors(imgText)

        imgText.setBounds(21f,35f,641f,174f)

        val listPercent = listOf(
            income / onePercent,
            expense / onePercent,
            expenseAny / onePercent,
        )

        var ny = 386f
        listProgress.onEachIndexed { index, aStatisticProgress ->
            addActor(aStatisticProgress)
            aStatisticProgress.setBounds(0f, ny,685f,41f)
            ny -= 33 + 41
            aStatisticProgress.setPercent(listPercent[index])
        }

    }

}