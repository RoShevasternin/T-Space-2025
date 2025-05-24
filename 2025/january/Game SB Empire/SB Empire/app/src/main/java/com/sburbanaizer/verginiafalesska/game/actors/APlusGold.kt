package com.sburbanaizer.verginiafalesska.game.actors

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sburbanaizer.verginiafalesska.game.utils.HEIGHT_UI
import com.sburbanaizer.verginiafalesska.game.utils.WIDTH_UI
import com.sburbanaizer.verginiafalesska.game.utils.actor.disable
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedGroup
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedScreen
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame
import kotlin.random.Random

class APlusGold(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listImgGold = List(50) { Image(gdxGame.assetsAll.listP10Gold[gdxGame.ds_Level.flow.value.lvlProdaja-1]) }

    private val listPos = listOf(
        Vector2(0f, 3f),
        Vector2(114f, 102f),
        Vector2(297f, 140f),
        Vector2(468f, 102f),
        Vector2(588f, 3f),
    )
    private val randomX get() = (0..588).random().toFloat()
    private val randomY get() = (0..140).random().toFloat()

    override fun addActorsOnGroup() {
        disable()
        addItems()
    }

    private fun addItems() {
        listImgGold.onEach { imgCoin ->
            addActor(imgCoin)
            imgCoin.setBounds(WIDTH_UI, HEIGHT_UI, 93f, 38f)
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
                Actions.moveTo(297f, 1f),

                Actions.moveTo(pos.x, pos.y, 0.4f),

                Actions.fadeOut(0.2f),
                Actions.moveTo(WIDTH_UI, HEIGHT_UI),
            ))
        }
    }

}