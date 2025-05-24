package com.liberator.wisoliter.game.manager

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
        click (SoundData("sound/click.mp3")),
        buy   (SoundData("sound/buy.mp3")),
        create(SoundData("sound/create.mp3")),
        select(SoundData("sound/select.mp3")),
        fail  (SoundData("sound/fail.mp3")),

        w1  (SoundData("sound/gun/w1.mp3")),
        w2  (SoundData("sound/gun/w2.mp3")),
        w3  (SoundData("sound/gun/w3.mp3")),
        w4  (SoundData("sound/gun/w4.mp3")),
        w5  (SoundData("sound/gun/w5.mp3")),
        w6  (SoundData("sound/gun/w6.mp3")),
        w7  (SoundData("sound/gun/w7.mp3")),
        w8  (SoundData("sound/gun/w8.mp3")),
        w9  (SoundData("sound/gun/w9.mp3")),
        w10 (SoundData("sound/gun/w10.mp3")),
        w11 (SoundData("sound/gun/w11.mp3")),
        w12 (SoundData("sound/gun/w12.mp3")),
        w13 (SoundData("sound/gun/w13.mp3")),
        w14 (SoundData("sound/gun/w14.mp3")),
        w15 (SoundData("sound/gun/w15.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}