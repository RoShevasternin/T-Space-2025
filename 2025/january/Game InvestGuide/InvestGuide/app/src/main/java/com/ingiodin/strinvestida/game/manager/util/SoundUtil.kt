package com.ingiodin.strinvestida.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.ingiodin.strinvestida.game.manager.AudioManager
import com.ingiodin.strinvestida.game.utils.runGDX
import com.ingiodin.strinvestida.game.manager.SoundManager

class SoundUtil {

    val click              = SoundManager.EnumSound.click.data.sound
    val bonus           = SoundManager.EnumSound.bonus.data.sound
    val fail            = SoundManager.EnumSound.fail.data.sound
    val win         = SoundManager.EnumSound.win.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}