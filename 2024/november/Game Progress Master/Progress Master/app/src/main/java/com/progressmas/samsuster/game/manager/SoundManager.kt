package com.progressmas.samsuster.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

class SoundManager(var assetManager: AssetManager) {

    var loadableSoundList = mutableListOf<SoundData>()

    fun load() {
        loadableSoundList.onEach { assetManager.load(it.path, Sound::class.java) }
    }

    fun init() {
        loadableSoundList.onEach { it.sound = assetManager[it.path, Sound::class.java] }
        loadableSoundList.clear()
    }

    enum class EnumSound(val data: SoundData) {
        buy       (SoundData("sound/buy.mp3")),
        click_soft(SoundData("sound/click_soft.mp3")),
        coin      (SoundData("sound/coin.mp3")),
        coin_1    (SoundData("sound/coin_1.mp3")),
        coin_2    (SoundData("sound/coin_2.mp3")),
        coin_3    (SoundData("sound/coin_3.mp3")),
        coin_4    (SoundData("sound/coin_4.mp3")),
        fail      (SoundData("sound/fail.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}