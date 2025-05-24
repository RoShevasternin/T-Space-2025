package com.eqcpert.padlotka.game.screens

import com.eqcpert.padlotka.R
import com.eqcpert.padlotka.game.LibGDXGame
import com.eqcpert.padlotka.game.actors.AButton
import com.eqcpert.padlotka.game.actors.AClosedLevel
import com.eqcpert.padlotka.game.actors.panel.*
import com.eqcpert.padlotka.game.utils.*
import com.eqcpert.padlotka.game.utils.actor.animHide
import com.eqcpert.padlotka.game.utils.advanced.AdvancedGroup

class Work_2_Screen(override val game: LibGDXGame) : AbstractWorkScreen(ScreenType._2) {

    companion object {
        private var isFirstShow = true

        // 0..100
        private var containerXP = ContainerXP(100f)

        private var containerBlockXP = ContainerBlockXP(Block{})
    }

    override val currentContainerXP      = containerXP
    override val currentContainerBlockXP = containerBlockXP

    override val panelImprovements = AImprovements_2_Panel(this, screenType)
    override val panelInvestments  = AInvestments_2_Panel(this, screenType)

    // Actors
    private val btnRight = AButton(this, AButton.Type.Right)
    private val btnLeft  = AButton(this, AButton.Type.Left)

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false

            infinityUpdateProgressXP()
        }

        game.backgroundColor = GColor.lvl_2
        game.activity.setNavBarColor(R.color.lvl_2)

        super.show()
    }

    override fun AdvancedGroup.addActorsOnMainGroup() {
        addBtns()

        if (game.dataStore.level_2.not()) {
            stageUI.root.disable()

            topStageUI.addAndFillActor(
                AClosedLevel(this@Work_2_Screen, screenType.ordinal).also { level ->
                    level.blockOpenLvl = { price ->
                        if (price <= game.dataStore.balance) {
                            screen.game.soundUtil.apply { play(buy, 0.18f) }

                            game.dataStore.updateBalance { it - price }
                            lblBalance.setText(game.dataStore.balance)

                            game.dataStore.updateLVL_2 { true }
                            level.close()

                            stageUI.root.enable()
                        } else {
                            screen.game.soundUtil.apply { play(fail, 0.18f) }
                        }
                    }
                    level.blockCloseLvl = {
                        this.animHide(TIME_ANIM)
                        level.close()
                        game.navigationManager.back()
                    }

                }
            )
        }
    }

    private fun AdvancedGroup.addBtns() {
        addActors(btnLeft, btnRight)
        btnLeft.setBounds(24f, 1505f, 70f, 148f)
        btnRight.setBounds(1018f, 1505f, 70f, 148f)

        btnLeft.setOnClickListener {
            animHide(TIME_ANIM) {
                game.navigationManager.navigate(Work_1_Screen::class.java.name)
            }
        }
        btnRight.setOnClickListener {
            animHide(TIME_ANIM) {
                game.navigationManager.navigate(Work_3_Screen::class.java.name, Work_2_Screen::class.java.name)
            }
        }
    }

}