package com.cinvestor.crotcevni.game.actors.main

import com.cinvestor.crotcevni.game.actors.ALevel
import com.cinvestor.crotcevni.game.actors.APlusGold
import com.cinvestor.crotcevni.game.actors.button.AButton
import com.cinvestor.crotcevni.game.actors.progress
import com.cinvestor.crotcevni.game.screens.ImproveScreen
import com.cinvestor.crotcevni.game.screens.InvestScreen
import com.cinvestor.crotcevni.game.screens.MenuScreen
import com.cinvestor.crotcevni.game.utils.Block
import com.cinvestor.crotcevni.game.utils.TIME_ANIM_SCREEN
import com.cinvestor.crotcevni.game.utils.actor.PosSize
import com.cinvestor.crotcevni.game.utils.actor.animDelay
import com.cinvestor.crotcevni.game.utils.actor.animHide
import com.cinvestor.crotcevni.game.utils.actor.animShow
import com.cinvestor.crotcevni.game.utils.actor.setBounds
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedMainGroup
import com.cinvestor.crotcevni.game.utils.gdxGame

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val aLevel    = ALevel(screen)
    private val btnWork   = AButton(screen, AButton.Type.Work)
    private val btnInvest = AButton(screen, AButton.Type.Invest)
    private val btnImprov = AButton(screen, AButton.Type.Impr)
    private val btnConfig = AButton(screen, AButton.Type.Conf)
    private val aPlusGold = APlusGold(screen)

    private val level = gdxGame.ds_Level.flow.value

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addALevel()
        addBtnWork()
        addBtns()
        addAPlusGold()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALevel() {
        addActor(aLevel)
        aLevel.setBounds(0f, 947f, 933f, 903f)
    }

    private fun addAPlusGold() {
        addActor(aPlusGold)
        aPlusGold.setBounds(16f, 628f, 900f, 228f)
    }

    private fun addBtnWork() {
        addActor(btnWork)
        btnWork.setBounds(167f, 590f, 598f, 136f)

        btnWork.setOnClickListener {
            if (progress!!.progressPercentFlow.value - level >= 0) {
                progress?.progressPercentFlow?.value -= level

                aPlusGold.startAnim()
                gdxGame.ds_Balance.update { it + 10 }
            }
        }
    }


    private fun addBtns() {
        val listPosSize = listOf(
            PosSize(93f, 336f, 321f, 180f),
            PosSize(533f, 336f, 307f, 180f),
            PosSize(93f, 140f, 747f, 113f),
        )

        val listBlock = listOf(
            {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(InvestScreen::class.java.name, screen::class.java.name)
                }
            },
            {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ImproveScreen::class.java.name, screen::class.java.name)
                }
            },
            {
                gdxGame.activity.showUrl("https://www.google.com/", false)
            },
        )

        listOf(btnInvest, btnImprov, btnConfig).onEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(listPosSize[index])
            btn.setOnClickListener { listBlock[index]() }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}