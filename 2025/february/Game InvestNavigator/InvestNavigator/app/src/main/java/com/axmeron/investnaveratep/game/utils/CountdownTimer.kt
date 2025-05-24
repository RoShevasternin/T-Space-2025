package com.axmeron.investnaveratep.game.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownTimer(
    private val coroutineScope: CoroutineScope,
    private val totalSeconds: Int, // Загальний час у секундах
    private val onTick: (String) -> Unit, // Колбек для оновлення тексту
    private val onFinish: () -> Unit // Колбек для завершення
) {

    private var remainingSeconds = totalSeconds

    fun start() {
        coroutineScope.launch {
            while (remainingSeconds > 0) {
                val minutes = remainingSeconds / 60
                val seconds = remainingSeconds % 60
                onTick(String.format("%02d:%02d", minutes, seconds))
                delay(1000L)
                remainingSeconds--
            }
            onFinish()
        }
    }
}