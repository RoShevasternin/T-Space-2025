package com.sberigatelny.finexpertaizer.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align

class FastMinuteTimer(ls: LabelStyle) : Label("", ls) {

    private var totalMinutes = 0 // Лічильник хвилин

    var blockHours: (Int) -> Unit = {}

    init {
        setAlignment(Align.center)
        updateLabel()
        startTimer()
    }

    private fun updateLabel() {
        val hours   = totalMinutes / 60 % 24
        val minutes = totalMinutes % 60
        setText(String.format("%02d:%02d", hours, minutes))

        blockHours(hours)
    }

    private fun startTimer() {
        addAction(Actions.forever(Actions.sequence(
            //Actions.delay(1f),
            Actions.run {
                totalMinutes++
                if (totalMinutes >= 1440) { // 24 години * 60 хвилин
                    totalMinutes = 0
                }
                updateLabel()
            }
        )))
    }
}