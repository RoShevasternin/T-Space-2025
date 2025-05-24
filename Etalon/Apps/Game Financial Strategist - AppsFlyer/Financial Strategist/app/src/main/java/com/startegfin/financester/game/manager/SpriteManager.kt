package com.startegfin.financester.game.manager

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

        ALL_ITEMS_PANEL  (TextureData("texture/all/all_items_panel.png")),
        MAIN_PANELS      (TextureData("texture/all/main_panels.png")),
        MASK             (TextureData("texture/all/mask.png")),
        MINUS_ITEMS_PANEL(TextureData("texture/all/minus_items_panel.png")),
        MINUS_PANEL      (TextureData("texture/all/minus_panel.png")),
        PLUS_ITEMS_PANEL (TextureData("texture/all/plus_items_panel.png")),
        PLUS_PANEL       (TextureData("texture/all/plus_panel.png")),
        TRANSACTION_PANEL(TextureData("texture/all/transaction_panel.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}