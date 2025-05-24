package com.tinkf.tnk.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.tinkf.tnk.game.manager.AudioManager
import com.tinkf.tnk.game.utils.runGDX
import com.tinkf.tnk.game.manager.SoundManager

class SoundUtil {

    val click   = SoundManager.EnumSound.click.data.sound
    val fail    = SoundManager.EnumSound.fail.data.sound
    val sell    = SoundManager.EnumSound.sell.data.sound
    val success = SoundManager.EnumSound.success.data.sound

    private val s1 = SoundManager.EnumSound.s1.data.sound
    private val s2 = SoundManager.EnumSound.s2.data.sound
    private val s3 = SoundManager.EnumSound.s3.data.sound
    private val s4 = SoundManager.EnumSound.s4.data.sound
    private val s5 = SoundManager.EnumSound.s5.data.sound
    private val s6 = SoundManager.EnumSound.s6.data.sound
    private val s7 = SoundManager.EnumSound.s7.data.sound

    val listS = listOf(s1, s2, s3, s4, s5, s6, s7)

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}