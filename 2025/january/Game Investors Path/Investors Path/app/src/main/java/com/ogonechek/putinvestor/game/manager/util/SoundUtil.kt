package com.ogonechek.putinvestor.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.ogonechek.putinvestor.game.manager.AudioManager
import com.ogonechek.putinvestor.game.utils.runGDX
import com.ogonechek.putinvestor.game.manager.SoundManager

class SoundUtil {

    val click  = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.15f)

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