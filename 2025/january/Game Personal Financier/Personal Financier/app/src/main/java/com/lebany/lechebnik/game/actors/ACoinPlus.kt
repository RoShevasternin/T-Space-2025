package com.lebany.lechebnik.game.actors

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.lebany.lechebnik.game.utils.HEIGHT_UI
import com.lebany.lechebnik.game.utils.WIDTH_UI
import com.lebany.lechebnik.game.utils.advanced.AdvancedGroup
import com.lebany.lechebnik.game.utils.advanced.AdvancedScreen
import com.lebany.lechebnik.game.utils.disable
import com.lebany.lechebnik.util.log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.random.Random

class ACoinPlus(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listImgCoin = List(50) { Image(screen.game.all.plus_coin) }

    private val listPos = listOf(
        Vector2(0f, 0f),
        Vector2(178f, 172f),
        Vector2(463f, 231f),
        Vector2(730f, 172f),
        Vector2(917f, 0f),
    )
    private val randomX get() = (0..917).random().toFloat()
    private val randomY get() = (172..231).random().toFloat()

    override fun addActorsOnGroup() {
        disable()
        addItems()
    }

    private fun addItems() {
        listImgCoin.onEach { imgCoin ->
            addActor(imgCoin)
            imgCoin.setBounds(WIDTH_UI, HEIGHT_UI, 149f, 62f)
        }
    }

    private var currentIndex = 0
    private var pos          = Vector2()
    private var tmpVector    = Vector2()

    fun startAnim() {
        if (currentIndex + 1 > listImgCoin.lastIndex) currentIndex = 0

        pos = if (Random.nextBoolean()) listPos.random() else tmpVector.set(randomX, randomY)

        listImgCoin[currentIndex++].also { coin ->
            coin.addAction(Actions.sequence(
                Actions.alpha(1f),
                Actions.moveTo(458f, 0f),

                Actions.moveTo(pos.x, pos.y, 0.4f),

                Actions.fadeOut(0.2f),
                Actions.moveTo(WIDTH_UI, HEIGHT_UI),
            ))
        }
    }

}