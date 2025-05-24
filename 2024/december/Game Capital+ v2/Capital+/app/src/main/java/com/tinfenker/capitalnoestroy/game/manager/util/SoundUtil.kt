package com.tinfenker.capitalnoestroy.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.tinfenker.capitalnoestroy.game.manager.AudioManager
import com.tinfenker.capitalnoestroy.game.utils.runGDX
import com.tinfenker.capitalnoestroy.game.manager.SoundManager

class SoundUtil {

    val click          = SoundManager.EnumSound.click_game.data.sound
    val fail_game      = SoundManager.EnumSound.fail_game.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}