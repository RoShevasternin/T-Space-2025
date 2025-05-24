package com.pulser.purlembager.game.actors

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.actor.setPosition
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class AKeyboard(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgMain = Image(gdxGame.assetsAll.numbers)

    private val listPos = listOf(
        Vector2(315f, -20f),
        Vector2(-53f, 715f),
        Vector2(332f, 715f),
        Vector2(726f, 715f),
        Vector2(-37f, 470f),
        Vector2(349f, 470f),
        Vector2(726f, 470f),
        Vector2(-53f, 226f),
        Vector2(335f, 226f),
        Vector2(726f, 225f),
        Vector2(708f, -20f),
    )

    var blockInput: (Int) -> Unit = {}
    var blockDrop = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgMain)

        listPos.onEachIndexed { index, pos ->
            addActor(Actor().also {
                it.setPosition(pos)
                it.setSize(158f, 158f)

                it.setOnClickListener(gdxGame.soundUtil) {
                    if (index == listPos.lastIndex) blockDrop() else blockInput(index)
                }
            })
        }

    }

}