package com.sukapital.saepital.game.manager

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
        LOADER(AtlasData("atlas/loader.atlas")),
        ALL   (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        SPLASH_MASK   (TextureData("texture/loader/mask.png")),
        SPLASH_LOADER(TextureData("texture/loader/Loader.png")),

        BLOG_LIST(TextureData("texture/all/blog_list.png")),
        TEXT_1   (TextureData("texture/all/text_1.png")),
        TEXT_2   (TextureData("texture/all/text_2.png")),
        TEXT_3   (TextureData("texture/all/text_3.png")),
        TEXT_4   (TextureData("texture/all/text_4.png")),
        TEXT_5   (TextureData("texture/all/text_5.png")),
        IncomeExpensePanel(TextureData("texture/all/IncomeExpensePanel.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}