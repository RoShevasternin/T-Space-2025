package com.barabanovich.helowerskay.game.manager

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
        L_background_1 (TextureData("textures/loader/background_1.png")),

        background_2 (TextureData("textures/all/background_2.png")),

        _1_def       (TextureData("textures/all/_1_def.png")),
        _1_press     (TextureData("textures/all/_1_press.png")),
        _2_def       (TextureData("textures/all/_2_def.png")),
        _2_press     (TextureData("textures/all/_2_press.png")),
        _3_def       (TextureData("textures/all/_3_def.png")),
        _3_press     (TextureData("textures/all/_3_press.png")),
        _4_def       (TextureData("textures/all/_4_def.png")),
        _4_press     (TextureData("textures/all/_4_press.png")),
        ekspert      (TextureData("textures/all/ekspert.png")),
        forma        (TextureData("textures/all/forma.png")),
        prognoz      (TextureData("textures/all/prognoz.png")),
        start        (TextureData("textures/all/start.png")),
        znatok       (TextureData("textures/all/znatok.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}