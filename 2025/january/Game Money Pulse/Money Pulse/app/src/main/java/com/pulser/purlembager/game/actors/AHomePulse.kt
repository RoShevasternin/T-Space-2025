package com.pulser.purlembager.game.actors

import com.pulser.purlembager.game.actors.shader.AMaskGroup
import com.pulser.purlembager.game.utils.SizeScaler
import com.pulser.purlembager.game.utils.actor.setBoundsScaled
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen

class AHomePulse(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 1175f)

    private val mask   = AMaskGroup(screen)
    private val aPulse = APulse(screen)

    override fun addActorsOnGroup() {
        color.a = 0.3f
        addAndFillActor(mask)
        mask.addActor(aPulse)
        aPulse.setBoundsScaled(sizeScaler, -227f, -272f, 1616f, 1148f)
    }

}