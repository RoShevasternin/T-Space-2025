package com.smarteca.foundsender.game.manager

import org.robovm.apple.avfoundation.AVAudioSession

object AudioManager {

    private val audioSession = AVAudioSession.getSharedInstance()

    // Отримання максимального рівня гучності (на iOS це завжди 1.0)
    val maxVolumeLevel: Float
        get() = 1.0f

    // Отримання поточного рівня гучності
    val volumeLevel: Float
        get() = audioSession.outputVolume

    // Отримання рівня гучності у відсотках (0-100%)
    val volumeLevelPercent: Float
        get() = volumeLevel * 100f

}
