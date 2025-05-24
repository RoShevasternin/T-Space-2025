package com.axmeron.investnaveratep.game.manager

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

    fun initTexture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        LOADER  (AtlasData("atlas/loader.atlas")),
        ALL     (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        L_gradient     (TextureData("texture/loader/gradient.png")),
        L_mask_logo    (TextureData("texture/loader/mask_logo.png")),
        L_mask_progress(TextureData("texture/loader/mask_progress.png")),

        test         (TextureData("texture/all/test.png")),
        a_chel       (TextureData("texture/all/a_chel.png")),
        a_coin       (TextureData("texture/all/a_coin.png")),
        a_result     (TextureData("texture/all/a_result.png")),
        a_tel        (TextureData("texture/all/a_tel.png")),
        b_chel       (TextureData("texture/all/b_chel.png")),
        b_coin       (TextureData("texture/all/b_coin.png")),
        b_result     (TextureData("texture/all/b_result.png")),
        c_chel       (TextureData("texture/all/c_chel.png")),
        c_result     (TextureData("texture/all/c_result.png")),
        c_x1         (TextureData("texture/all/c_x1.png")),
        c_x2         (TextureData("texture/all/c_x2.png")),
        menu         (TextureData("texture/all/menu.png")),
        onboard_blob (TextureData("texture/all/onboard_blob.png")),
        onboard_main (TextureData("texture/all/onboard_main.png")),
        result       (TextureData("texture/all/result.png")),
        white_result (TextureData("texture/all/white_result.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}