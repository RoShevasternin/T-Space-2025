package com.proccaptald.proffesionalestic.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.proccaptald.proffesionalestic.game.LibGDXGame
import com.proccaptald.proffesionalestic.game.actors.AButton
import com.proccaptald.proffesionalestic.game.actors.layout.AVerticalGroup
import com.proccaptald.proffesionalestic.game.actors.panel.APlayerPanel
import com.proccaptald.proffesionalestic.game.actors.panel.ATargetPanel
import com.proccaptald.proffesionalestic.game.utils.TIME_ANIM
import com.proccaptald.proffesionalestic.game.utils.actor.animHide
import com.proccaptald.proffesionalestic.game.utils.actor.animShow
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedStage
import com.proccaptald.proffesionalestic.game.utils.region

class SelectTargetAndPlayersScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var LIST_PERS_INDEX = listOf(0)
            private set

        var TARGET = 0
            private set
    }

    private val btnPlay        = AButton(this, AButton.Static.Type.TO_GAME)
    private val btnAddPlayer   = AButton(this, AButton.Static.Type.ADD_PLAYER)
    private val targetPanel    = ATargetPanel(this)

    private val verticalGroup = AVerticalGroup(this,62f, isWrap = true).apply { setSize(558f,720f) }
    private val scroll        = ScrollPane(verticalGroup)

    // Field

    private var playerCount = 0
    private val listBtnAddPlayerY = listOf(407f, 249f)

    private val listPlayerPanel = mutableListOf<APlayerPanel>()

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addTargetPanel()
        addScroll()
        addBtnAddPlayer()
        addBtnPlay()

        addPlayerPanel()
    }

    private fun AdvancedStage.addBtnAddPlayer() {
        addActors(btnAddPlayer)

        btnAddPlayer.apply {
            setBounds(50f,710f,558f,85f)
            setOnClickListener { handlerAddPlayer() }
        }
    }

    private fun AdvancedStage.addBtnPlay() {
        addActors(btnPlay)

        btnPlay.apply {
            setBounds(50f,80f,558f,125f)
            setOnClickListener {
                TARGET = targetPanel.target
                LIST_PERS_INDEX = listPlayerPanel.map { it.proffesionIndex }

                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name, SelectTargetAndPlayersScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addTargetPanel() {
        addActor(targetPanel)
        targetPanel.setBounds(50f,1160f,558f,175f)
    }

    private fun AdvancedStage.addScroll() {
        addActor(scroll)
        scroll.setBounds(50f,378f,558f,720f)
    }

    // Logic ---------------------------------------------------------------------------

    private fun addPlayerPanel() {
        playerCount++

        val playerPanel = APlayerPanel(this, playerCount)
        listPlayerPanel.add(playerPanel)
        playerPanel.setSize(558f,241f)
        verticalGroup.addActor(playerPanel)
    }

    private fun handlerAddPlayer() {
        if (playerCount < 3) {
            btnAddPlayer.addAction(Actions.moveTo(50f, listBtnAddPlayerY[playerCount-1]))
        }
        if (playerCount < 4) {
            addPlayerPanel()

            if (playerCount == 4) {
                btnAddPlayer.disable()
                btnAddPlayer.addAction(Actions.sequence(
                    Actions.fadeOut(0.3f),
                    Actions.removeActor()
                ))
            }

            stageUI.root.addAction(Actions.sequence(
                Actions.delay(0.3f),
                Actions.run { scroll.scrollPercentY = 1f }
            ))
        }
    }

}