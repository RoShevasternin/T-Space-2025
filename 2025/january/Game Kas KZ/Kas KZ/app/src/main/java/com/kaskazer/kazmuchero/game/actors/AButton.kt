package com.kaskazer.kazmuchero.game.actors

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kaskazer.kazmuchero.game.actors.AButton.Type.*
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedGroup
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen

open class AButton(
    override val screen: AdvancedScreen,
    type: Type? = null
) : AdvancedGroup() {

    private val defaultImage  by lazy {  if (type != null) Image(getStyleByType(type).default)  else Image() }
    private val pressedImage  by lazy { (if (type != null) Image(getStyleByType(type).pressed)  else Image()).apply { isVisible = false } }
    private val disabledImage by lazy { (if (type != null) Image(getStyleByType(type).disabled) else Image()).apply { isVisible = false } }

    private var onClickBlock    : () -> Unit = { }

    var touchDownBlock   : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchDraggedBlock: AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchUpBlock     : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }

    private var area: Actor? = null


    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
    }


    private fun getActors() = listOf<Actor>(
        defaultImage,
        pressedImage,
        disabledImage,
    )


    private var sound: Sound? = null

    private fun getListener() = object : InputListener() {
        var isWithin     = false
        var isWithinArea = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDownBlock(x, y)
            touchDragged(event, x, y, pointer)

            sound?.let { screen.game.soundUtil.apply { play(it) } }
            event?.stop()
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            touchDraggedBlock(x, y)

            isWithin = x in 0f..width && y in 0f..height
            area?.let { isWithinArea = x in 0f..it.width && y in 0f..it.height }


            if (isWithin || isWithinArea) press() else unpress()

        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchUpBlock(x, y)

            if (isWithin || isWithinArea) {
                unpress()
                onClickBlock()
            }

        }
    }

    fun press() {
        defaultImage.isVisible = false
        pressedImage.isVisible = true
    }

    fun unpress() {
        defaultImage.isVisible = true
        pressedImage.isVisible = false
    }

    fun disable(useDisabledStyle: Boolean = true) {
        touchable = Touchable.disabled

        if (useDisabledStyle) {
            defaultImage.isVisible  = false
            pressedImage.isVisible  = false
            disabledImage.isVisible = true
        }

    }

    fun enable() {
        touchable = Touchable.enabled

        defaultImage.isVisible  = true
        pressedImage.isVisible  = false
        disabledImage.isVisible = false

    }

    fun pressAndDisable(useDisabledStyle: Boolean = false) {
        press()
        disable(useDisabledStyle)
    }

    fun unpressAndEnable() {
        unpress()
        enable()
    }

    fun setStyle(style: AButtonStyle) {
        defaultImage.drawable  = style.default
        pressedImage.drawable  = style.pressed
        disabledImage.drawable = style.disabled
    }

    fun setOnClickListener(sound: Sound? = screen.game.soundUtil.click, block: () -> Unit) {
        this.sound = sound
        onClickBlock = block
    }

    fun setArea(actor: Actor, useOriginal: Boolean = true) {
        area = actor
        actor.addListener(getListener())
        if (useOriginal.not()) clearListeners()
    }

    private fun getStyleByType(type: Type) = when(type) {
        Hello -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.btn_hello_def),
            pressed  = TextureRegionDrawable(screen.game.all.btn_hello_press),
            disabled = TextureRegionDrawable(screen.game.all.btn_hello_press),
        )
        Dukan -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.dukan_def),
            pressed  = TextureRegionDrawable(screen.game.all.dukan_press),
            disabled = TextureRegionDrawable(screen.game.all.dukan_press),
        )
        GazSatmak -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.gaz_satmak_def),
            pressed  = TextureRegionDrawable(screen.game.all.gaz_satmak_press),
            disabled = TextureRegionDrawable(screen.game.all.gaz_satmak_press),
        )
        GeriGel -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.gari_gel_def),
            pressed  = TextureRegionDrawable(screen.game.all.gari_gel_press),
            disabled = TextureRegionDrawable(screen.game.all.gari_gel_press),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class AButtonStyle(
        val default: Drawable,
        val pressed: Drawable,
        val disabled: Drawable? = null,
    )

    enum class Type {
        Hello, Dukan, GazSatmak, GeriGel
    }

}