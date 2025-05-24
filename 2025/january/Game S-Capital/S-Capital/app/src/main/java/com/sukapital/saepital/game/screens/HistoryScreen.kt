package com.sukapital.saepital.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.sukapital.saepital.game.LibGDXGame
import com.sukapital.saepital.game.actors.APanelIncExp
import com.sukapital.saepital.game.actors.TmpGroup
import com.sukapital.saepital.game.actors.systemPanel.AIncomeExpenseDialog
import com.sukapital.saepital.game.actors.systemUI.ABottom
import com.sukapital.saepital.game.actors.systemUI.AHeader
import com.sukapital.saepital.game.utils.TIME_ANIM
import com.sukapital.saepital.game.utils.WIDTH_UI
import com.sukapital.saepital.game.utils.actor.animHide
import com.sukapital.saepital.game.utils.actor.animShow
import com.sukapital.saepital.game.utils.actor.setOnClickListener
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.advanced.AdvancedStage

class HistoryScreen(override val game: LibGDXGame) : AdvancedScreen() {

    // Actors
    private val aHeader  = AHeader(this)
    private val aBottom  = ABottom(this)

    private val tmpGroup = TmpGroup(this)
    private val aIncExp  = APanelIncExp(this)
    private val aIncomeExpensePanel = AIncomeExpenseDialog(this)

    override fun show() {
        tmpGroup.animHide()
        super.show()
        tmpGroup.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActors(tmpGroup)
        addSystemUI()
        aBottom.checkHome()

        tmpGroup.apply {
            addScroll()
        }

        topStageUI.apply {
            addPanels()
        }
    }

    private fun AdvancedStage.addPanels() {
        addAndFillActors(aIncomeExpensePanel)

        aIncomeExpensePanel.apply {
            blockRemove = {
                hideDialog()

            }
            blockDone = {
                hideDialog()

                stageUI.root.addAction(
                    Actions.sequence(
                        Actions.delay(0.1f),
                        Actions.run { aIncExp.update() }
                    ))
            }
        }
    }

    private fun AdvancedStage.addSystemUI() {
        addActor(aHeader)
        aHeader.setBounds(0f,1552f, WIDTH_UI,240f)
        addActor(aBottom)
        aBottom.setBounds(0f,0f, WIDTH_UI,187f)

        aBottom.blockNavTo = {
            if (it == ABottom.ClickType.Blog) tmpGroup.animHide(TIME_ANIM) {
                game.navigationManager.navigate(BlogScreen::class.java.name, HistoryScreen::class.java.name)
            }
            if (it == ABottom.ClickType.Plus) {
                aIncomeExpensePanel.showDialog()
            }
        }
    }

    private fun AdvancedGroup.addScroll() {
        addActor(aIncExp)
        aIncExp.setBounds(49f,295f, 730f,1149f)


        val aBack = Actor()
        addActor(aBack)
        aBack.apply {
            setBounds(88f, 1336f, 74f, 74f)
            setOnClickListener(game.soundUtil) { tmpGroup.animHide(TIME_ANIM) { game.navigationManager.back() } }
        }

    }

}