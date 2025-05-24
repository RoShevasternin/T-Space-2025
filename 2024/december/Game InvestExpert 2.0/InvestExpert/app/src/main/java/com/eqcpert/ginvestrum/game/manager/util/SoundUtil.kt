package com.eqcpert.ginvestrum.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.eqcpert.ginvestrum.game.manager.AudioManager
import com.eqcpert.ginvestrum.game.utils.runGDX
import com.eqcpert.ginvestrum.game.manager.SoundManager

class SoundUtil {

    val buy        = SoundManager.EnumSound.buy.data.sound
    val click_soft = SoundManager.EnumSound.click_soft.data.sound
    val coin       = SoundManager.EnumSound.coin.data.sound
    val fail       = SoundManager.EnumSound.fail.data.sound

    private val coin_1 = SoundManager.EnumSound.coin_1.data.sound
    private val coin_2 = SoundManager.EnumSound.coin_2.data.sound
    private val coin_3 = SoundManager.EnumSound.coin_3.data.sound
    private val coin_4 = SoundManager.EnumSound.coin_4.data.sound

    val listCoin = listOf(coin_1,coin_2,coin_3,coin_4)

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}