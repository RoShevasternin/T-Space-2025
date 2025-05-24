package com.pulser.purlembager.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.dataStore.DataDesire
import com.pulser.purlembager.game.dataStore.DataTransaction
import com.pulser.purlembager.game.dataStore.DataTransactionType
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame
import com.pulser.purlembager.game.utils.toSeparate
import com.pulser.purlembager.game.utils.toSeparateCrapka

class AItemDesire(
    override val screen: AdvancedScreen,
    dataDesire: DataDesire,
    ls50      : Label.LabelStyle,
    ls37      : Label.LabelStyle,
): AdvancedGroup() {

    private val lblName  = Label(dataDesire.tName, ls50)
    private val lblSumma = Label("${dataDesire.tSumma.toSeparate()} ₽", ls37)
    private val lblDate  = Label("${dataDesire.tDate} ${dataDesire.tDay}", ls37)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.desire_back))
        addLbls()
    }

    // Actors ----------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblName, lblSumma, lblDate)
        lblName.apply {
            setBounds(30f, 261f, 331f, 75f)
            setAlignment(Align.center)
        }
        lblSumma.apply {
            setBounds(43f, 122f, 180f, 51f)
            setAlignment(Align.left)
        }
        lblDate.apply {
            setBounds(43f, 71f, 198f, 51f)
            setAlignment(Align.left)
        }
    }

}