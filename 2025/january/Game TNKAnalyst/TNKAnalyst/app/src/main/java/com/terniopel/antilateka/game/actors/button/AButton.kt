package com.terniopel.antilateka.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.terniopel.antilateka.game.utils.TextureEmpty
import com.terniopel.antilateka.game.utils.actor.animHide
import com.terniopel.antilateka.game.utils.actor.animShow
import com.terniopel.antilateka.game.utils.advanced.AdvancedGroup
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen
import com.terniopel.antilateka.game.utils.gdxGame
import com.terniopel.antilateka.game.utils.region

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
//        Type.K1 -> AButtonStyle(
//            default  = TextureRegionDrawable(gdxGame.assetsAll.k1_def),
//            pressed  = TextureRegionDrawable(gdxGame.assetsAll.k1_press),
//            disabled = TextureRegionDrawable(gdxGame.assetsAll.k1_press),
//        )
//        Type.K2 -> AButtonStyle(
//            default  = TextureRegionDrawable(gdxGame.assetsAll.k2_def),
//            pressed  = TextureRegionDrawable(gdxGame.assetsAll.k2_press),
//            disabled = TextureRegionDrawable(gdxGame.assetsAll.k2_press),
//        )
//        Type.K3 -> AButtonStyle(
//            default  = TextureRegionDrawable(gdxGame.assetsAll.k3_def),
//            pressed  = TextureRegionDrawable(gdxGame.assetsAll.k3_press),
//            disabled = TextureRegionDrawable(gdxGame.assetsAll.k3_press),
//        )
//        Type.K4 -> AButtonStyle(
//            default  = TextureRegionDrawable(gdxGame.assetsAll.k4_def),
//            pressed  = TextureRegionDrawable(gdxGame.assetsAll.k4_press),
//            disabled = TextureRegionDrawable(gdxGame.assetsAll.k4_press),
//        )
//        Type.StartTest -> AButtonStyle(
//            default  = TextureRegionDrawable(gdxGame.assetsAll.start_test_def),
//            pressed  = TextureRegionDrawable(gdxGame.assetsAll.start_test_press),
//            disabled = TextureRegionDrawable(gdxGame.assetsAll.start_test_press),
//        )
//        Type.Next -> AButtonStyle(
//            default  = TextureRegionDrawable(gdxGame.assetsAll.next_def),
//            pressed  = TextureRegionDrawable(gdxGame.assetsAll.next_press),
//            disabled = TextureRegionDrawable(gdxGame.assetsAll.next_press),
//        )
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
        None, //K1, K2, K3, K4, //StartTest, Next,
    }

}