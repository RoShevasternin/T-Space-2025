package com.eqcpert.ginvestrum.game.screens

import com.eqcpert.ginvestrum.R
import com.eqcpert.ginvestrum.game.LibGDXGame
import com.eqcpert.ginvestrum.game.actors.AButton
import com.eqcpert.ginvestrum.game.actors.panel.AImprovements_1_Panel
import com.eqcpert.ginvestrum.game.actors.panel.AInvestments_1_Panel
import com.eqcpert.ginvestrum.game.utils.*
import com.eqcpert.ginvestrum.game.utils.actor.animHide
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedGroup

class Work_1_Screen(override val game: LibGDXGame) : AbstractWorkScreen(ScreenType._1) {

    companion object {
        private var isFirstShow = true

        // 0..100
        private var containerXP = ContainerXP(100f)

        private var containerBlockXP = ContainerBlockXP(Block{})
    }

    override val currentContainerXP      = containerXP
    override val currentContainerBlockXP = containerBlockXP

    override val panelImprovements = AImprovements_1_Panel(this, screenType)
    override val panelInvestments  = AInvestments_1_Panel(this, screenType)

    // Actors
    private val btnRight = AButton(this, AButton.Type.Right)

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false

            game.musicUtil.also { mu ->
                mu.music = mu.work.apply { isLooping = true }
                mu.volumeLevelFlow.value = 7.5f
            }

            game.activity.setStatusBarColor(R.color.white)

            infinityUpdateProgressXP()
        }

        game.backgroundColor = GColor.lvl_1
        game.activity.setNavBarColor(R.color.lvl_1)

        super.show()
    }

    override fun AdvancedGroup.addActorsOnMainGroup() {
        addBtns()
    }

    private fun AdvancedGroup.addBtns() {
        addActor(btnRight)
        btnRight.setBounds(1018f, 1505f, 70f, 148f)

        btnRight.setOnClickListener {
            animHide(TIME_ANIM) {
                game.navigationManager.navigate(Work_2_Screen::class.java.name, Work_1_Screen::class.java.name)
            }
        }
    }

}