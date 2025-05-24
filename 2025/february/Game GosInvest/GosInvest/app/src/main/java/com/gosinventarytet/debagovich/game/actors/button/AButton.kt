package com.gosinventarytet.debagovich.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.gosinventarytet.debagovich.game.utils.TextureEmpty
import com.gosinventarytet.debagovich.game.utils.actor.animHide
import com.gosinventarytet.debagovich.game.utils.actor.animShow
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedGroup
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedScreen
import com.gosinventarytet.debagovich.game.utils.gdxGame
import com.gosinventarytet.debagovich.game.utils.region

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
        Type.ToMain -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.to_main_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.to_main_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.to_main_press),
        )
        Type.Ans -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.ans_def),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.ans_press),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.ans_press),
        )
        Type.Random -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.random_press),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.random_def),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.random_def),
        )
        Type.Quiz_01 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.quiz_01_press),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.quiz_01_def),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.quiz_01_def),
        )
        Type.Quiz_02 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.quiz_02_press),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.quiz_02_def),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.quiz_02_def),
        )
        Type.Quiz_03 -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.quiz_03_press),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.quiz_03_def),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.quiz_03_def),
        )
        Type.All -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.all_press),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.all_def),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.all_def),
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
        None, Back, ToMain, Ans, Random, Quiz_01, Quiz_02, Quiz_03, All
    }

}