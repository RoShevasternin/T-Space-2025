package com.gazprombiznes.pygazprobiznes.game.manager

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
        L_BACKGROUND(TextureData("textures/loader/backgred.png")),
        L_MASKA     (TextureData("textures/loader/mask.png")),

        BAGA     (TextureData("textures/all/baga.png")),
        DED_LEFT (TextureData("textures/all/ded_left.png")),
        DED_RIGHT(TextureData("textures/all/ded_right.png")),
        DEROP    (TextureData("textures/all/derop.png")),
        MARSK    (TextureData("textures/all/marsk.png")),
        PERRDO   (TextureData("textures/all/perrdo.png")),

        _1  (TextureData("textures/all/fone/1.png")),
        _2  (TextureData("textures/all/fone/2.png")),
        _3  (TextureData("textures/all/fone/3.png")),
        _4  (TextureData("textures/all/fone/4.png")),
        _5  (TextureData("textures/all/fone/5.png")),
        _6  (TextureData("textures/all/fone/6.png")),
        _7  (TextureData("textures/all/fone/7.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}