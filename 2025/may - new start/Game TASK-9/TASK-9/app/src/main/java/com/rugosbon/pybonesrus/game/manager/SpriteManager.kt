package com.rugosbon.pybonesrus.game.manager

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
        CATEGORY(AtlasData("atlas/category.atlas")),
        ICONS(AtlasData("atlas/icons.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        //L_B_LOADER(TextureData("textures/loader/b.png")),

        B1                 (TextureData("textures/b1.png")),
        B2                 (TextureData("textures/b2.png")),
        B3                 (TextureData("textures/b3.png")),
        BALANCEG           (TextureData("textures/balanceg.png")),
        BUTTONS            (TextureData("textures/buttons.png")),
        INPUTE             (TextureData("textures/inpute.png")),
        R1                 (TextureData("textures/r1.png")),
        R2                 (TextureData("textures/r2.png")),
        R3                 (TextureData("textures/r3.png")),
        S1                 (TextureData("textures/s1.png")),
        S2                 (TextureData("textures/s2.png")),
        S3                 (TextureData("textures/s3.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}