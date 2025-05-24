package com.vectorvesta.bronfinteh.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.vectorvesta.bronfinteh.game.utils.TextureEmpty
import com.vectorvesta.bronfinteh.game.utils.actor.animHide
import com.vectorvesta.bronfinteh.game.utils.actor.animShow
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedScreen
import com.vectorvesta.bronfinteh.game.utils.gdxGame
import com.vectorvesta.bronfinteh.game.utils.region

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

    private val animShowTime = 0.050f
    private val animHideTime = 0.400f


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
                gdxGame.soundUtil.apply { play(click) }
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

    fun setOnClickListener(block: () -> Unit) {
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
        Type.Back -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.back_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.back_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.back_press),
        )
        Type.Remove -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.remove_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.remove_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.remove_press),
        )
        Type.Calculate -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.calculate_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.calculate_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.calculate_press),
        )
        Type.MORTGAGE -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.MORTGAGE_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.MORTGAGE_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.MORTGAGE_PRESS),
        )
        Type.LOAN -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.LOAN_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.LOAN_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.LOAN_PRESS),
        )
        Type.INVESTMENTS -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.INVESTMENTS_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.INVESTMENTS_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.INVESTMENTS_PRESS),
        )
        Type.DEPOSIT -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.DEPOSIT_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.DEPOSIT_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.DEPOSIT_PRESS),
        )
        Type.LEASING -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.LEASING_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.LEASING_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.LEASING_PRESS),
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
        None, Back, Remove, Calculate,
        MORTGAGE, LOAN, INVESTMENTS, DEPOSIT, LEASING
    }

}