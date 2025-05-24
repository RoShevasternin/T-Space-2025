package com.inveanst.litka.game.screens

import com.inveanst.litka.R
import com.inveanst.litka.appContext
import com.inveanst.litka.game.LibGDXGame
import com.inveanst.litka.game.actors.ABackground
import com.inveanst.litka.game.actors.TmpGroup
import com.inveanst.litka.game.actors.panel.ATestPanel
import com.inveanst.litka.game.utils.TIME_ANIM
import com.inveanst.litka.game.utils.actor.animHide
import com.inveanst.litka.game.utils.actor.animShow
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.advanced.AdvancedStage

class TestScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var RESULT = 0
            private set
    }

    private val indexType = MenuScreen.selectedTestType.ordinal

    private val listQuestion = listOf(
        appContext.resources.getStringArray(R.array.oi_question),
        appContext.resources.getStringArray(R.array.ao_question),
        appContext.resources.getStringArray(R.array.rd_question),
    )[indexType]

    private val listAnswers = listOf(
        listOf(
            appContext.resources.getStringArray(R.array.oi_answers_1),
            appContext.resources.getStringArray(R.array.oi_answers_2),
            appContext.resources.getStringArray(R.array.oi_answers_3),
            appContext.resources.getStringArray(R.array.oi_answers_4),
            appContext.resources.getStringArray(R.array.oi_answers_5),
        ),
        listOf(
            appContext.resources.getStringArray(R.array.ao_answers_1),
            appContext.resources.getStringArray(R.array.ao_answers_2),
            appContext.resources.getStringArray(R.array.ao_answers_3),
            appContext.resources.getStringArray(R.array.ao_answers_4),
            appContext.resources.getStringArray(R.array.ao_answers_5),
        ),
        listOf(
            appContext.resources.getStringArray(R.array.rd_answers_1),
            appContext.resources.getStringArray(R.array.rd_answers_2),
            appContext.resources.getStringArray(R.array.rd_answers_3),
            appContext.resources.getStringArray(R.array.rd_answers_4),
            appContext.resources.getStringArray(R.array.rd_answers_5),
        ),
    )[indexType]

    private var currentQuestionIndex = 0

    private val tmpGroup      = TmpGroup(this)
    private val imgBackground = ABackground(this)

    private val panel = ATestPanel(this,
        appContext.resources.getStringArray(R.array.selected_test_title)[indexType],
        listQuestion[currentQuestionIndex],
        listAnswers[currentQuestionIndex].toList(),
    )

    override fun AdvancedStage.addActorsOnStageUI() {
        RESULT = 0
        addAndFillActors(imgBackground, tmpGroup)
        tmpGroup.apply {
            color.a = 0f
            addPanel()
            animShow(TIME_ANIM)
        }

    }

    private fun AdvancedGroup.addPanel() {
        addActor(panel)
        panel.setBounds(28f, 26f, 319f, 629f)
        panel.blockCheck = {
            if (it) RESULT++
            currentQuestionIndex++
            if (currentQuestionIndex < 5) {
                panel.next(listQuestion[currentQuestionIndex], listAnswers[currentQuestionIndex].toList())
            } else {
                tmpGroup.animHide(TIME_ANIM) { game.navigationManager.navigate(FinishScreen::class.java.name) }
            }
        }
        panel.blockBack = {
            tmpGroup.animHide(TIME_ANIM) { game.navigationManager.back() }
        }
    }

}