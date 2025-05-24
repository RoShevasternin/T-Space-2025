package com.gpecspertisa.sollomka.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gpecspertisa.sollomka.game.utils.GColor
import com.gpecspertisa.sollomka.game.utils.TIME_ANIM
import com.gpecspertisa.sollomka.game.utils.actor.animHide
import com.gpecspertisa.sollomka.game.utils.actor.setOnClickListener
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedGroup
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedScreen

class AClosedLevel(override val screen: AdvancedScreen, screenTypeIndex: Int): AdvancedGroup() {

    private val price = listOf(2000, 4000, 6000)[screenTypeIndex-1]

    private val imgBackground = Image(screen.drawerUtil.getRegion(GColor.dark_80))
    private val imgPanel      = Image(screen.game.all.listNextLvl[screenTypeIndex-1])
    private val aOpen         = Actor()
    private val aX            = Actor()

    var blockOpenLvl : (Int) -> Unit = {}
    var blockCloseLvl: () -> Unit = {}

    override fun addActorsOnGroup() {
        screen.topStageBack.addAndFillActor(imgBackground)

        addActors(imgPanel, aOpen, aX)
        imgPanel.setBounds(201f, 825f, 711f, 760f)
        aOpen.apply {
            setBounds(352f, 855f, 409f, 100f)
            setOnClickListener(screen.game.soundUtil) {
                blockOpenLvl(price)
            }
        }
        aX.apply {
            setBounds(796f, 1473f, 89f, 89f)
            setOnClickListener(screen.game.soundUtil) {
                blockCloseLvl()
            }
        }
    }

    fun close() {
        imgBackground.animHide(TIME_ANIM)
        animHide(TIME_ANIM) {
            dispose()
            imgBackground.addAction(Actions.removeActor())
            addAction(Actions.removeActor())
        }
    }

}