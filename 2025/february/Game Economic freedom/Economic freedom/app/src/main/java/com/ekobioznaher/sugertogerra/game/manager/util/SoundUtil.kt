package com.ekobioznaher.sugertogerra.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.ekobioznaher.sugertogerra.game.manager.AudioManager
import com.ekobioznaher.sugertogerra.game.utils.runGDX
import com.ekobioznaher.sugertogerra.game.manager.SoundManager

class SoundUtil {

    val click  = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.05f)

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