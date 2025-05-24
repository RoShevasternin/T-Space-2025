package com.funkun.kylkan.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.funkun.kylkan.game.dataStore.Trata
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.toSeparateCrapka

class ATrata(
    override val screen: AdvancedScreen,
    val tripData: Trata,
    val lsReg: Label.LabelStyle,
    val lsMed: Label.LabelStyle,
): AdvancedGroup() {

    private val lblName  = Label(tripData.name, lsReg)
    private val lblSumma = Label("${tripData.summa.toSeparateCrapka()} ₽", lsMed)

    override fun addActorsOnGroup() {
        addLbls()
    }

    // Actors -------------------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblName, lblSumma)
        lblName.setBounds(0f, 24f, 191f, 51f)
        lblSumma.setBounds(552f, 24f, 203f, 51f)
        lblSumma.setAlignment(Align.right)
    }

}