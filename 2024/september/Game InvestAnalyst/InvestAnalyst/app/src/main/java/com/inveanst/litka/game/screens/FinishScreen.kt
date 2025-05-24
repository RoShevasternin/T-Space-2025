package com.inveanst.litka.game.screens

import com.inveanst.litka.R
import com.inveanst.litka.appContext
import com.inveanst.litka.game.LibGDXGame
import com.inveanst.litka.game.actors.ABackground
import com.inveanst.litka.game.actors.TmpGroup
import com.inveanst.litka.game.actors.panel.AFinishPanel
import com.inveanst.litka.game.actors.panel.ATestPanel
import com.inveanst.litka.game.utils.TIME_ANIM
import com.inveanst.litka.game.utils.actor.animHide
import com.inveanst.litka.game.utils.actor.animShow
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.advanced.AdvancedStage

class FinishScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val indexType = MenuScreen.selectedTestType.ordinal

    private val listFinish1 = appContext.resources.getStringArray(R.array.finish_1)
    private val listFinish2 = appContext.resources.getStringArray(R.array.finish_2)

    private val tmpGroup      = TmpGroup(this)
    private val imgBackground = ABackground(this)

    private val indexByResult = when(TestScreen.RESULT) {
        in 0..2 -> 0
        in 3..4 -> 1
        5             -> 2
        else -> 0
    }

    private val panel = AFinishPanel(this,
        appContext.resources.getStringArray(R.array.selected_test_title)[indexType],
        game.all.listResult[indexType],
        TestScreen.RESULT,
        listFinish1[indexByResult],
        listFinish2[indexByResult],
    )

    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActors(imgBackground, tmpGroup)
        tmpGroup.apply {
            color.a = 0f
            addPanel()
            animShow(TIME_ANIM)
        }

        game.soundUtil.apply { play(win_game, 0.8f) }

    }

    private fun AdvancedGroup.addPanel() {
        addActor(panel)
        panel.setBounds(28f, 113f, 319f, 492f)
        panel.blockBack = {
            tmpGroup.animHide(TIME_ANIM) { game.navigationManager.back() }
        }
    }

}