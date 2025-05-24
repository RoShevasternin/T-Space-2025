package com.pulser.purlembager.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.actors.shader.AMaskGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class ALogo(override val screen: AdvancedScreen): AdvancedGroup() {

    private val gear     = Image(gdxGame.assetsLoader.gear)
    private val progress = Image(gdxGame.assetsLoader.progress)
    private val mask     = AMaskGroup(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(gear)
        addActor(mask)

        mask.apply {
            setBounds(0f, 115f, 235f, 107f)
            addActor(progress)
            progress.setBounds(0f, -115f, this@ALogo.width, this@ALogo.height)
        }

        progress.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -110f, 0.5f, Interpolation.sineIn),
            Actions.moveBy(0f, 110f, 0.5f, Interpolation.pow2),
        )))
    }

}