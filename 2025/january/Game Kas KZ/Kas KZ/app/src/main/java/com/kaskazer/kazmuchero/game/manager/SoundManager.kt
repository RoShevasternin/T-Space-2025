package com.kaskazer.kazmuchero.game.manager

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
        bubble_1  (SoundData("sound/bubble_1.mp3")),
        bubble_2  (SoundData("sound/bubble_2.mp3")),
        bubble_3  (SoundData("sound/bubble_3.mp3")),
        buy       (SoundData("sound/buy.mp3")),
        click     (SoundData("sound/click.mp3")),
        gaz_satmak(SoundData("sound/gaz_satmak.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}