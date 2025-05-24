package com.romander.navfenixgater.game.actors.button

import com.romander.navfenixgater.game.manager.util.SoundUtil
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.romander.navfenixgater.game.utils.TextureEmpty
import com.romander.navfenixgater.game.utils.actor.animHide
import com.romander.navfenixgater.game.utils.actor.animShow
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.game.utils.region

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
        Type.Save -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.save_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.save_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.save_press),
        )
        Type.Remove -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.remove_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.remove_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.remove_press),
        )
        Type.New -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.ras_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.ras_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.ras_press),
        )
        Type.Glav -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.golovna_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.golovna_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.golovna_press),
        )
        Type.Calc -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.calcul_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.calcul_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.calcul_press),
        )
        Type.Hist -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.history_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.history_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.history_press),
        )
        Type.Reset -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.restart_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.restart_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.restart_press),
        )
        Type.Rasss -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.rasss_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.rasss_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.rasss_press),
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
        None, Save, Remove, New, Glav, Calc, Hist, Reset, Rasss
    }

}