package com.tinfenker.capitalnoestroy.game.manager

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

    fun initTeture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }


    enum class EnumAtlas(val data: AtlasData) {
        SPLASH(AtlasData("atlas/splash.atlas")),
        ALL   (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        SPLASH_MASK(TextureData("texture/splash/mask.png")),

        PANEL_BIGGER(TextureData("texture/all/panel_bigger.png")),
        BIGGER_INPUT(TextureData("texture/all/bigger_input.png")),
        SHKALA_MASK (TextureData("texture/all/shkala_mask.png")),
        TOP_INVEST  (TextureData("texture/all/top_invest.png")),
        PANEL_DATA_BIG(TextureData("texture/all/panel_data_big.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}