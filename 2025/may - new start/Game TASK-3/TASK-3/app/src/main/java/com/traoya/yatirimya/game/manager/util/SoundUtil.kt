package com.traoya.yatirimya.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.traoya.yatirimya.game.manager.AudioManager
import com.traoya.yatirimya.game.utils.runGDX
import com.traoya.yatirimya.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)
    val touch = AdvancedSound(SoundManager.EnumSound.touch.data.sound, 1f)
    val fail  = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 1f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(advancedSound: AdvancedSound) {
        if (isPause.not()) {
            advancedSound.apply {
                sound.play((volumeLevel / 100f) * coff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}