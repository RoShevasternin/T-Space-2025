package com.pezdunkov.sberdarorcassa.game.actors

import com.pezdunkov.sberdarorcassa.game.actors.checkbox.ACheckBox
import com.pezdunkov.sberdarorcassa.game.utils.SizeScaler
import com.pezdunkov.sberdarorcassa.game.utils.actor.setBoundsScaled
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedGroup
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedScreen

class AMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 636f)

    val boxS1 = ACheckBox(screen, ACheckBox.Type.S1)
    val boxS2 = ACheckBox(screen, ACheckBox.Type.S2)
    val boxS3 = ACheckBox(screen, ACheckBox.Type.S3)
    val boxS4 = ACheckBox(screen, ACheckBox.Type.S4)

    var blockS1 = {}
    var blockS2 = {}
    var blockS3 = {}
    var blockS4 = {}

    override fun addActorsOnGroup() {
        addActors(boxS1, boxS2, boxS3, boxS4)
        boxS1.setBoundsScaled(sizeScaler, 0f, 0f, 132f, 188f)
        boxS2.setBoundsScaled(sizeScaler, 170f, 0f, 132f, 188f)
        boxS3.setBoundsScaled(sizeScaler, 335f, 0f, 132f, 188f)
        boxS4.setBoundsScaled(sizeScaler, 500f, 0f, 132f, 188f)

        boxS1.setOnCheckListener { if (it) blockS1() }
        boxS2.setOnCheckListener { if (it) blockS2() }
        boxS3.setOnCheckListener { if (it) blockS3() }
        boxS4.setOnCheckListener { if (it) blockS4() }
    }

}