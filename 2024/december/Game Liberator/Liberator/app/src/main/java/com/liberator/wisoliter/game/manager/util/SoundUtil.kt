package com.liberator.wisoliter.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.liberator.wisoliter.game.manager.AudioManager
import com.liberator.wisoliter.game.utils.runGDX
import com.liberator.wisoliter.game.manager.SoundManager

class SoundUtil {

    val click  = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.2f)
    val buy    = AdvancedSound(SoundManager.EnumSound.buy.data.sound, 0.4f)
    val create = AdvancedSound(SoundManager.EnumSound.create.data.sound, 1f)
    val select = AdvancedSound(SoundManager.EnumSound.select.data.sound, 1f)
    val fail   = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.4f)

    private val w1  = AdvancedSound(SoundManager.EnumSound.w1.data.sound, 0.2f)
    private val w2  = AdvancedSound(SoundManager.EnumSound.w2.data.sound, 0.2f)
    private val w3  = AdvancedSound(SoundManager.EnumSound.w3.data.sound, 0.2f)
    private val w4  = AdvancedSound(SoundManager.EnumSound.w4.data.sound, 0.2f)
    private val w5  = AdvancedSound(SoundManager.EnumSound.w5.data.sound, 0.2f)
    private val w6  = AdvancedSound(SoundManager.EnumSound.w6.data.sound, 0.2f)
    private val w7  = AdvancedSound(SoundManager.EnumSound.w7.data.sound, 0.2f)
    private val w8  = AdvancedSound(SoundManager.EnumSound.w8.data.sound, 0.2f)
    private val w9  = AdvancedSound(SoundManager.EnumSound.w9.data.sound, 0.2f)
    private val w10 = AdvancedSound(SoundManager.EnumSound.w10.data.sound, 0.2f)
    private val w11 = AdvancedSound(SoundManager.EnumSound.w11.data.sound, 0.2f)
    private val w12 = AdvancedSound(SoundManager.EnumSound.w12.data.sound, 0.2f)
    private val w13 = AdvancedSound(SoundManager.EnumSound.w13.data.sound, 0.2f)
    private val w14 = AdvancedSound(SoundManager.EnumSound.w14.data.sound, 0.2f)
    private val w15 = AdvancedSound(SoundManager.EnumSound.w15.data.sound, 0.2f)

    val listAtack = listOf(w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15)


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