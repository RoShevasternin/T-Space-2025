package com.basarili.baslangisc.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.basarili.baslangisc.game.manager.AudioManager
import com.basarili.baslangisc.game.utils.runGDX
import com.basarili.baslangisc.game.manager.SoundManager

class SoundUtil {

    val click              = SoundManager.EnumSound.click.data.sound
    private val bubble_1   = SoundManager.EnumSound.bubble_1.data.sound
    private val bubble_2   = SoundManager.EnumSound.bubble_2.data.sound
    private val bubble_3   = SoundManager.EnumSound.bubble_3.data.sound
    val buy                = SoundManager.EnumSound.buy.data.sound
    val gaz_satmak         = SoundManager.EnumSound.gaz_satmak.data.sound


    val listBubble = listOf(bubble_1,bubble_2,bubble_3)

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}