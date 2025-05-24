package com.traoya.yatirimya.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.traoya.yatirimya.game.actors.shader.AMaskGroup
import com.traoya.yatirimya.game.utils.SizeScaler
import com.traoya.yatirimya.game.utils.advanced.AdvancedGroup
import com.traoya.yatirimya.game.utils.advanced.AdvancedScreen
import com.traoya.yatirimya.game.utils.gdxGame
import com.traoya.yatirimya.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 514f)

    private val imgProgress = Image(gdxGame.assetsLoader.red)
    private val mask        = AMaskGroup(screen, gdxGame.assetsLoader.MASKA)

    private var onePercentX = 0f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)

    override fun addActorsOnGroup() {
        //addAndFillActor(Image(gdxGame.assetsAll.bap))
        addMask()

        onePercentX = width / 100f

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x = (percent * onePercentX) - width
                }
            }
        }

        //addListener(inputListener())
    }

    // Actors ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addAndFillActor(imgProgress)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun inputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            progressPercentFlow.value = when {
                x <= 0 -> 0f
                x >= width -> 100f
                else -> x / onePercentX
            }

            event?.stop()
        }
    }

}