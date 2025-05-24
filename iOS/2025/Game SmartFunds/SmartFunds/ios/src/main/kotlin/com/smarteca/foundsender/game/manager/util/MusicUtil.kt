package com.smarteca.foundsender.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.smarteca.foundsender.game.manager.AudioManager
import com.smarteca.foundsender.game.utils.runGDX
import com.smarteca.foundsender.game.manager.MusicManager
import com.smarteca.foundsender.util.cancelCoroutineAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val music_def = MusicManager.EnumMusic.music_def.data.music

    // 0..100
    val volumeLevelFlow = MutableStateFlow(AudioManager.volumeLevelPercent)

    var coff = 1f

    private var lastMusic: Music? = null
    var currentMusic: Music?
        get() = lastMusic
        set(value) { runGDX {
            if (value != null) {
                if (lastMusic != value) {
                    lastMusic?.stop()
                    lastMusic = value
                    lastMusic?.volume = (volumeLevelFlow.value / 100f) * coff
                    lastMusic?.play()
                }
            } else {
                lastMusic?.stop()
                lastMusic = null
            }
        } }

    init {
        coroutine.launch { volumeLevelFlow.collect { level -> runGDX { lastMusic?.volume = (level / 100f) * coff } } }
    }

    override fun dispose() {
        cancelCoroutineAll(coroutine)
        lastMusic    = null
        currentMusic = null
    }

}
