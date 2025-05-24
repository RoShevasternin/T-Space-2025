package com.liberator.wisoliter.game.manager

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
        L_background(TextureData("texture/loader/background.png")),
        L_grid      (TextureData("texture/loader/grid.png")),
        L_msek      (TextureData("texture/loader/msek.png")),

        long_mask     (TextureData("texture/all/long_mask.png")),
        mini_mask     (TextureData("texture/all/mini_mask.png")),
        pop_ended_army(TextureData("texture/all/pop_ended_army.png")),
        pop_greet     (TextureData("texture/all/pop_greet.png")),
        pop_need_xp   (TextureData("texture/all/pop_need_xp.png")),

        test   (TextureData("texture/test.png")),

        w1(TextureData("texture/all/world_background/w1.png")),
        w2(TextureData("texture/all/world_background/w2.png")),
        w3(TextureData("texture/all/world_background/w3.png")),
        w4(TextureData("texture/all/world_background/w4.png")),
        w5(TextureData("texture/all/world_background/w5.png")),
        w6(TextureData("texture/all/world_background/w6.png")),
        w7(TextureData("texture/all/world_background/w7.png")),
        w8(TextureData("texture/all/world_background/w8.png")),
        w9(TextureData("texture/all/world_background/w9.png")),

        c1  (TextureData("texture/all/country/c1.png")),
        c2  (TextureData("texture/all/country/c2.png")),
        c3  (TextureData("texture/all/country/c3.png")),
        c4  (TextureData("texture/all/country/c4.png")),
        c5  (TextureData("texture/all/country/c5.png")),
        c6  (TextureData("texture/all/country/c6.png")),
        c7  (TextureData("texture/all/country/c7.png")),
        c8  (TextureData("texture/all/country/c8.png")),
        c9  (TextureData("texture/all/country/c9.png")),
        c10 (TextureData("texture/all/country/c10.png")),
        c11 (TextureData("texture/all/country/c11.png")),
        c12 (TextureData("texture/all/country/c12.png")),
        c13 (TextureData("texture/all/country/c13.png")),
        c14 (TextureData("texture/all/country/c14.png")),
        c15 (TextureData("texture/all/country/c15.png")),
        c16 (TextureData("texture/all/country/c16.png")),
        c17 (TextureData("texture/all/country/c17.png")),
        c18 (TextureData("texture/all/country/c18.png")),
        c19 (TextureData("texture/all/country/c19.png")),
        c20 (TextureData("texture/all/country/c20.png")),
        c21 (TextureData("texture/all/country/c21.png")),
        c22 (TextureData("texture/all/country/c22.png")),
        c23 (TextureData("texture/all/country/c23.png")),
        c24 (TextureData("texture/all/country/c24.png")),
        c25 (TextureData("texture/all/country/c25.png")),
        c26 (TextureData("texture/all/country/c26.png")),
        c27 (TextureData("texture/all/country/c27.png")),
        c28 (TextureData("texture/all/country/c28.png")),
        c29 (TextureData("texture/all/country/c29.png")),
        c30 (TextureData("texture/all/country/c30.png")),

    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}