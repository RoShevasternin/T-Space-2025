package com.progressmas.samsuster.game.actors.dialog

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.progressmas.samsuster.game.utils.TIME_ANIM
import com.progressmas.samsuster.game.utils.actor.animHide
import com.progressmas.samsuster.game.utils.actor.setOnClickListener
import com.progressmas.samsuster.game.utils.advanced.AdvancedGroup
import com.progressmas.samsuster.game.utils.advanced.AdvancedScreen

class ADialogInvest(
    override val screen: AdvancedScreen,
    screenTypeIndex: Int,
    investmentIndex: Int,
): AdvancedGroup() {

    private val imgBackground = Image(screen.game.all.listAllDialogInvest[investmentIndex][screenTypeIndex])

    override fun addActorsOnGroup() {
        color.a = 0f
        addAndFillActor(imgBackground)
        val aX = Actor()
        addActor(aX)
        aX.setBounds(591f, 494f, 89f, 89f)
        aX.setOnClickListener(screen.game.soundUtil) {
            animHide(TIME_ANIM) {
                dispose()
                addAction(Actions.removeActor())
            }
        }
    }

}