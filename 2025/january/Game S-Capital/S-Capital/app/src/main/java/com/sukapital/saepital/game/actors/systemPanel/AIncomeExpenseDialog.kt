package com.sukapital.saepital.game.actors.systemPanel

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sukapital.saepital.game.actors.systemPanel.actors.AIncomeExpense
import com.sukapital.saepital.game.utils.GColor
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.disable
import com.sukapital.saepital.game.utils.enable

class AIncomeExpenseDialog(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgBackground = Image(screen.drawerUtil.getRegion(GColor.black_50))
    private val aPanel        = AIncomeExpense(screen)

    var blockRemove: () -> Unit = {}
    var blockDone  : () -> Unit = {}

    override fun addActorsOnGroup() {
        disable()
        screen.topStageBack.addAndFillActor(imgBackground)
        imgBackground.y = -screen.topViewportBack.worldHeight

        addPanel()
    }

    private fun addPanel() {
        addActor(aPanel)
        aPanel.setSize(827f,617f)
        aPanel.y = -617f

        aPanel.blockRemove = { blockRemove() }
        aPanel.blockDone   = { blockDone() }
    }

    // Logic -------------------------------------------------------------------

    fun showDialog() {
        enable()
        screen.stageUI.root.disable()
        imgBackground.addAction(Actions.moveTo(0f,0f,0.25f))
        aPanel.addAction(Actions.moveTo(0f,0f,0.25f))
        aPanel.reset()
    }

    fun hideDialog() {
        disable()
        screen.stageUI.root.enable()
        imgBackground.addAction(Actions.moveTo(0f, -screen.topViewportBack.worldHeight,0.25f))
        aPanel.addAction(Actions.moveTo(0f, -617f,0.25f))
    }

}