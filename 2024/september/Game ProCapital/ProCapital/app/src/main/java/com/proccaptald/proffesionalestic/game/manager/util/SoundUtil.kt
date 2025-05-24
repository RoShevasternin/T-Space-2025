package com.proccaptald.proffesionalestic.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.proccaptald.proffesionalestic.game.manager.AudioManager
import com.proccaptald.proffesionalestic.game.utils.runGDX
import com.proccaptald.proffesionalestic.game.manager.SoundManager

class SoundUtil {

    val click   = SoundManager.EnumSound.click.data.sound
    val coin    = SoundManager.EnumSound.coin.data.sound
    val step    = SoundManager.EnumSound.step.data.sound
    val winner  = SoundManager.EnumSound.winner.data.sound
    val bonus   = SoundManager.EnumSound.bonus.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}