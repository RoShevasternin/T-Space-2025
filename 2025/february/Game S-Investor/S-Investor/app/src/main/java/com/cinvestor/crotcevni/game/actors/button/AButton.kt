package com.cinvestor.crotcevni.game.actors.button

import com.cinvestor.crotcevni.game.manager.util.SoundUtil
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.cinvestor.crotcevni.game.utils.TextureEmpty
import com.cinvestor.crotcevni.game.utils.actor.animHide
import com.cinvestor.crotcevni.game.utils.actor.animShow
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedGroup
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedScreen
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.cinvestor.crotcevni.game.utils.region

open class AButton(
    override val screen: AdvancedScreen,
    type: Type
) : AdvancedGroup() {

    private val defaultImage  = Image(getStyleByType(type).default)
    private val pressedImage  = Image(getStyleByType(type).pressed).also { it.color.a = 0f }
    private val disabledImage = Image(getStyleByType(type).disabled).also { it.color.a = 0f }

    private var onClickBlock: () -> Unit = { }

    var touchDownBlock   : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchDraggedBlock: AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchUpBlock     : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }

    private var area: Actor? = null

    private var sound: SoundUtil.AdvancedSound? = null

    private val animShowTime = 0.050f
    private val animHideTime = 0.0750f


    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
    }


    private fun getActors() = listOf<Actor>(
        defaultImage,
        pressedImage,
        disabledImage,
    )


    private fun getListener() = object : InputListener() {
        var isEnter     = false
        var isEnterArea = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDownBlock(x, y)

            touchDragged(event, x, y, pointer)

            event?.stop()
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            touchDraggedBlock(x, y)

            isEnter = x in 0f..width && y in 0f..height
            area?.let { isEnterArea = x in 0f..it.width && y in 0f..it.height }

            if (isEnter || isEnterArea) press() else unpress()
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchUpBlock(x, y)

            if (isEnter || isEnterArea) {
                unpress()
                sound?.let { gdxGame.soundUtil.apply { play(it) } }
                onClickBlock()
            }
        }
    }

    fun press() {
        defaultImage.clearActions()
        pressedImage.clearActions()

        defaultImage.animHide(animHideTime)
        pressedImage.animShow(animShowTime)
    }

    fun unpress() {
        defaultImage.clearActions()
        pressedImage.clearActions()

        defaultImage.animShow(animShowTime)
        pressedImage.animHide(animHideTime)
    }

    fun disable(useDisabledStyle: Boolean = true) {
        touchable = Touchable.disabled

        if (useDisabledStyle) {
            defaultImage.clearActions()
            pressedImage.clearActions()
            disabledImage.clearActions()

            defaultImage.animHide()
            pressedImage.animHide()
            disabledImage.animShow()
        }

    }

    fun enable() {
        touchable = Touchable.enabled

        defaultImage.clearActions()
        pressedImage.clearActions()
        disabledImage.clearActions()

        defaultImage.animShow()
        pressedImage.animHide()
        disabledImage.animHide()

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

    fun setOnClickListener(sound: SoundUtil.AdvancedSound? = gdxGame.soundUtil.click, block: () -> Unit) {
        this.sound   = sound
        onClickBlock = block
    }

    fun addArea(actor: Actor) {
        area = actor
        actor.addListener(getListener())
    }

    private fun getStyleByType(type: Type) = when(type) {
        Type.None -> AButtonStyle(
            default = TextureRegionDrawable(TextureEmpty.region),
            pressed = TextureRegionDrawable(TextureEmpty.region),
            disabled = TextureRegionDrawable(TextureEmpty.region),
        )
        Type.Work -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.work_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.work_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.work_press),
        )
        Type.Invest -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.invest_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.invest_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.invest_press),
        )
        Type.Impr -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.impr_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.impr_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.impr_press),
        )
        Type.Conf -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.conf_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.conf_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.conf_press),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class AButtonStyle(
        var default: Drawable,
        var pressed: Drawable,
        var disabled: Drawable,
    )

    enum class Type {
        None, Work, Invest, Impr, Conf
    }

}