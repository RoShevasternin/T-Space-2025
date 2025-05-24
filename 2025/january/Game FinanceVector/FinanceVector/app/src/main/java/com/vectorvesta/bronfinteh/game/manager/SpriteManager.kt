package com.vectorvesta.bronfinteh.game.manager

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
        L_CUBECE(TextureData("textures/loader/cubece.png")),

        DEPOSIT_DEF       (TextureData("textures/all/deposit_def.png")),
        DEPOSIT_PRESS     (TextureData("textures/all/deposit_press.png")),
        INVESTMENTS_DEF   (TextureData("textures/all/investments_def.png")),
        INVESTMENTS_PRESS (TextureData("textures/all/investments_press.png")),
        LEASING_DEF       (TextureData("textures/all/leasing_def.png")),
        LEASING_PRESS     (TextureData("textures/all/leasing_press.png")),
        LOAN_DEF          (TextureData("textures/all/loan_def.png")),
        LOAN_PRESS        (TextureData("textures/all/loan_press.png")),
        MORTGAGE_DEF      (TextureData("textures/all/mortgage_def.png")),
        MORTGAGE_PRESS    (TextureData("textures/all/mortgage_press.png")),
        VISION            (TextureData("textures/all/vision.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}