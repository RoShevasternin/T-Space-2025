package com.smarters.moneyes.game.actors.scroller.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.smarters.moneyes.game.actors.AScrollPane
import com.smarters.moneyes.game.actors.autoLayout.AHorizontalGroup
import com.smarters.moneyes.game.actors.button.AButton
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen

class AScrollerLevel(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val horizontalGroup = AHorizontalGroup(screen, 70f, paddingLeft = 61f, paddingRight = 61f, isWrapHorizontal = true)
    private val scroll          = AScrollPane(horizontalGroup)
    private val aPoints         = APoints(screen)
    private val levelListA      = ALevelListA(screen)
    private val levelListB      = ALevelListB(screen)

    var blockSelectedLevel: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addScroll()
        addPoints()
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 54f, 1150f, 877f)
        horizontalGroup.addLevelListAB()
    }

    private fun AHorizontalGroup.addLevelListAB() {
        levelListA.setSize(1028f, 777f)
        levelListB.setSize(1028f, 777f)
        addActors(levelListA, levelListB)

        levelListA.blockSelect = { blockSelectedLevel(it) }
        levelListB.blockSelect = { blockSelectedLevel(it) }
    }

    private fun addPoints() {
        addActor(aPoints)
        aPoints.setBounds(534f, 0f, 82f, 25f)
    }

    private var time = 0f
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        time += Gdx.graphics.deltaTime
        if (time >= 0.075) {
            time = 0f

            scroll.scrollPercentX.also { percentX ->
                when (percentX) {
                    in 0f..0.50f -> 0
                    in 0.50f..1f -> 1
                    else                -> 0
                }.also { indexPoint ->
                    aPoints.check(indexPoint) }
            }
        }
    }
}