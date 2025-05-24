package com.lebany.lechebnik.game.actors.dialog

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.lebany.lechebnik.game.actors.Mask
import com.lebany.lechebnik.game.utils.TIME_ANIM
import com.lebany.lechebnik.game.utils.WIDTH_UI
import com.lebany.lechebnik.game.utils.actor.animHide
import com.lebany.lechebnik.game.utils.actor.setOnClickListener
import com.lebany.lechebnik.game.utils.advanced.AdvancedGroup
import com.lebany.lechebnik.game.utils.advanced.AdvancedScreen
import com.lebany.lechebnik.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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