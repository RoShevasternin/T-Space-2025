package com.pulser.purlembager.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.dataStore.DataTransaction
import com.pulser.purlembager.game.dataStore.DataTransactionType
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.toSeparate
import com.pulser.purlembager.game.utils.toSeparateCrapka

class AItem(
    override val screen: AdvancedScreen,
    dataTransaction: DataTransaction,
    ls50      : Label.LabelStyle,
    ls50_summa: Label.LabelStyle,
): AdvancedGroup() {

    private val result = if (dataTransaction.type == DataTransactionType.Income) "+" else "-"

    private val lblName  = Label(dataTransaction.tName, ls50)
    private val lblSumma = Label("$result ₽${dataTransaction.tSumma.toSeparateCrapka()}", ls50_summa)

    override fun addActorsOnGroup() {
        addLbls()
    }

    // Actors ----------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblName, lblSumma)
        lblName.apply {
            setBounds(0f, 0f, 956f, 64f)
            setAlignment(Align.left)
        }
        lblSumma.apply {
            setBounds(771f, 0f, 185f, 64f)
            setAlignment(Align.right)
        }
    }

}