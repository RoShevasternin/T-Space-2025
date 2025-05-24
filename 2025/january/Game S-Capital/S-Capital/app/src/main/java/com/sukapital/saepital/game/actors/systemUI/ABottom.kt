package com.sukapital.saepital.game.actors.systemUI

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sukapital.saepital.game.actors.AButton
import com.sukapital.saepital.game.actors.checkbox.ACheckBox
import com.sukapital.saepital.game.actors.checkbox.ACheckBoxGroup
import com.sukapital.saepital.game.utils.GColor
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen

class ABottom(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgBottom = Image(screen.drawerUtil.getRegion(GColor.dark))
    private val boxMain   = ACheckBox(screen, ACheckBox.Type.MAIN)
    private val btnPlus   = AButton(screen, AButton.Type.Plus)
    private val boxBlog   = ACheckBox(screen, ACheckBox.Type.BLOG)

    // Field
    var blockNavTo: (ClickType) -> Unit = {}

    enum class ClickType {
        Main, Plus, Blog
    }

    override fun addActorsOnGroup() {
        screen.stageBack.addActor(imgBottom)
        imgBottom.setBounds(0f,y / screen.scalerY, screen.viewportBack.worldWidth, height / screen.scalerY)

        val cbg = ACheckBoxGroup()
        addActors(boxMain, btnPlus, boxBlog)
        boxMain.apply {
            checkBoxGroup = cbg
            setBounds(127f,46f,115f,105f)
            setOnCheckListener { isCheck ->
                if (isCheck) blockNavTo(ClickType.Main)
            }
        }
        btnPlus.apply {
            setBounds(357f,43f,111f,112f)
            setOnClickListener {
                blockNavTo(ClickType.Plus)
            }
        }
        boxBlog.apply {
            checkBoxGroup = cbg
            setBounds(589f,51f,107f,96f)
            setOnCheckListener { isCheck ->
                if (isCheck) blockNavTo(ClickType.Blog)
            }
        }
    }

    // Logic --------------------------------------------------------------------

    fun checkHome() {
        boxMain.check(false)
    }

    fun checkBlog() {
        boxBlog.check(false)
    }

}