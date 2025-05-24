package com.bobrevsc.tkarierraperbola.game.screens

import com.bobrevsc.tkarierraperbola.R
import com.bobrevsc.tkarierraperbola.game.LibGDXGame
import com.bobrevsc.tkarierraperbola.game.actors.AButton
import com.bobrevsc.tkarierraperbola.game.actors.AClosedLevel
import com.bobrevsc.tkarierraperbola.game.actors.panel.*
import com.bobrevsc.tkarierraperbola.game.utils.*
import com.bobrevsc.tkarierraperbola.game.utils.actor.animHide
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedGroup

class Work_4_Screen(override val game: LibGDXGame) : AbstractWorkScreen(ScreenType._4) {

    companion object {
        private var isFirstShow = true

        // 0..100
        private var containerXP = ContainerXP(100f)

        private var containerBlockXP = ContainerBlockXP(Block{})
    }

    override val currentContainerXP      = containerXP
    override val currentContainerBlockXP = containerBlockXP

    override val panelImprovements = AImprovements_4_Panel(this, screenType)
    override val panelInvestments  = AInvestments_4_Panel(this, screenType)

    // Actors
    private val btnLeft  = AButton(this, AButton.Type.Left)

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false

            infinityUpdateProgressXP()
        }

        game.backgroundColor = GColor.lvl_4
        game.activity.setNavBarColor(R.color.lvl_4)

        super.show()
    }

    override fun AdvancedGroup.addActorsOnMainGroup() {
        addBtns()

        if (game.dataStore.level_4.not()) {
            stageUI.root.disable()

            topStageUI.addAndFillActor(
                AClosedLevel(this@Work_4_Screen, screenType.ordinal).also { level ->
                    level.blockOpenLvl = { price ->
                        if (price <= game.dataStore.balance) {
                            screen.game.soundUtil.apply { play(buy, 0.18f) }

                            game.dataStore.updateBalance { it - price }
                            lblBalance.setText(game.dataStore.balance)

                            game.dataStore.updateLVL_4 { true }
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
        addActors(btnLeft)
        btnLeft.setBounds(24f, 1505f, 70f, 148f)

        btnLeft.setOnClickListener {
            animHide(TIME_ANIM) {
                game.navigationManager.navigate(Work_3_Screen::class.java.name)
            }
        }
    }

}