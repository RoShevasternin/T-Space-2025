package com.clickandbuild.motors.game.manager

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
        LOADER (AtlasData("atlas/loader.atlas")),
        ALL    (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        background_1(TextureData("texture/loader/background_1.png")),
        loader_mask (TextureData("texture/loader/loader_mask.png")),

        background_2(TextureData("texture/all/background_2.png")),
        background_3(TextureData("texture/all/background_3.png")),
        text_3      (TextureData("texture/all/text_3.png")),
        mask        (TextureData("texture/all/mask.png")),

        t0      (TextureData("texture/all/tehnika/t0.png")),
        t1_0    (TextureData("texture/all/tehnika/t1_0.png")),
        t1_1    (TextureData("texture/all/tehnika/t1_1.png")),
        t1_2    (TextureData("texture/all/tehnika/t1_2.png")),
        t2_0    (TextureData("texture/all/tehnika/t2_0.png")),
        t2_1    (TextureData("texture/all/tehnika/t2_1.png")),
        t2_2    (TextureData("texture/all/tehnika/t2_2.png")),
        t3      (TextureData("texture/all/tehnika/t3.png")),
        t4_0    (TextureData("texture/all/tehnika/t4_0.png")),
        t4_1    (TextureData("texture/all/tehnika/t4_1.png")),
        t4_2    (TextureData("texture/all/tehnika/t4_2.png")),
        t5_0_0  (TextureData("texture/all/tehnika/t5_0_0.png")),
        t5_0_1  (TextureData("texture/all/tehnika/t5_0_1.png")),
        t5_0_2  (TextureData("texture/all/tehnika/t5_0_2.png")),
        t5_1_0  (TextureData("texture/all/tehnika/t5_1_0.png")),
        t5_1_1  (TextureData("texture/all/tehnika/t5_1_1.png")),
        t5_1_2  (TextureData("texture/all/tehnika/t5_1_2.png")),
        t5_2_0  (TextureData("texture/all/tehnika/t5_2_0.png")),
        t5_2_1  (TextureData("texture/all/tehnika/t5_2_1.png")),
        t5_2_2  (TextureData("texture/all/tehnika/t5_2_2.png")),
        t6_0_0  (TextureData("texture/all/tehnika/t6_0_0.png")),
        t6_0_1  (TextureData("texture/all/tehnika/t6_0_1.png")),
        t6_0_2  (TextureData("texture/all/tehnika/t6_0_2.png")),
        t6_1_0  (TextureData("texture/all/tehnika/t6_1_0.png")),
        t6_1_1  (TextureData("texture/all/tehnika/t6_1_1.png")),
        t6_1_2  (TextureData("texture/all/tehnika/t6_1_2.png")),
        t6_2_0  (TextureData("texture/all/tehnika/t6_2_0.png")),
        t6_2_1  (TextureData("texture/all/tehnika/t6_2_1.png")),
        t6_2_2  (TextureData("texture/all/tehnika/t6_2_2.png")),
        t7      (TextureData("texture/all/tehnika/t7.png")),
        t8_0_0  (TextureData("texture/all/tehnika/t8_0_0.png")),
        t8_0_1  (TextureData("texture/all/tehnika/t8_0_1.png")),
        t8_0_2  (TextureData("texture/all/tehnika/t8_0_2.png")),
        t8_1_0  (TextureData("texture/all/tehnika/t8_1_0.png")),
        t8_1_1  (TextureData("texture/all/tehnika/t8_1_1.png")),
        t8_1_2  (TextureData("texture/all/tehnika/t8_1_2.png")),
        t8_2_0  (TextureData("texture/all/tehnika/t8_2_0.png")),
        t8_2_1  (TextureData("texture/all/tehnika/t8_2_1.png")),
        t8_2_2  (TextureData("texture/all/tehnika/t8_2_2.png")),
        t9_0_0  (TextureData("texture/all/tehnika/t9_0_0.png")),
        t9_0_1  (TextureData("texture/all/tehnika/t9_0_1.png")),
        t9_0_2  (TextureData("texture/all/tehnika/t9_0_2.png")),
        t9_1_0  (TextureData("texture/all/tehnika/t9_1_0.png")),
        t9_1_1  (TextureData("texture/all/tehnika/t9_1_1.png")),
        t9_1_2  (TextureData("texture/all/tehnika/t9_1_2.png")),
        t9_2_0  (TextureData("texture/all/tehnika/t9_2_0.png")),
        t9_2_1  (TextureData("texture/all/tehnika/t9_2_1.png")),
        t9_2_2  (TextureData("texture/all/tehnika/t9_2_2.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}