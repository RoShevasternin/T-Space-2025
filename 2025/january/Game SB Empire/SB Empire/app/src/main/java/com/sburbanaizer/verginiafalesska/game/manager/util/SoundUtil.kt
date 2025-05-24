package com.sburbanaizer.verginiafalesska.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.sburbanaizer.verginiafalesska.game.manager.AudioManager
import com.sburbanaizer.verginiafalesska.game.utils.runGDX
import com.sburbanaizer.verginiafalesska.game.manager.SoundManager

class SoundUtil {

    val click  = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.085f)
    val drop   = AdvancedSound(SoundManager.EnumSound.drop.data.sound, 0.15f)
    val gold   = AdvancedSound(SoundManager.EnumSound.gold.data.sound, 0.15f)

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