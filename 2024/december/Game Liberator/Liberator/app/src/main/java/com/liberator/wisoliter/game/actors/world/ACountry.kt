package com.liberator.wisoliter.game.actors.world

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.ScreenUtils
import com.liberator.wisoliter.game.actors.shader.AImageColor
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.actor.disable
import com.liberator.wisoliter.game.utils.actor.setOnClickListenerByPolygon
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.util.log

class ACountry(
    override val screen: AdvancedScreen,
    val texture: Texture,
    val polygon: Polygon? = null,

    val xp   : Int,
    var uron : Int,
    val indexCountry: Int
) : AdvancedGroup() {

    private val imgCountry = AImageColor(screen, texture)

    // Debug
    private val shapeRendererDebug = ShapeRenderer()
    private var fboDebug     : FrameBuffer?   = null
    private var textureDebug : TextureRegion? = null
    private var cameraDebug = OrthographicCamera()

    var isDebugCountry = false

    // Field

    var blockSelect: (ACountry) -> Unit = {}
    var blockZahvacheno = {}

    override fun addActorsOnGroup() {
        if (polygon != null) {
            polygon.scale(width)
            createFrameBufferDebug()

            //debug()
            //isDebugCountry = true

            imgCountry.percentage  = (uron.toFloat() / xp.toFloat())
            imgCountry.targetColor = if (imgCountry.percentage == 1f) {
                disable()
                GameColor.cDarkGray
            } else GameColor.cRed

            setOnClickListenerByPolygon(gdxGame.soundUtil, polygon, 5) {
                blockSelect(this)
            }
        }

        addImgCountry()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (isDebugCountry.not()) return

        textureDebug?.let {
            batch?.draw(
                it,
                x, y,
                originX, originY,
                width, height,
                scaleX, scaleY,
                rotation
            )
        }

    }

    // Actors -----------------------------------------------------------------------------------

    private fun addImgCountry() {
        addAndFillActor(imgCountry)
        imgCountry.disable()
    }

    // FBO Debug-------------------------------------------------------------------------------------

    private fun createFrameBufferDebug() {
        cameraDebug = OrthographicCamera(width, height)
        cameraDebug.setToOrtho(false, width, height)

        fboDebug = FrameBuffer(Pixmap.Format.RGBA8888, width.toInt(), height.toInt(), false)

        textureDebug = TextureRegion(fboDebug!!.colorBufferTexture)
        textureDebug!!.flip(false, true)

        shapeRendererDebug.projectionMatrix = cameraDebug.combined
        shapeRendererDebug.color = Color.RED

        polygon?.let {
            fboDebug!!.begin()
            ScreenUtils.clear(Color.CLEAR)

            shapeRendererDebug.begin(ShapeRenderer.ShapeType.Line)
            shapeRendererDebug.polygon(polygon.transformedVertices)
            shapeRendererDebug.end()

            fboDebug!!.end()
        }

    }

    // Logic -----------------------------------------------------------------------------------------

    fun atack() {
        if (uron < xp) {
            uron += 10

            gdxGame.ds_CountryUron.update { listUron -> listUron.toMutableList().also { it[indexCountry] = uron } }

            if (uron >= xp) {
                imgCountry.percentage  = 1f
                imgCountry.targetColor = GameColor.cDarkGray
                disable()
                blockZahvacheno()
            } else {
                imgCountry.percentage  = (uron.toFloat() / xp.toFloat())
                imgCountry.targetColor = GameColor.cRed
            }
        }
    }

    fun tutorial() {
        imgCountry.percentage  = 0.99f
        imgCountry.targetColor = GameColor.cRed
    }

}