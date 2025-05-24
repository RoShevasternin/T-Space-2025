package com.frusune.abvger.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class ACheckBox(
    override val screen: AdvancedScreen,
    type: Static.Type? = null,
) : AdvancedGroup() {

    private val defaultImage by lazy {  if (type != null) Image(getStyleByType(type).default) else Image() }
    private val checkImage   by lazy { (if (type != null) Image(getStyleByType(type).checked) else Image()).apply { isVisible = false } }

    private var onCheckBlock: (Boolean) -> Unit = { }

    private var isInvokeCheckBlock: Boolean = true
    var checkBoxGroup: ACheckBoxGroup? = null

    val checkFlow = MutableStateFlow(false)

    var isSound = true

    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
        asyncCollectCheckFlow()
    }

    private fun getActors() = listOf<Actor>(
        defaultImage,
        checkImage,
    )

    private fun asyncCollectCheckFlow() {
        coroutine?.launch { checkFlow.collect { isCheck -> if (isInvokeCheckBlock) onCheckBlock(isCheck) } }
    }

    open fun getListener() = object : InputListener() {
        var isWithin = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            if (isSound) screen.game.soundUtil.apply { play(click, 0.25f) }
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (isWithin) {
                if (checkBoxGroup != null) {
                    if (checkFlow.value.not()) check()
                } else {
                    if (checkFlow.value) uncheck() else check()
                }
            }
        }
    }



    fun check(isInvokeCheckBlock: Boolean = true) {
        this.isInvokeCheckBlock = isInvokeCheckBlock

        checkBoxGroup?.let {
            it.currentCheckedCheckBox?.uncheck()
            it.currentCheckedCheckBox = this
        }

        defaultImage.isVisible = false
        checkImage.isVisible   = true

        checkFlow.value = true
    }

    fun uncheck(isInvokeCheckBlock: Boolean = true) {
        this.isInvokeCheckBlock = isInvokeCheckBlock

        defaultImage.isVisible = true
        checkImage.isVisible   = false

        checkFlow.value = false
    }

    fun checkAndDisable() {
        check()
        disable()
    }

    fun uncheckAndEnabled() {
        uncheck()
        enable()
    }

    fun enable() {
        touchable = Touchable.enabled
    }

    fun disable() {
        touchable = Touchable.disabled
    }

    fun setStyle(style: Static.ACheckBoxStyle) {
        defaultImage.drawable = TextureRegionDrawable(style.default)
        checkImage.drawable   = TextureRegionDrawable(style.checked)
    }

    fun setOnCheckListener(block: (Boolean) -> Unit) {
        onCheckBlock = block
    }

    fun getStyleByType(type: Static.Type) = when(type) {
        Static.Type.POINT -> Static.ACheckBoxStyle(
            default = screen.game.all.circle_mini,
            checked = screen.game.all.circle_big,
        )
        Static.Type.HOME -> Static.ACheckBoxStyle(
            default = screen.game.all.home_def,
            checked = screen.game.all.home_check,
        )
        Static.Type.INVEST -> Static.ACheckBoxStyle(
            default = screen.game.all.invest_def,
            checked = screen.game.all.invest_check,
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    object Static {
        data class ACheckBoxStyle(
            val default: TextureRegion,
            val checked: TextureRegion,
        )

        enum class Type {
            POINT, HOME, INVEST
        }

    }

}