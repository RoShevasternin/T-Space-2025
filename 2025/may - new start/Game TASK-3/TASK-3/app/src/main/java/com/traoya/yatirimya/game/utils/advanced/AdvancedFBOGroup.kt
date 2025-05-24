package com.traoya.yatirimya.game.utils.advanced

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Disposable
import kotlin.ranges.until

abstract class AdvancedFBOGroup : AdvancedGroup() {

    protected val tmpMatrix     : Matrix4 = Matrix4()
    protected val identityMatrix: Matrix4 = Matrix4().idt()

    /** Викликається всередині FBO */
    protected open fun drawChildrenToFbo(batch: Batch, parentAlpha: Float) {
        children.begin()
        for (i in 0 until children.size) {
            val child = children[i]
            if (child.isVisible) child.draw(batch, parentAlpha)
        }
        children.end()
    }

}