package com.plannercap.pitalcamp.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.plannercap.pitalcamp.game.manager.AudioManager
import com.plannercap.pitalcamp.game.utils.runGDX
import com.plannercap.pitalcamp.game.manager.SoundManager

class SoundUtil {

    val click          = SoundManager.EnumSound.click_game.data.sound
    val fail_game      = SoundManager.EnumSound.fail_game.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}