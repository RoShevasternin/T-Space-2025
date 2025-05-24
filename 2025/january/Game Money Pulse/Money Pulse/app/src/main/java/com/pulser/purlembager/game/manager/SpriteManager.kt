package com.pulser.purlembager.game.manager

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
        L_a(TextureData("texture/loader/a.png")),
        L_b(TextureData("texture/loader/b.png")),

        Blog       (TextureData("texture/all/Blog.png")),
        blog_button(TextureData("texture/all/blog_button.png")),
        input_back (TextureData("texture/all/input_back.png")),
        list       (TextureData("texture/all/list.png")),
        mask       (TextureData("texture/all/mask.png")),
        numbers    (TextureData("texture/all/numbers.png")),

        test(TextureData("texture/test.png")),
        elp_mask(TextureData("texture/elp_mask.png")),

        _1(TextureData("texture/1.png")),
        _2(TextureData("texture/2.png")),
        _3(TextureData("texture/3.png")),
        _4(TextureData("texture/4.png")),
        _5(TextureData("texture/5.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}