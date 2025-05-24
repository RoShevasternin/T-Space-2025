package com.sburbanaizer.verginiafalesska.game.manager

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
        L_mask  (TextureData("texture/loader/mask.png")),
        L_Loader(TextureData("texture/loader/Loader.png")),

        _1          (TextureData("texture/all/1.png")),
        _2          (TextureData("texture/all/2.png")),
        _3          (TextureData("texture/all/3.png")),
        _4          (TextureData("texture/all/4.png")),
        d1          (TextureData("texture/all/d1.png")),
        d2          (TextureData("texture/all/d2.png")),
        d3          (TextureData("texture/all/d3.png")),
        d4          (TextureData("texture/all/d4.png")),
        improvment  (TextureData("texture/all/improvment.png")),
        investment  (TextureData("texture/all/investment.png")),
        p1          (TextureData("texture/all/p1.png")),
        p2          (TextureData("texture/all/p2.png")),
        p3          (TextureData("texture/all/p3.png")),
        top         (TextureData("texture/all/top.png")),
        Tutorial_1  (TextureData("texture/all/Tutorial 1.png")),
        Tutorial_2  (TextureData("texture/all/Tutorial 2.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}