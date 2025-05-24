package com.kaskazer.kazmuchero.game.manager

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
        LOADER   (AtlasData("atlas/loader.atlas")),
        ALL      (AtlasData("atlas/all.atlas")),
        BUILDINGS(AtlasData("atlas/buildings.atlas")),
        DETAILS  (AtlasData("atlas/details.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        background_loader(TextureData("texture/loader/background_loader.png")),

        background_game(TextureData("texture/all/background_game.png")),

        market(TextureData("texture/all/market.png")),
        pop_1 (TextureData("texture/all/pop_1.png")),
        pop_2 (TextureData("texture/all/pop_2.png")),
        pop_3 (TextureData("texture/all/pop_3.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}