package com.fincarable.kapletaloverno.game.manager

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
        click  (SoundData("sound/click.mp3")),
        fail   (SoundData("sound/fail.mp3")),
        s1     (SoundData("sound/s1.mp3")),
        s2     (SoundData("sound/s2.mp3")),
        s3     (SoundData("sound/s3.mp3")),
        s4     (SoundData("sound/s4.mp3")),
        s5     (SoundData("sound/s5.mp3")),
        s6     (SoundData("sound/s6.mp3")),
        s7     (SoundData("sound/s7.mp3")),
        sell   (SoundData("sound/sell.mp3")),
        success(SoundData("sound/success.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}