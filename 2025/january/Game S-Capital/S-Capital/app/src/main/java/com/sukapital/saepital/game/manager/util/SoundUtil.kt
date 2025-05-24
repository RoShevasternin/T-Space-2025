package com.sukapital.saepital.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.sukapital.saepital.game.manager.AudioManager
import com.sukapital.saepital.game.utils.runGDX
import com.sukapital.saepital.game.manager.SoundManager

class SoundUtil {

    val click          = SoundManager.EnumSound.click_game.data.sound
    val fail_game      = SoundManager.EnumSound.fail_game.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}