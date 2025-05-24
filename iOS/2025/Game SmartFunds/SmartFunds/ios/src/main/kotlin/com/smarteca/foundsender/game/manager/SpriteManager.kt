package com.smarteca.foundsender.game.manager

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
        ACCESS(AtlasData("atlas/access.atlas")),

        LOADER(AtlasData("atlas/loader.atlas")),

        ALL   (AtlasData("atlas/all.atlas")),
        TEST  (AtlasData("atlas/test.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        ACCESS_ACCESS    (TextureData("textures/access/access.png")),
        ACCESS_BACKGROUND(TextureData("textures/access/background.png")),

        LOADER_BACKGROUND(TextureData("textures/loader/background.png")),
        LOADER_MASK      (TextureData("textures/loader/mask.png")),

        ACCESS          (TextureData("textures/all/access.png")),
        DASHBOARD_TEXT  (TextureData("textures/all/dashboard_text.png")),
        SAVINGS_FORMA   (TextureData("textures/all/savings_forma.png")),
        CALCULATOR_FORMA(TextureData("textures/all/calculator_forma.png")),

        G1(TextureData("textures/all/g1.png")),
        G2(TextureData("textures/all/g2.png")),
        G3(TextureData("textures/all/g3.png")),
        G4(TextureData("textures/all/g4.png")),

        R1(TextureData("textures/all/r1.png")),
        R2(TextureData("textures/all/r2.png")),
        R3(TextureData("textures/all/r3.png")),

        ANSWERS(TextureData("textures/all/answers.png")),
        TEST_PROGRESS(TextureData("textures/all/test_progress.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
