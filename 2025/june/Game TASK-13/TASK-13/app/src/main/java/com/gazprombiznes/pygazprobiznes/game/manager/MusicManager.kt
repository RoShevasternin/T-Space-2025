package com.gazprombiznes.pygazprobiznes.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music

class MusicManager(var assetManager: AssetManager) {

    var loadableMusicList = mutableListOf<MusicData>()

    fun load() {
        loadableMusicList.onEach { assetManager.load(it.path, Music::class.java) }
    }

    fun init() {
        loadableMusicList.onEach { it.music = assetManager[it.path, Music::class.java] }
        loadableMusicList.clear()
    }

    enum class EnumMusic(val data: MusicData) {
//        game    (MusicData("music/game.ogg")),
    }

    data class MusicData(
        val path: String,
    ) {
        lateinit var music: Music
    }

}