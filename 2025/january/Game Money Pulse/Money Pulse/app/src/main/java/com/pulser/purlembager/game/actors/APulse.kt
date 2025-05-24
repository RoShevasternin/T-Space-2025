package com.pulser.purlembager.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.actors.shader.AMaskGroup
import com.pulser.purlembager.game.utils.SizeScaler
import com.pulser.purlembager.game.utils.actor.setBoundsScaled
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class APulse(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 1616f)

    private val a = Image(gdxGame.assetsLoader.a)
    private val b = Image(gdxGame.assetsLoader.b)

    override fun addActorsOnGroup() {
        addActors(b, a)

        val value = 50f.scaled

        b.apply {
            setBoundsScaled(sizeScaler, 0f, 50f, 1492f, 860f)
            addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(value, -value, 1f, Interpolation.sine),
                Actions.moveBy(-value, value, 1f, Interpolation.sine),
            )))
        }
        a.apply {
            setBoundsScaled(sizeScaler, 122f, 0f, 1494f, 1148f)
            addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(-value, value, 1f, Interpolation.sine),
                Actions.moveBy(value, -value, 1f, Interpolation.sine),
            )))
        }

    }

}