package com.smarteca.foundsender.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.smarteca.foundsender.game.manager.util.SoundUtil
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.advanced.AdvancedGroup
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen

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

    private val animShowTime = 0.085f
    private val animHideTime = 0.070f


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
        Type.AskNot -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAccess.ask_not_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAccess.ask_not_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAccess.ask_not_press),
        )
        Type.Allow -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAccess.allow_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAccess.allow_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAccess.allow_press),
        )
        Type.Green -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.btn_green_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.btn_green_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.btn_green_press),
        )
        Type.i1 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsDef[0]),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[0]),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[0]),
        )
        Type.i2 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsDef[1]),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[1]),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[1]),
        )
        Type.i3 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsDef[2]),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[2]),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[2]),
        )
        Type.i4 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsDef[3]),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[3]),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[3]),
        )
        Type.i5 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsDef[4]),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[4]),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.listMenuItemsPress[4]),
        )
        Type.Dialog -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.dialogs_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.dialogs_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.dialogs_press),
        )
        Type.Reset -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.reset_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.reset_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.reset_press),
        )
        Type.Savings -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.savings_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.savings_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.savings_press),
        )
        Type.Delete -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.delete_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.delete_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.delete_press),
        )
        Type.Test_Simple -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.test_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.test_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.test_press),
        )
        Type.Test_Progress -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.test_with_progress_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.test_with_progress_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.test_with_progress_press),
        )
        Type.AllTest -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.all_test_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.all_test_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.all_test_press),
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
        None, AskNot, Allow, Green, Dialog, Reset,
        Savings, Delete, Test_Progress, Test_Simple, AllTest,

        i1, i2, i3, i4, i5;

        companion object {
            val listItem = listOf(i1, i2, i3, i4, i5)
        }
    }

}
