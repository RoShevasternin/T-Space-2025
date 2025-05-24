package com.smarteca.foundsender.game.actors

import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.actors.progress.AProgressTest
import com.smarteca.foundsender.game.utils.actor.disable
import com.smarteca.foundsender.game.utils.advanced.AdvancedGroup
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen

class ATestWithProgress(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val btn      = AButton(screen, AButton.Type.Test_Progress)
    private val progress = AProgressTest(screen)

    var blockClick = {}

    override fun addActorsOnGroup() {
        addAndFillActor(btn)
        addActor(progress)
        progress.setBounds(42f, 60f, 964f, 12f)
        progress.disable()

        btn.setOnClickListener { blockClick() }
    }

    fun setProgress(progress: Float) {
        this.progress.progressPercentFlow.value = progress
    }

}
