package com.baleru.gamanecpidec.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.baleru.gamanecpidec.game.actors.checkbox.ACheckBox
import com.baleru.gamanecpidec.game.utils.SizeScaler
import com.baleru.gamanecpidec.game.utils.WIDTH_UI
import com.baleru.gamanecpidec.game.utils.actor.setBoundsScaled
import com.baleru.gamanecpidec.game.utils.actor.setOnClickListener
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedGroup
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedScreen
import com.baleru.gamanecpidec.game.utils.gdxGame

class AMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    val aHistory = ACheckBox(screen, ACheckBox.Type.HIST)
    val aPlus    = ACheckBox(screen, ACheckBox.Type.PLUS)
    val aPolza   = ACheckBox(screen, ACheckBox.Type.POLZA)

    var blockHistory = {}
    var blockPlus    = {}
    var blockPolza   = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(Color.valueOf("EEEEEE"))))

        addActors(aHistory, aPlus, aPolza)
        aHistory.setBoundsScaled(sizeScaler, 126f, 33f, 119f, 103f)
        aPlus.setBoundsScaled(sizeScaler, 373f, 25f, 119f, 119f)
        aPolza.setBoundsScaled(sizeScaler, 621f, 33f, 138f, 103f)

        aHistory.setOnClickListener(gdxGame.soundUtil) { blockHistory() }
        aPlus.setOnClickListener(gdxGame.soundUtil) { blockPlus() }
        aPolza.setOnClickListener(gdxGame.soundUtil) { blockPolza() }
    }

}