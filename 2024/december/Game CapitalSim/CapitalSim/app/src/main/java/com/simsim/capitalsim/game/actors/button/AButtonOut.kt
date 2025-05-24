package com.simsim.capitalsim.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.simsim.capitalsim.game.utils.TextureEmpty
import com.simsim.capitalsim.game.utils.actor.PosSize
import com.simsim.capitalsim.game.utils.actor.animHide
import com.simsim.capitalsim.game.utils.actor.animShow
import com.simsim.capitalsim.game.utils.actor.setBounds
import com.simsim.capitalsim.game.utils.advanced.AdvancedGroup
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.game.utils.region

open class AButtonOut(
    override val screen: AdvancedScreen,
    val type: Type
) : AdvancedGroup() {

    private val defaultImage  = Image(getStyleByType(type).default)
    private val pressedImage  = Image(getStyleByType(type).pressed).also { it.color.a = 0f }
    private val disabledImage = Image(getStyleByType(type).disabled).also { it.color.a = 0f }

    private var onClickBlock: () -> Unit = { }

    var touchDownBlock   : AButtonOut.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchDraggedBlock: AButtonOut.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchUpBlock     : AButtonOut.(x: Float, y: Float) -> Unit = { _, _ -> }

    private var area: Actor? = null

    private val animShowTime = 0.050f
    private val animHideTime = 0.400f


    override fun addActorsOnGroup() {
        getStyleByType(type).posSize.also {
            getActors().onEach { actor ->
                addActor(actor)
                actor.setBounds(it.x, it.y, it.w, it.h)
            }
        }
        addListener(getListener())
    }


    private fun getActors() = listOf<Actor>(
        defaultImage,
        pressedImage,
        disabledImage,
    )



    private fun getListener() = object : InputListener() {
        var isWithin     = false
        var isWithinArea = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDownBlock(x, y)
            touchDragged(event, x, y, pointer)

            gdxGame.soundUtil.apply { play(click) }

            //event?.stop()
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

    fun setType(type: Type) {
        val style = getStyleByType(type)

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
            default  = TextureRegionDrawable(TextureEmpty.region),
            pressed  = TextureRegionDrawable(TextureEmpty.region),
            disabled = TextureRegionDrawable(TextureEmpty.region),
            posSize  = PosSize(0f, 0f, width, height)
        )
        Type.StartTest -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.start_test_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.start_test_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.start_test_press),
            posSize  = PosSize(-53f, -104f, 1005f, 343f)
        )
        Type.Next -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.next_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.next_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.next_press),
            posSize  = PosSize(-74f, -103f, 961f, 342f)
        )
        Type.p1 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.p1_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.p1_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.p1_press),
            posSize  = PosSize(-74f, -103f, 1047f, 342f)
        )
        Type.p2 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.p2_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.p2_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.p2_press),
            posSize  = PosSize(-74f, -103f, 1047f, 342f)
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class AButtonStyle(
        var default: Drawable,
        var pressed: Drawable,
        var disabled: Drawable,
        var posSize: PosSize,
    )

    enum class Type {
        None, StartTest, Next, p1, p2
    }

}