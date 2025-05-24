package com.frusune.abvger.game.actors.scroller

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.toBalance

class ABanner(
    override val screen: AdvancedScreen,
    val title: String,
    val sum  : Int,
    val ls33 : LabelStyle,
    val ls53 : LabelStyle,
) : AdvancedGroup() {

    private val imgPanel = Image(screen.game.all.scroller)
    private val lblTitle = Label(title, ls33)
    private val lblSum   = Label("${sum.toBalance} ₽", ls53)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActors(lblTitle, lblSum)
        lblTitle.setBounds(159f,152f,267f,23f)
        lblSum.setBounds(159f,83f,296f,38f)
    }

}