package com.axmeron.investnaveratep.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.axmeron.investnaveratep.game.actors.shader.AMaskGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame

class ALogo(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgLogo = Image(gdxGame.assetsLoader.logo)
    private val mask    = AMaskGroup(screen, gdxGame.assetsLoader.mask_logo)
    private val imgGear = Image(gdxGame.assetsLoader.gear)

    override fun addActorsOnGroup() {
        addAndFillActor(imgLogo)
        addMask()
        mask.addGear()
    }

    // Actors ---------------------------------------------------

    private fun addMask() {
        addActor(mask)
        mask.setBounds(40f, 38f, 100f, 107f)
    }

    private fun AdvancedGroup.addGear() {
        addActor(imgGear)
        imgGear.setBounds(0f, 1f, 99f, 99f)

        imgGear.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(-360f, 3f)))
        }
    }

}