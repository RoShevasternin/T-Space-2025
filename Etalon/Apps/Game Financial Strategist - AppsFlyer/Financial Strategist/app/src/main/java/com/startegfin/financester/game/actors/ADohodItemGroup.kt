package com.startegfin.financester.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.startegfin.financester.game.utils.actor.setOnClickListener
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen

class ADohodItemGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgPanel  = Image(screen.game.all.PLUS_ITEMS_PANEL)
    private val listItems = List(3) { Actor() }

    var blockSelect: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        var nx = 40f
        listItems.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(nx,60f,206f,189f)
            nx += 26f + 206f
            actor.setOnClickListener(screen.game.soundUtil) { blockSelect(index) }
        }
    }

}