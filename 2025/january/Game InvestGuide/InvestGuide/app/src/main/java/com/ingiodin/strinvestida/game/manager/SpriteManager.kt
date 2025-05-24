package com.ingiodin.strinvestida.game.manager

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
        LOADERAS (AtlasData("atlas/loaderas.atlas")),
        ALL      (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        loader  (TextureData("texture/loader/loader.png")),
        maska   (TextureData("texture/loader/maska.png")),

        bottom       (TextureData("texture/all/bottom.png")),
        chel         (TextureData("texture/all/chel.png")),
        cubek        (TextureData("texture/all/cubek.png")),
        fon          (TextureData("texture/all/fon.png")),
        mishen       (TextureData("texture/all/mishen.png")),
        ogo          (TextureData("texture/all/ogo.png")),
        persona_padae(TextureData("texture/all/persona_padae.png")),
        strela       (TextureData("texture/all/strela.png")),
        stupeni      (TextureData("texture/all/stupeni.png")),
        vau          (TextureData("texture/all/vau.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}