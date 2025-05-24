package com.frusune.abvger.game.actors.input

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.frusune.abvger.game.actors.AButton
import com.frusune.abvger.game.actors.progress.AYellowProgress
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.runGDX
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class APeriod(
    override val screen: AdvancedScreen,
    val ls53: Label.LabelStyle,
) : AdvancedGroup() {

    private val imgPanel = Image(screen.game.all.period)
    private val shkala   = Image(screen.game.all._0_12)
    private val progress = AYellowProgress(screen)
    private val btnDone  = AButton(screen, AButton.Static.Type.Done)
    private val lblNum   = Label("0", ls53)

    var blockProgress: (Int) -> Unit = {}
    var blockDone    : () -> Unit = {}

    override fun addActorsOnGroup() {
        addActors(imgPanel, shkala, progress, btnDone, lblNum)
        imgPanel.setBounds(0f,602f,380f,139f)
        shkala.setBounds(0f,354f,517f,28f)
        progress.apply {
            setBounds(0f,293f,516f,45f)

            val onePercent = 100f / 12f

            coroutine?.launch {
                progressPercentFlow.collect { runGDX {
                    val result = (it / onePercent).roundToInt()
                    lblNum.setText(result)
                    blockProgress(result)
                } }
            }
        }
        btnDone.apply {
            setBounds(175f,0f,166f,94f)
            setOnClickListener { blockDone() }
        }
        lblNum.apply {
            setBounds(0f,602f,206f,95f)
            setAlignment(Align.center)
        }
    }

}