package com.ayrym.inperdader.game.actors

import com.ayrym.inperdader.game.utils.GameColor
import com.ayrym.inperdader.game.utils.advanced.AdvancedGroup
import com.ayrym.inperdader.game.utils.advanced.AdvancedScreen
import com.ayrym.inperdader.game.utils.font.FontParameter
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Timer

class ACountdownTimer(
    override val screen: AdvancedScreen,
    minutes: Int, // Початкові хвилини
): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + ":")
    private val font30    = screen.fontGenerator_Regular.generateFont(parameter.setSize(30))
    private val ls30      = Label.LabelStyle(font30, GameColor.purpl_B5)

    private var timeLeft = minutes * 60 // Час у секундах
    private val label    = Label(formatTime(timeLeft), ls30)

    private val timer = Timer()

    override fun addActorsOnGroup() {
        addAndFillActor(label)
        label.setAlignment(Align.center)
    }

    fun startTimer() {
        timer.scheduleTask(object : Timer.Task() {
            override fun run() {
                if (timeLeft > 0) {
                    timeLeft--
                    label.setText(formatTime(timeLeft))
                } else {
                    stopTimer()
                }
            }
        }, 1f, 1f) // Запускаємо кожну секунду
    }

    fun restart(newMinutes: Int) {
        stopTimer()
        timeLeft = newMinutes * 60
        label.setText(formatTime(timeLeft))
        startTimer()
    }

    fun stopTimer() {
        timer.clear()
    }

    fun getTimeLeft(): Int {
        return timeLeft
    }

    private fun formatTime(seconds: Int): String {
        val min = seconds / 60
        val sec = seconds % 60
        return "%02d:%02d".format(min, sec)
    }

}