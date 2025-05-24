package com.simsim.capitalsim.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.simsim.capitalsim.game.manager.AudioManager
import com.simsim.capitalsim.game.utils.runGDX
import com.simsim.capitalsim.game.manager.SoundManager

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.5f)

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