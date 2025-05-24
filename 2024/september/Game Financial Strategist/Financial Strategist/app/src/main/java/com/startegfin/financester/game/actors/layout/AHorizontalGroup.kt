package com.startegfin.financester.game.actors.layout

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import kotlin.math.max

open class AHorizontalGroup(
    override val screen: AdvancedScreen,
    var gap      : Float = 0f,
    val startGap : Float = 0f,
    val endGap   : Float = 0f,
    val alignmentH: Layout.AlignmentHorizontal = Layout.AlignmentHorizontal.START,
    val alignmentV: Layout.AlignmentVertical   = Layout.AlignmentVertical.BOTTOM,
    val direction : Static.Direction = Static.Direction.RIGHT,
    val isWrapH   : Boolean = false,
    val isWrapV   : Boolean = false,
) : AdvancedGroup() {

    private var nx = 0f

    private var newWidth  = 0f
    private var newHeight = 0f

    private var outsideActor = Actor()

    var outsideBlock: (Actor) -> Unit = {}

    override fun getPrefWidth(): Float {
        if (isWrapH) {
            newWidth = 0f
            children.onEach { newWidth += it.width + gap }

            newWidth -= gap
            newWidth += startGap + endGap

            if (newWidth > width) width = newWidth else newWidth = width
        } else {
            newWidth = width
        }

        return newWidth
    }


    override fun getPrefHeight(): Float {
        if (isWrapV) {
            newHeight = 0f
            children.onEach { newHeight = max(it.height, newHeight) }
            height = newHeight
        } else {
            newHeight = height
        }
        return newHeight

    }

    override fun addActorsOnGroup() {}

    override fun childrenChanged() {
        super.childrenChanged()

        update()
    }

    // Logic ------------------------------------------------------------------------

    fun update() {
        getPrefWidth()
        getPrefHeight()

        placeChildren()
    }

    private fun placeChildren() {
        val childrenList = children.toList()

        when (alignmentH) {
            Layout.AlignmentHorizontal.START -> {
                nx = 0f

                when (direction) {
                    Static.Direction.LEFT -> childrenList.reversed().onEachIndexed { index, a -> a.moveFromSTART(index) }
                    Static.Direction.RIGHT -> childrenList.onEachIndexed { index, a -> a.moveFromSTART(index) }
                }
            }
            Layout.AlignmentHorizontal.CENTER -> {
                val childrenWidth = childrenList.map { it.width }.sum()
                val gapWidth      = (gap * childrenList.count().dec())
                nx = (width / 2) - ((childrenWidth + gapWidth) / 2)

                when (direction) {
                    Static.Direction.LEFT -> childrenList.reversed().onEach { a -> a.moveFromCENTER() }
                    Static.Direction.RIGHT -> childrenList.onEach { a -> a.moveFromCENTER() }
                }
            }
            Layout.AlignmentHorizontal.END -> {
                nx = width

                when (direction) {
                    Static.Direction.LEFT -> childrenList.onEachIndexed { index, a -> a.moveFromEND(index) }
                    Static.Direction.RIGHT -> childrenList.reversed().onEachIndexed { index, a -> a.moveFromEND(index) }
                }
            }
            Layout.AlignmentHorizontal.AUTO -> {
                val childrenWidth = childrenList.map { it.width }.sum()
                val gapWidth      = (width - childrenWidth) / childrenList.count().dec()

                nx = 0f

                when (direction) {
                    Static.Direction.LEFT -> childrenList.reversed().onEach { a -> a.moveFromAUTO(gapWidth) }
                    Static.Direction.RIGHT -> childrenList.onEach { a -> a.moveFromAUTO(gapWidth) }
                }
            }
        }

        when(alignmentV) {
            Layout.AlignmentVertical.BOTTOM -> {
                children.onEach {it.y = 0f }
            }
            Layout.AlignmentVertical.CENTER -> {
                children.onEach { it.y = (height / 2) - (it.height / 2) }
            }
            Layout.AlignmentVertical.TOP -> {
                children.onEach { it.y = height - it.height }
            }
            Layout.AlignmentVertical.AUTO -> {

            }
        }

    }

    private fun Int.gap() = (if (this == 0) startGap else gap)

    private fun Actor.moveFromSTART(index: Int) {
        nx += index.gap()
        ifOutside()
        x = nx
        nx += width
    }

    private fun Actor.moveFromEND(index: Int) {
        nx -= (index.gap() + width)
        ifOutsideFromEND()
        x = nx
    }

    private fun Actor.moveFromCENTER() {
        ifOutside()
        x = nx
        nx += (width + gap)
    }

    private fun Actor.moveFromAUTO(autoGap: Float) {
        x = nx
        nx += (width + autoGap)
    }

    private fun Actor.ifOutside() {
        if ((nx + width) > this@AHorizontalGroup.width) {
            outsideActor = when(direction) {
                Static.Direction.LEFT -> children.last()
                Static.Direction.RIGHT -> this
            }
            outsideBlock(outsideActor)
        }
    }

    private fun Actor.ifOutsideFromEND() {
        if (nx < 0) {
            outsideActor = when(direction) {
                Static.Direction.LEFT -> this
                Static.Direction.RIGHT -> children.last()
            }
            this@AHorizontalGroup.addAction(Actions.sequence(
                Actions.removeActor(outsideActor),
                Actions.run { outsideBlock(outsideActor) }
            ))
        }
    }


    object Static {
        enum class Direction {
            LEFT, RIGHT
        }
    }

}