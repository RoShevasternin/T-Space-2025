package com.smarteca.foundsender.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.StringBuilder
import com.badlogic.gdx.utils.viewport.Viewport
import com.smarteca.foundsender.GdxGame

typealias Acts = Actions

val gdxGame: GdxGame get() = Gdx.app.applicationListener as GdxGame

val Texture.region: TextureRegion get() = TextureRegion(this)
val Float.toMS: Long get() = (this * 1000).toLong()
val Viewport.zeroScreenVector: Vector2 get() = project(Vector2(0f, 0f))
val TextureEmpty get() = Texture(1, 1, Pixmap.Format.Alpha)

fun disposeAll(vararg disposable: Disposable?) {
    disposable.forEach { it?.dispose() }
}

fun currentTimeMinus(time: Long) = System.currentTimeMillis().minus(time)

fun Collection<Disposable>.disposeAll() {
    onEach { it.dispose() }
}

fun InputMultiplexer.addProcessors(vararg processor: InputProcessor) {
    processor.onEach { addProcessor(it) }
}

fun runGDX(block: Runnable) {
    Gdx.app.postRunnable { block.run() }
}

fun log(text: String) {
    Gdx.app.log("VLAD", text)
}

fun TextureRegion.toTexture(): Texture {
    val pixmap = Pixmap(regionWidth, regionHeight, Pixmap.Format.RGBA8888)

    if (texture.textureData.isPrepared.not()) texture.textureData.prepare()
    val texturePixmap = texture.textureData.consumePixmap()

    pixmap.drawPixmap(texturePixmap, 0, 0, regionX, regionY, regionWidth, regionHeight)

    val newTexture = Texture(pixmap)

    texturePixmap.dispose()
    pixmap.dispose()

    return newTexture
}

fun Float.divOr0(num: Float): Float = if (num != 0f) this / num else 0f

fun Vector2.divOr0(scalar: Float): Vector2 {
    x = x.divOr0(scalar)
    y = y.divOr0(scalar)
    return this
}

fun Vector2.divOr0(scalar: Vector2): Vector2 {
    x = x.divOr0(scalar.x)
    y = y.divOr0(scalar.y)
    return this
}

private val stringBuilder = StringBuilder()

fun Number.toSeparate(): String {
    stringBuilder.clear()
    stringBuilder.append(this.toString())

    when(stringBuilder.length) {
        4 -> stringBuilder.insert(1, ' ')
        5 -> stringBuilder.insert(2, ' ')
        6 -> stringBuilder.insert(3, ' ')
        7 -> stringBuilder.insert(1, ' ').insert(5, ' ')
        8 -> stringBuilder.insert(2, ' ').insert(6, ' ')
    }

    return stringBuilder.toString()
}

fun String.toSeparate(): String {
    stringBuilder.clear()
    stringBuilder.append(this)

    when(stringBuilder.length) {
        4 -> stringBuilder.insert(1, ' ')
        5 -> stringBuilder.insert(2, ' ')
        6 -> stringBuilder.insert(3, ' ')
        7 -> stringBuilder.insert(1, ' ').insert(5, ' ')
        8 -> stringBuilder.insert(2, ' ').insert(6, ' ')
    }

    return stringBuilder.toString()
}

fun String.isNumeric(): Boolean {
    return this.matches(Regex("\\d+"))
}

fun String.isNumTake(count: Int): Int {
    return if (isNumeric()) take(count).toInt() else 0
}
