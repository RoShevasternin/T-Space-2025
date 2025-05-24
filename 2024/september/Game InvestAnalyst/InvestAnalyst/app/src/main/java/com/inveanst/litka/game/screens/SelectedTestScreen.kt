package com.inveanst.litka.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.inveanst.litka.R
import com.inveanst.litka.appContext
import com.inveanst.litka.game.LibGDXGame
import com.inveanst.litka.game.actors.ABackground
import com.inveanst.litka.game.actors.TmpGroup
import com.inveanst.litka.game.actors.panel.ASelectedTestPanel
import com.inveanst.litka.game.utils.TIME_ANIM
import com.inveanst.litka.game.utils.actor.animHide
import com.inveanst.litka.game.utils.actor.animShow
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.advanced.AdvancedStage

class SelectedTestScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val indexType = MenuScreen.selectedTestType.ordinal

    private val tmpGroup      = TmpGroup(this)
    private val imgBackground = ABackground(this)

    private val panel  = ASelectedTestPanel(this,
        game.all.listIcon[indexType],
        appContext.resources.getStringArray(R.array.selected_test_title)[indexType],
        appContext.resources.getStringArray(R.array.selected_test_text)[indexType],
    )
    private val scroll = ScrollPane(panel)

    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActors(imgBackground, tmpGroup)
        tmpGroup.apply {
            color.a = 0f
            addScroll()
            animShow(TIME_ANIM)
        }

        panel.apply {
            blockGoTest = {
                tmpGroup.animHide(TIME_ANIM) { game.navigationManager.navigate(TestScreen::class.java.name, SelectedTestScreen::class.java.name) }
            }
            blockBack = {
                tmpGroup.animHide(TIME_ANIM) { game.navigationManager.back() }
            }
        }
    }

    override fun dispose() {
        super.dispose()
        panel.dispose()
    }

    private fun AdvancedGroup.addScroll() {
        addActor(scroll)
        scroll.setBounds(28f, 0f, 319f, 685f)
    }

}