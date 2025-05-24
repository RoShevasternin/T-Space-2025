package com.bobrevsc.tkarierraperbola.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.bobrevsc.tkarierraperbola.game.manager.AudioManager
import com.bobrevsc.tkarierraperbola.game.utils.runGDX
import com.bobrevsc.tkarierraperbola.util.cancelCoroutinesAll
import com.bobrevsc.tkarierraperbola.game.manager.MusicManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val work = MusicManager.EnumMusic.work.data.music

    // 0..100
    val volumeLevelFlow = MutableStateFlow(AudioManager.volumeLevelPercent)

    private var _music: Music? = null
    var music: Music?
        get() = _music
        set(value) { runGDX {
            if (value != null) {
                if (_music != value) {
                    _music?.stop()
                    _music = value
                    _music?.volume = volumeLevelFlow.value / 100f
                    _music?.play()
                }
            } else {
                _music?.stop()
                _music = null
            }
        } }

    init {
        coroutine.launch { volumeLevelFlow.collect { level -> runGDX { _music?.volume = level / 100f } } }
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        _music = null
        music  = null
    }

}