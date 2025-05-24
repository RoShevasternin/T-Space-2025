package com.liberator.wisoliter.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.liberator.wisoliter.game.utils.SizeScaler
import com.liberator.wisoliter.game.utils.actor.disable
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame

class AGrid(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 904f)

    override fun addActorsOnGroup() {
        disable()
        addAndFillActor(Image(gdxGame.assetsLoader.grid))
    }

    fun animMove() {
        val startX =  (-29f).scaled
        val startY =  (-57f).scaled
        val endX   =  (-128f).scaled
        val endY   =  0f

        addAction(Actions.forever(Actions.sequence(
            Actions.moveTo(startX, startY, 2f),
            Actions.moveTo(endX, endY),
        )))
    }

}