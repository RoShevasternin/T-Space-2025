package com.terniopel.antilateka.game.actors.checkbox

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.terniopel.antilateka.game.utils.advanced.AdvancedGroup
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen
import com.terniopel.antilateka.game.utils.gdxGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class ACheckBox(
    override val screen: AdvancedScreen,
    type: Type? = null,
) : AdvancedGroup() {

    private val defaultImage by lazy {  if (type != null) Image(getStyleByType(type).default) else Image() }
    private val checkImage   by lazy { (if (type != null) Image(getStyleByType(type).checked) else Image()).apply { isVisible = false } }

    private var onCheckBlock: (Boolean) -> Unit = { }

    private var isInvokeCheckBlock: Boolean = true
    var checkBoxGroup: ACheckBoxGroup? = null

    val checkFlow = MutableStateFlow(false)

    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
        asyncCollectCheckFlow()
    }

    override fun sizeChanged() {
        super.sizeChanged()
        getActors().onEach { it.setSize(width, height) }
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
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (isWithin) {
                gdxGame.soundUtil.apply { play(click) }

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

    fun setStyle(style: ACheckBoxStyle) {
        defaultImage.drawable = style.default
        checkImage.drawable   = style.checked
    }

    fun setOnCheckListener(block: (Boolean) -> Unit) {
        onCheckBlock = block
    }

    fun getStyleByType(type: Type) = when(type) {
        Type.ANSWER -> ACheckBoxStyle(
            default = NinePatchDrawable(gdxGame.assetsAll.ans_def),
            checked = NinePatchDrawable(gdxGame.assetsAll.ans_press),
        )
        Type.K1 -> ACheckBoxStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.k1_def),
            checked  = TextureRegionDrawable(gdxGame.assetsAll.k1_press),
        )
        Type.K2 -> ACheckBoxStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.k2_def),
            checked  = TextureRegionDrawable(gdxGame.assetsAll.k2_press),
        )
        Type.K3 -> ACheckBoxStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.k3_def),
            checked  = TextureRegionDrawable(gdxGame.assetsAll.k3_press),
        )
        Type.K4 -> ACheckBoxStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.k4_def),
            checked  = TextureRegionDrawable(gdxGame.assetsAll.k4_press),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class ACheckBoxStyle(
        val default: Drawable,
        val checked: Drawable,
    )

    enum class Type {
        ANSWER, K1, K2, K3, K4,
    }


}