package com.basarili.baslangisc.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.basarili.baslangisc.game.utils.advanced.AdvancedGroup
import com.basarili.baslangisc.game.utils.advanced.AdvancedScreen

class AClickGaz(
    override val screen: AdvancedScreen,
    value: Int,
    ls66: LabelStyle
) : AdvancedGroup() {

    private val imgGaz   = Image(screen.game.all.gas)
    private val lblValue = Label("+$value", ls66)

    override fun addActorsOnGroup() {
        addActors(imgGaz, lblValue)
        imgGaz.setBounds(0f, 0f, 111f, 112f)
        lblValue.setBounds(125f, 23f, 63f, 66f)

        setOrigin(Align.center)
        rotation = 25f
    }

}