package com.smarteca.foundsender.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.smarteca.foundsender.game.manager.AudioManager
import com.smarteca.foundsender.game.manager.SoundManager

class SoundUtil {

    val click  = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)

    val calculate = AdvancedSound(SoundManager.EnumSound.calculate.data.sound, 1f)
    val fail      = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.4f)
    val win       = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.4f)
    val win_game  = AdvancedSound(SoundManager.EnumSound.win_game.data.sound, 1f)

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
