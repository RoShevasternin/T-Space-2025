package com.jobzone.cobzone.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.jobzone.cobzone.game.manager.AudioManager
import com.jobzone.cobzone.game.utils.runGDX
import com.jobzone.cobzone.game.manager.SoundManager

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.1f)

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