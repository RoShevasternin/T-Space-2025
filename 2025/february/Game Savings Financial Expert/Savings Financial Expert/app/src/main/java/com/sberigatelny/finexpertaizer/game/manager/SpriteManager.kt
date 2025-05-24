package com.sberigatelny.finexpertaizer.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList    = mutableListOf<AtlasData>()
    var loadableTexturesList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    // Texture
    fun loadTexture() {
        loadableTexturesList.onEach { assetManager.load(it.path, Texture::class.java) }
    }

    fun initTexture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        LOADER(AtlasData("atlas/loader.atlas")),
        ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        L_mask (TextureData("textures/loader/mask.png")),

        _1              (TextureData("textures/all/1.png")),
        _2              (TextureData("textures/all/2.png")),
        _3              (TextureData("textures/all/3.png")),
        _4              (TextureData("textures/all/4.png")),
        _5              (TextureData("textures/all/5.png")),
        _6              (TextureData("textures/all/6.png")),
        _7              (TextureData("textures/all/7.png")),
        _8              (TextureData("textures/all/8.png")),
        _9              (TextureData("textures/all/9.png")),
        _10             (TextureData("textures/all/10.png")),
        back_1         (TextureData("textures/all/back_1.png")),
        back_2         (TextureData("textures/all/back_2.png")),
        back_3         (TextureData("textures/all/back_3.png")),
        back_4         (TextureData("textures/all/back_4.png")),
        back_5         (TextureData("textures/all/back_5.png")),
        chel           (TextureData("textures/all/chel.png")),
        d1             (TextureData("textures/all/d1.png")),
        d2             (TextureData("textures/all/d2.png")),
        greet_10k      (TextureData("textures/all/greet_10k.png")),
        greet_1000     (TextureData("textures/all/greet_1000.png")),
        greet_new_work (TextureData("textures/all/greet_new_work.png")),
        nema_rabota    (TextureData("textures/all/nema_rabota.png")),
        time_and_gold  (TextureData("textures/all/time_and_gold.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}