package com.frusune.abvger.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.frusune.abvger.game.LibGDXGame
import com.frusune.abvger.game.actors.*
import com.frusune.abvger.game.actors.scroller.AScroller
import com.frusune.abvger.game.utils.Block
import com.frusune.abvger.game.utils.TIME_ANIM
import com.frusune.abvger.game.utils.actor.animHide
import com.frusune.abvger.game.utils.actor.animShow
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.advanced.AdvancedStage

class AddScreen(override val game: LibGDXGame) : AdvancedScreen() {

    // Actors
    private val aHeader  = AHeader(this)
    private val tmpGroup = TmpGroup(this)

    private val aScroller   = AScroller(this)
    private val aInputPanel = AInputPanel(this)

    override fun show() {
        tmpGroup.animHide()
        super.show()
        tmpGroup.animShow(TIME_ANIM) {
            animInputPanel()
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAHeader()

        addAndFillActors(tmpGroup)
        tmpGroup.apply {
            addAScroller()
            addAInputPanel()
        }
    }

    private fun AdvancedStage.addAHeader() {
        addActor(aHeader)
        aHeader.setBounds(12f,1470f,750f,186f)
    }

    private fun AdvancedGroup.addAScroller() {
        addActor(aScroller)
        aScroller.setBounds(5f,1205f,764f,253f)
    }

    private fun AdvancedGroup.addAInputPanel() {
        addActor(aInputPanel)
        aInputPanel.setBounds(15f,-1100f,744f,1052f)

        aInputPanel.apply {
            blockRemove = Block {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
            blockDone = Block {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AllInvestScreen::class.java.name)
                }
            }
        }
    }

    // Anim ---------------------------------------------------------------------------

    private fun animInputPanel() {
        aInputPanel.addAction(Actions.moveTo(15f,0f, 0.7f, Interpolation.swingOut))
    }

}