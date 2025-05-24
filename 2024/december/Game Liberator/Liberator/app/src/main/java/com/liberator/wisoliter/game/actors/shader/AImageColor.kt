package com.liberator.wisoliter.game.actors.shader

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.disposeAll

class AImageColor(
    override val screen: AdvancedScreen,
    val texture: Texture,
): AdvancedGroup() {

    companion object {
        private var vertexShader   = Gdx.files.internal("shader/colorVS.glsl").readString()
        private var fragmentShader = Gdx.files.internal("shader/colorFS.glsl").readString()
    }

    private var shaderProgram: ShaderProgram? = null

    var percentage  = 0f // Відсоток зміни кольору (0.0 до 1.0)
    var targetColor = GameColor.cDarkGray

    override fun addActorsOnGroup() {
        addAndFillActor(Image(texture))
        createShaders()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch == null || shaderProgram == null) return

        batch.end()

        // draw Result -------------------------------

        batch.begin()
        batch.projectionMatrix = batch.projectionMatrix
        batch.transformMatrix  = batch.transformMatrix

        batch.shader = shaderProgram
        shaderProgram!!.setUniformf("u_percentage", percentage)
        shaderProgram!!.setUniformf("u_targetColor", targetColor)

        //oldGroupAlpha     = color.a
        //batchResult.color = color.apply { a *= parentAlpha }
        //color.a           = oldGroupAlpha

        super.draw(batch, parentAlpha)

        batch.end()

        batch.begin()
    }

    override fun dispose() {
        super.dispose()
        disposeAll(shaderProgram)
    }

    // Logic ------------------------------------------------------------------------

    private fun createShaders() {
        ShaderProgram.pedantic = false
        shaderProgram = ShaderProgram(vertexShader, fragmentShader)

        if (shaderProgram?.isCompiled == false) {
            throw IllegalStateException("shader compilation failed:\n" + shaderProgram?.log)
        }
    }

}