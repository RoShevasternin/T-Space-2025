package com.ekobioznaher.sugertogerra.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTexturesList   = mutableListOf<TextureData>()

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
        LOADER  (AtlasData("atlas/loader.atlas")),
        ALL     (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        L_Loader(TextureData("texture/loader/Loader.png")),

        Back   (TextureData("texture/all/Back.png")),
        Konfety(TextureData("texture/all/Konfety.png")),

        r1 (TextureData("texture/all/result/r1.png")),
        r5 (TextureData("texture/all/result/r5.png")),
        r10(TextureData("texture/all/result/r10.png")),

        _1 (TextureData("texture/all/level/1.png")),
        _2 (TextureData("texture/all/level/2.png")),
        _3 (TextureData("texture/all/level/3.png")),
        _4 (TextureData("texture/all/level/4.png")),
        _5 (TextureData("texture/all/level/5.png")),
        _6 (TextureData("texture/all/level/6.png")),
        _7 (TextureData("texture/all/level/7.png")),
        _8 (TextureData("texture/all/level/8.png")),
        _9 (TextureData("texture/all/level/9.png")),
        _10(TextureData("texture/all/level/10.png")),
        _11(TextureData("texture/all/level/11.png")),
        _12(TextureData("texture/all/level/12.png")),

        d1(TextureData("texture/all/dome/d1.png")),
        d2(TextureData("texture/all/dome/d2.png")),
        d3(TextureData("texture/all/dome/d3.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}