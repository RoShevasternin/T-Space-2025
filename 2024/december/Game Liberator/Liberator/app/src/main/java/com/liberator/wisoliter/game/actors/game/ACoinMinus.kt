package com.liberator.wisoliter.game.actors.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.liberator.wisoliter.game.utils.actor.disable
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame
import kotlin.random.Random

class ACoinMinus(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listImgCoin = List(50) { Image(gdxGame.assetsAll.minus10) }

    private val listPos = listOf(
        Vector2(0f, 0f),
        Vector2(124f, 109f),
        Vector2(323f, 150f),
        Vector2(509f, 109f),
        Vector2(639f, 1f),
    )
    private val randomX get() = (0..639).random().toFloat()
    private val randomY get() = (67..150).random().toFloat()

    override fun addActorsOnGroup() {
        disable()
        addItems()
    }

    private fun addItems() {
        listImgCoin.onEach { imgCoin ->
            addActor(imgCoin)
            imgCoin.setBounds(5000f, 5000f, 103f, 41f)
        }
    }

    private var currentIndex = 0
    private var pos          = Vector2()
    private var tmpVector    = Vector2()

    fun animMinus() {
        if (currentIndex + 1 > listImgCoin.lastIndex) currentIndex = 0

        pos = if (Random.nextBoolean()) listPos.random() else tmpVector.set(randomX, randomY)

        listImgCoin[currentIndex++].also { coin ->
            coin.addAction(Actions.sequence(
                Actions.alpha(1f),
                Actions.moveTo(320f, 0f),

                Actions.moveTo(pos.x, pos.y, 0.35f),

                Actions.fadeOut(0.25f),
                Actions.moveTo(5000f, 5000f),
            ))
        }
    }

}