package com.smarters.moneyes.game.actors.scroller.level

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.smarters.moneyes.game.actors.checkbox.ACheckBox
import com.smarters.moneyes.game.actors.checkbox.ATouchCheckBox
import com.smarters.moneyes.game.actors.checkbox.ATouchCheckBoxGroup
import com.smarters.moneyes.game.utils.actor.animShow
import com.smarters.moneyes.game.utils.actor.disable
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.gdxGame

class ALevel(
    override val screen: AdvancedScreen,
    val levelIndex: Int,
    val cbg: ATouchCheckBoxGroup
): AdvancedGroup() {

    private val imgLevel = Image(gdxGame.assetsAll.listLevel[levelIndex])
    private val boxFrame = ATouchCheckBox(screen, ATouchCheckBox.Type.FRAME)
    private val imgCheck = Image(gdxGame.assetsAll.done)

    var blockSelect: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgLevel)
        addAndFillActor(boxFrame)

        boxFrame.checkBoxGroup = cbg
        boxFrame.setOnCheckListener { blockSelect(levelIndex) }

        addActor(imgCheck)
        imgCheck.setBounds(238f, 283f, 139f, 139f)
        imgCheck.disable()
        imgCheck.color.a = 0f
    }

    fun CHECK() {
        imgCheck.color.a = 1f
    }

}