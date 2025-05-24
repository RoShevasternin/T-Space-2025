package com.borubashka.arsemajeg.game.actors.shader

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.ScreenUtils
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedFBOGroup
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedScreen
import com.borubashka.arsemajeg.game.utils.disposeAll

class AMaskGroup(
    override val screen: AdvancedScreen,
    private val maskTexture: Texture? = null
): AdvancedFBOGroup() {

    companion object {
        private var vertexShader   = Gdx.files.internal("shader/defaultVS.glsl").readString()
        private var fragmentShader = Gdx.files.internal("shader/maskFS.glsl").readString()
    }

    private var shaderProgram: ShaderProgram? = null

    private var fboGroup    : FrameBuffer? = null
    private var textureGroup: TextureRegion? = null

    private var camera = OrthographicCamera()

    //private val globalPosition = Vector2()
    //private val tmpVector2     = Vector2(0f, 0f)

    override fun addActorsOnGroup() {
        createShaders()
        createFrameBuffer()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch         == null ||
            shaderProgram == null ||
            fboGroup      == null
        ) return

        batch.end()

        //globalPosition.set(localToStageCoordinates(tmpVector2.set(0f, 0f)))
        //camera.position.set(globalPosition.x + width / 2f, globalPosition.y + height / 2f, 0f)
        //camera.update()

        // draw fboGroup -------------------------------

        //SpriteBatch().setBlendFunction()
        //batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA)
        //batch.setBlendFunction(GL20.GL_ONE_MINUS_DST_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        //batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA, GL20.GL_ONE)

        fboGroup!!.begin()
        ScreenUtils.clear(Color.CLEAR/*PINK.apply { a = 0.5f }*/)
        batch.begin()

        batch.projectionMatrix = camera.combined
        batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE)

        tmpMatrix.set(batch.transformMatrix)
        batch.transformMatrix = identityMatrix

        drawChildrenToFbo(batch, parentAlpha)

        batch.transformMatrix = tmpMatrix

        batch.end()
        fboGroup!!.end()
        stage.viewport.apply()
        batch.color = Color.WHITE

        // draw Result -------------------------------

        batch.begin()

        batch.projectionMatrix = stage.camera.combined
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA)

        if (maskTexture != null) {
            batch.shader = shaderProgram

            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1)
            maskTexture.bind(1)
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
            textureGroup!!.texture.bind(0)

            shaderProgram!!.setUniformi("u_mask", 1)
            shaderProgram!!.setUniformi("u_texture", 0)
        }

        batch.draw(
            textureGroup,
            x, y,
            originX, originY,
            width, height,
            scaleX, scaleY,
            rotation,
        )

        batch.end()
        batch.begin()

        batch.shader = null
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

    }

    override fun dispose() {
        super.dispose()
        disposeAll(
            shaderProgram,
            fboGroup,
        )
    }

    // Logic ------------------------------------------------------------------------

    private fun createShaders() {
        ShaderProgram.pedantic = false
        shaderProgram = ShaderProgram(vertexShader, fragmentShader)

        if (shaderProgram?.isCompiled == false) {
            throw IllegalStateException("shader compilation failed:\n" + shaderProgram?.log)
        }
    }

    private fun createFrameBuffer() {
        camera = OrthographicCamera(width, height)
        camera.position.set(width / 2f, height / 2f, 0f)
        camera.update()

        fboGroup = FrameBuffer(Pixmap.Format.RGBA8888, width.toInt(), height.toInt(), false)

        textureGroup = TextureRegion(fboGroup!!.colorBufferTexture)
        textureGroup!!.flip(false, true)
    }

}