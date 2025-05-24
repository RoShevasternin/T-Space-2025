package com.tinfenker.capitalnoestroy.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tinfenker.capitalnoestroy.game.LibGDXGame
import com.tinfenker.capitalnoestroy.game.actors.*
import com.tinfenker.capitalnoestroy.game.actors.layout.AVerticalGroup
import com.tinfenker.capitalnoestroy.game.actors.layout.Layout
import com.tinfenker.capitalnoestroy.game.actors.scroller.AScroller
import com.tinfenker.capitalnoestroy.game.utils.Block
import com.tinfenker.capitalnoestroy.game.utils.TIME_ANIM
import com.tinfenker.capitalnoestroy.game.utils.actor.animHide
import com.tinfenker.capitalnoestroy.game.utils.actor.animShow
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedGroup
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedScreen
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedStage

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