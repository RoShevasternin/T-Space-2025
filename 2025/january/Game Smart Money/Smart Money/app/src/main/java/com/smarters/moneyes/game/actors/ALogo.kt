package com.smarters.moneyes.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.gdxGame

class ALogo(override val screen: AdvancedScreen): AdvancedGroup() {

    private val l1 = Image(screen.drawerUtil.getTexture(Color.WHITE))
    private val l2 = Image(screen.drawerUtil.getTexture(Color.WHITE))
    private val l3 = Image(screen.drawerUtil.getTexture(Color.WHITE))
    private val l4 = Image(screen.drawerUtil.getTexture(Color.WHITE))

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsLoader.logo))

        addActors(l1, l2, l3, l4)
        l1.apply {
            setBounds(44f, 154f, 42f, 81f)
            addAction(Actions.forever(Actions.sequence(
                Actions.sizeBy(0f, -80f, 0.5f, Interpolation.sineOut),
                Actions.sizeBy(0f, 160f, 0.5f, Interpolation.sineOut),
                Actions.sizeBy(0f, -80f, 0.5f, Interpolation.sineIn),
            )))
        }
        l2.apply {
            setBounds(102f, 154f, 42f, 81f)
            addAction(Actions.forever(Actions.sequence(
                Actions.sizeBy(0f, 110f, 0.7f, Interpolation.sineOut),
                Actions.sizeBy(0f, -190f, 1.2f, Interpolation.sineOut),
                Actions.sizeBy(0f, 80f, 0.5f, Interpolation.sineIn),
            )))
        }
        l3.apply {
            setBounds(161f, 154f, 42f, 81f)
            addAction(Actions.forever(Actions.sequence(
                Actions.sizeBy(0f, -80f, 0.6f, Interpolation.sineOut),
                Actions.sizeBy(0f, 130f, 1.1f, Interpolation.sineOut),
                Actions.sizeBy(0f, -50f, 0.4f, Interpolation.sineIn),
            )))
        }
        l4.apply {
            setBounds(220f, 154f, 42f, 81f)
            addAction(Actions.forever(Actions.sequence(
                Actions.sizeBy(0f, 110f, 0.5f, Interpolation.sineOut),
                Actions.sizeBy(0f, -190f, 1f, Interpolation.sineOut),
                Actions.sizeBy(0f, 80f, 0.7f, Interpolation.sineIn),
            )))
        }
    }

}