package com.bobrevsc.tkarierraperbola.game.manager

import android.content.Context
import android.media.AudioManager
import com.bobrevsc.tkarierraperbola.appContext

object AudioManager {

    val audioManager   by lazy { appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val maxVolumeLevel by lazy { audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) }
    val volumeLevel    get() = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    val onePercentVolumeLevel by lazy { (maxVolumeLevel / 100f) }
    val volumeLevelPercent    get() = volumeLevel / onePercentVolumeLevel

}