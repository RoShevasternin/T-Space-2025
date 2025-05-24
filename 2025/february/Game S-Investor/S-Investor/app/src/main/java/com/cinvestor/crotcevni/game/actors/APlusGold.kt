package com.cinvestor.crotcevni.game.actors

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.cinvestor.crotcevni.game.utils.HEIGHT_UI
import com.cinvestor.crotcevni.game.utils.WIDTH_UI
import com.cinvestor.crotcevni.game.utils.actor.disable
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedGroup
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedScreen
import com.cinvestor.crotcevni.game.utils.gdxGame
import kotlin.random.Random

class APlusGold(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listImgGold = List(50) { Image(gdxGame.assetsAll.c) }

    private val listPos = listOf(
        Vector2(0f, 0f),
        Vector2(149f, 129f),
        Vector2(387f, 179f),
        Vector2(611f, 129f),
        Vector2(768f, 0f),
    )
    private val randomX get() = (0..768).random().toFloat()
    private val randomY get() = (0..179).random().toFloat()

    override fun addActorsOnGroup() {
        disable()
        addItems()
    }

    private fun addItems() {
        listImgGold.onEach { imgCoin ->
            addActor(imgCoin)
            imgCoin.setBounds(WIDTH_UI, HEIGHT_UI, 132f, 50f)
        }
    }

    private var currentIndex = 0
    private var pos          = Vector2()
    private var tmpVector    = Vector2()

    fun startAnim() {
        if (currentIndex + 1 > listImgGold.lastIndex) currentIndex = 0

        pos = if (Random.nextBoolean()) listPos.random() else tmpVector.set(randomX, randomY)

        listImgGold[currentIndex++].also { coin ->
            coin.addAction(Actions.sequence(
                Actions.alpha(1f),
                Actions.moveTo(383f, 0f),

                Actions.moveTo(pos.x, pos.y, 0.4f),

                Actions.fadeOut(0.2f),
                Actions.moveTo(WIDTH_UI, HEIGHT_UI),
            ))
        }
    }

}