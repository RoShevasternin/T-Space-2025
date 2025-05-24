package com.startegfin.financester.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.startegfin.financester.game.utils.actor.setOnClickListener
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen

class ARashodItemGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgPanel  = Image(screen.game.all.MINUS_ITEMS_PANEL)
    private val listItems = List(9) { Actor() }

    var blockSelect: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        var nx = 40f
        var ny = 521f
        listItems.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(nx, ny,206f,189f)
            nx += 26f + 206f
            if (index.inc() % 3 == 0) {
                nx = 40f
                ny -= 41f + 189f
            }
            actor.setOnClickListener(screen.game.soundUtil) { blockSelect(index) }
        }
    }

}