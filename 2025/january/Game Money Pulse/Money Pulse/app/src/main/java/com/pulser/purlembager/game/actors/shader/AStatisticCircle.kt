package com.pulser.purlembager.game.actors.shader

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.disposeAll
import com.pulser.purlembager.game.utils.runGDX


class AStatisticCircle(
    override val screen: AdvancedScreen,
    listColor        : List<Color>,
    var arrPercentage: FloatArray, // Має скласти 100%
): AdvancedGroup() {

    companion object {
        private var vertexShader   = Gdx.files.internal("shader/circleVS.glsl").readString()
        private var fragmentShader = Gdx.files.internal("shader/circleFS.glsl").readString()
    }

    private var shaderProgram: ShaderProgram? = null

    private val arrColor = listColor.convertColorListToFloatArray()

    override fun addActorsOnGroup() {
        runGDX {
            initShaders()

            addAndFillActor(Image(screen.drawerUtil.getRegion(Color.PINK)))
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch == null || shaderProgram == null) return

        batch.flush()

        batch.shader = shaderProgram

        // Передаємо кількість сегментів
        shaderProgram!!.setUniformi("u_segmentCount", arrPercentage.size)

        // Передаємо відсотки
        shaderProgram!!.setUniform1fv("u_percentages", arrPercentage, 0, arrPercentage.size)

        // Передаємо кольори (уже як floatArray)
        shaderProgram!!.setUniform4fv("u_colors", arrColor, 0, arrColor.size)

        super.draw(batch, parentAlpha)

        batch.shader = null

    }

    override fun dispose() {
        disposeAll(
            shaderProgram,
        )
        super.dispose()
    }

    // Logic ------------------------------------------------------------------------

    private fun initShaders() {
        ShaderProgram.pedantic = false
        shaderProgram = ShaderProgram(vertexShader, fragmentShader)

        if (shaderProgram?.isCompiled == false) {
            throw IllegalStateException("shader compilation failed:\n" + shaderProgram?.log)
        }
    }

    private fun List<Color>.convertColorListToFloatArray(): FloatArray {
        // Створюємо масив довжиною в 4 * кількість кольорів
        val floatArray = FloatArray(this.size * 4)

        // Заповнюємо масив значеннями кольорів
        this.onEachIndexed { index, color ->
            floatArray[index * 4]     = color.r
            floatArray[index * 4 + 1] = color.g
            floatArray[index * 4 + 2] = color.b
            floatArray[index * 4 + 3] = color.a
        }

        return floatArray
    }

}