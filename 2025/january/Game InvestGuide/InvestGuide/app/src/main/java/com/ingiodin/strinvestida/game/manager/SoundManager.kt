package com.ingiodin.strinvestida.game.manager

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
        bonus(SoundData("sound/bonus_game.mp3")),
        click(SoundData("sound/click_game.mp3")),
        fail(SoundData("sound/fail_game.mp3")),
        win(SoundData("sound/win_game.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}