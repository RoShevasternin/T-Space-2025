package com.proccaptald.proffesionalestic.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.proccaptald.proffesionalestic.game.LibGDXGame
import com.proccaptald.proffesionalestic.game.actors.*
import com.proccaptald.proffesionalestic.game.utils.TIME_ANIM
import com.proccaptald.proffesionalestic.game.utils.actor.animHide
import com.proccaptald.proffesionalestic.game.utils.actor.animShow
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedStage
import com.proccaptald.proffesionalestic.game.utils.region
import com.proccaptald.proffesionalestic.game.utils.runGDX
import com.proccaptald.proffesionalestic.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var WINNER_INDEX = 0
            private set
        var WINNER_COIN = 0
            private set
    }

    private val TARGET          = SelectTargetAndPlayersScreen.TARGET
    private val LIST_PERS_INDEX = SelectTargetAndPlayersScreen.LIST_PERS_INDEX

    private val imgPanel    = Image(game.all.CIRCLES)
    private val btnExit     = AButton(this, AButton.Static.Type.Exit)
    private val btnRestart  = AButton(this, AButton.Static.Type.Restart)
    private val btnBrosok   = AButton(this, AButton.Static.Type.Brosok)
    private val imgCube     = Image(game.all.cube)
    private val target      = ATarget(this, TARGET)
    private val listPlayer  = List(LIST_PERS_INDEX.size) { APlayer(this, LIST_PERS_INDEX[it]) }
    private val listPers    = List(LIST_PERS_INDEX.size) { APers(this, game.all.listPers[LIST_PERS_INDEX[it]]) }
    private val listCoin    = mutableListOf<ACoin>()

    // Field
    private val listTargetY    = listOf(1253f, 37f)
    private val listExitX      = listOf(78f, 265f)
    private val listRestartPos = listOf(
        Vector2(453f,1232f),
        Vector2(453f,1232f),
        Vector2(265f,97f),
        Vector2(265f,166f),
    )
    private val listPosPlayer  = listOf(
        listOf(
            Vector2(241f, 83f)
        ),
        listOf(
            Vector2(35f, 83f),
            Vector2(441f, 83f),
        ),
        listOf(
            Vector2(35f, 83f),
            Vector2(441f, 83f),
            Vector2(441f, 1181f),
        ),
        listOf(
            Vector2(35f, 83f),
            Vector2(441f, 83f),
            Vector2(441f, 1181f),
            Vector2(35f, 1181f),
        ),
    )
    private val listPosPers    = listOf(
        listOf(
            Vector2(74f, 1005f)
        ),
        listOf(
            Vector2(106f, 1005f),
            Vector2(43f, 1005f),
        ),
        listOf(
            Vector2(114f, 992f),
            Vector2(74f, 1020f),
            Vector2(37f, 984f),
        ),
        listOf(
            Vector2(86f, 961f),
            Vector2(119f, 1001f),
            Vector2(70f, 1017f),
            Vector2(32f, 981f),
        ),
    )
    private val listZ_IndexPers = listOf(
        listOf(10),
        listOf(10, 9),
        listOf(10, 9, 8),
        listOf(10, 9, 8, 7),
    )
    private val listStepPers    = listOf(
        Vector2(224f,1005f),
        Vector2(374f,1005f),
        Vector2(524f,1005f),

        Vector2(524f,854f),
        Vector2(524f,703f),
        Vector2(524f,552f),
        Vector2(524f,401f),

        Vector2(375f,401f),
        Vector2(226f,401f),
        Vector2(77f,401f),

        Vector2(77f,552f),
        Vector2(77f,703f),
        Vector2(77f,854f),
        Vector2(77f,1005f),
    )

    private val listPosCoin = mutableListOf<Vector2>()

    private var currentPlayerIndex = 0
    private var isFirstAddCoin     = true

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPanel()
        addBtns()
        addImgCube()

        addTarget()
        addListPlayer()
        addListPers()

        addListCoin()
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(28f,342f,600f,751f)
    }

    private fun AdvancedStage.addImgCube() {
        addActor(imgCube)
        imgCube.setBounds(254f,680f,151f,174f)
        imgCube.setOrigin(Align.center)
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnExit, btnRestart, btnBrosok)

        btnExit.apply {
            val nx = if (LIST_PERS_INDEX.size == 4) listExitX.last() else listExitX.first()
            setBounds(nx,1232f,126f,132f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
        btnRestart.apply {
            val pos = listRestartPos[LIST_PERS_INDEX.lastIndex]
            setBounds(pos.x, pos.y,126f,132f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
        btnBrosok.apply {
            setBounds(215f,544f,229f,85f)
            setOnClickListener(null) {
                game.soundUtil.apply { play(bonus, 0.2f) }

                btnBrosok.disable()
                imgCube.addAction(Actions.sequence(
                    Actions.rotateBy(1800f,2f, Interpolation.smooth2),
                    Actions.run {
                        coroutine?.launch { handlerBrosok() }
                    }
                ))
            }
        }
    }

    private fun AdvancedStage.addTarget() {
        addActor(target)
        val ny = if (LIST_PERS_INDEX.size == 4) listTargetY.last() else listTargetY.first()
        target.setBounds(277f, ny,103f,92f)
    }

    private fun AdvancedStage.addListPlayer() {
        val listPos = listPosPlayer[listPlayer.lastIndex]
        listPlayer.first().current()
        listPlayer.onEachIndexed { index, aPlayer ->
            addActor(aPlayer)
            val pos = listPos[index]
            aPlayer.setBounds(pos.x, pos.y,103f,92f)
        }
    }

    private fun AdvancedStage.addListPers() {
        val listPos = listPosPers[listPers.lastIndex]
        val listZ   = listZ_IndexPers[listPers.lastIndex]
        listPers.onEachIndexed { index, aPers ->
            addActor(aPers)
            val pos = listPos[index]
            aPers.setBounds(pos.x, pos.y,56f,176f)

            aPers.zIndex = listZ[index]
        }
    }

    private fun AdvancedStage.addListCoin() {
        fun addCoins() {
            val coinCount = (3..10).random()
            repeat(coinCount) { listCoin.add(ACoin(this@GameScreen)) }

            log("coinCount = $coinCount | size: ${listCoin.size}")

            listStepPers.take(listStepPers.size-1).shuffled().take(coinCount).also {
                listPosCoin.clear()
                listPosCoin.addAll(it)
            }

            listCoin.onEachIndexed { index, coin ->
                addActor(coin)
                coin.setBounds(listPosCoin[index].x - 16, listPosCoin[index].y + 4, 91f,36f)
            }
        }

        if (isFirstAddCoin) {
            isFirstAddCoin = false
            addCoins()
        } else {
            listCoin.onEachIndexed { index, coin ->
                coin.addAction(Actions.sequence(
                    Actions.fadeOut(0.25f),
                    Actions.run {
                        if (index == listCoin.lastIndex) {
                            listCoin.clear()
                            addCoins()
                        }
                    },
                    Actions.removeActor(),
                ))
            }
        }
    }

    // Logic ---------------------------------------------------------

    private suspend fun handlerBrosok() {
        val randomStepCount = (1..3).random()
        val pers = listPers[currentPlayerIndex]

        repeat(randomStepCount) {
            pers.step += 1
            if (pers.step > listStepPers.size) pers.step = 1

            val pos = listStepPers[pers.step-1]
            runGDX {
                game.soundUtil.apply { play(step, 0.2f) }
                pers.addAction(Actions.moveTo(pos.x, pos.y, 0.4f, Interpolation.sine))
            }
            delay(500)

            runGDX {
                if (listPosCoin.contains(pos)) {
                    game.soundUtil.apply { play(this.coin, 0.22f) }

                    val coin = listCoin[listPosCoin.indexOf(pos)]
                    listPlayer[currentPlayerIndex].coins += coin.coin
                    coin.animHide(0.25f)

                    if (listPlayer[currentPlayerIndex].coins >= TARGET) {
                        WINNER_COIN = listPlayer[currentPlayerIndex].coins
                        WINNER_INDEX = LIST_PERS_INDEX[currentPlayerIndex]

                        stageUI.root.animHide(TIME_ANIM) {
                            game.navigationManager.navigate(WinnerScreen::class.java.name)
                        }
                    }
                }
            }
        }

        nextPlayer()
    }

    private fun nextPlayer() {
        listPlayer[currentPlayerIndex].uncurrent {
            currentPlayerIndex++
            if (currentPlayerIndex > listPlayer.lastIndex) currentPlayerIndex = 0

            listPlayer[currentPlayerIndex].current()
            listPers[currentPlayerIndex].toFront()

            stageUI.addListCoin()
            btnBrosok.enable()
        }

    }

}