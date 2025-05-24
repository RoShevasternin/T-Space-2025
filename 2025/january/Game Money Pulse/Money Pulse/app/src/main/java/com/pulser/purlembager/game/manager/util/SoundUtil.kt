package com.pulser.purlembager.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.pulser.purlembager.game.manager.AudioManager
import com.pulser.purlembager.game.utils.runGDX
import com.pulser.purlembager.game.manager.SoundManager

class SoundUtil {

    val click  = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.5f)
    val fail   = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.4f)

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