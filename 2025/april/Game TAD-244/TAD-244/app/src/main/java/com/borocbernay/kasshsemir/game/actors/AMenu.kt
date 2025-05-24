package com.borocbernay.kasshsemir.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.borocbernay.kasshsemir.game.actors.checkbox.ACheckBox
import com.borocbernay.kasshsemir.game.utils.SizeScaler
import com.borocbernay.kasshsemir.game.utils.WIDTH_UI
import com.borocbernay.kasshsemir.game.utils.actor.setBoundsScaled
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedGroup
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedScreen
import com.borocbernay.kasshsemir.game.utils.gdxGame

class AMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    val boxS1 = ACheckBox(screen, ACheckBox.Type.i1)
    val boxS2 = ACheckBox(screen, ACheckBox.Type.i2)
    val boxS3 = ACheckBox(screen, ACheckBox.Type.i3)

    var blockS1 = {}
    var blockS2 = {}
    var blockS3 = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.botom))

        addActors(boxS1, boxS2, boxS3)
        boxS1.setBoundsScaled(sizeScaler, 111f, 47f, 105f, 88f)
        boxS2.setBoundsScaled(sizeScaler, 354f, 47f, 113f, 88f)
        boxS3.setBoundsScaled(sizeScaler, 605f, 47f, 149f, 88f)

        boxS1.setOnCheckListener { if (it) blockS1() }
        boxS2.setOnCheckListener { if (it) blockS2() }
        boxS3.setOnCheckListener { if (it) blockS3() }
    }

}