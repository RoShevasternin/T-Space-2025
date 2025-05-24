package com.frusune.abvger.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.frusune.abvger.game.actors.checkbox.ACheckBox
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen

class ATopInvest(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgPanel  = Image(screen.game.all.TOP_INVEST)
    private val btnPlus   = AButton(screen, AButton.Static.Type.Plus)
    private val boxHome   = ACheckBox(screen, ACheckBox.Static.Type.HOME)
    private val boxInvest = ACheckBox(screen, ACheckBox.Static.Type.INVEST)

    // Field
    var blockNavTo: (ClickType) -> Unit = {}

    enum class ClickType {
        Plus, Home, Invest
    }

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActors(btnPlus, boxHome, boxInvest)
        btnPlus.apply {
            setBounds(333f,60f,100f,100f)
            setOnClickListener {
                blockNavTo(ClickType.Plus)
            }
        }
        boxHome.apply {
            setBounds(78f,54f,204f,113f)
            setOnCheckListener { isCheck ->
                if (isCheck) blockNavTo(ClickType.Home)
            }
        }
        boxInvest.apply {
            setBounds(484f,54f,204f,113f)
            setOnCheckListener { isCheck ->
                if (isCheck) blockNavTo(ClickType.Invest)
            }
        }
    }

    // Logic --------------------------------------------------------------------

    fun checkHome() {
        boxHome.check(false)
    }

    fun checkInvest() {
        boxInvest.check(false)
    }

}