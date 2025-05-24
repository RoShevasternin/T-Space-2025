package com.jobzone.cobzone.game.manager

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

        // Loader ---------------------------------------------------------------------------

        L_background (TextureData("textures/loader/background_loader.png")),
        L_mask       (TextureData("textures/loader/mask.png")),

        // All ---------------------------------------------------------------------------

        A_gear      (TextureData("textures/all/gear.png")),
        A_gradik    (TextureData("textures/all/gradik.png")),
        A_list_1    (TextureData("textures/all/list_1.png")),
        A_list_2    (TextureData("textures/all/list_2.png")),
        A_lupa      (TextureData("textures/all/lupa.png")),
        A_oklik     (TextureData("textures/all/oklik.png")),
        A_pers      (TextureData("textures/all/pers.png")),
        A_polu_chel (TextureData("textures/all/polu_chel.png")),
        A_rrr       (TextureData("textures/all/rrr.png")),

        A_v1  (TextureData("textures/all/vacancy/v1.png")),
        A_v2  (TextureData("textures/all/vacancy/v2.png")),
        A_v3  (TextureData("textures/all/vacancy/v3.png")),
        A_v4  (TextureData("textures/all/vacancy/v4.png")),
        A_v5  (TextureData("textures/all/vacancy/v5.png")),
        A_v6  (TextureData("textures/all/vacancy/v6.png")),
        A_v7  (TextureData("textures/all/vacancy/v7.png")),
        A_v8  (TextureData("textures/all/vacancy/v8.png")),
        A_v9  (TextureData("textures/all/vacancy/v9.png")),
        A_v10 (TextureData("textures/all/vacancy/v10.png")),

        A_vacan1  (TextureData("textures/all/details/vacan1.png")),
        A_vacan2  (TextureData("textures/all/details/vacan2.png")),
        A_vacan3  (TextureData("textures/all/details/vacan3.png")),
        A_vacan4  (TextureData("textures/all/details/vacan4.png")),
        A_vacan5  (TextureData("textures/all/details/vacan5.png")),
        A_vacan6  (TextureData("textures/all/details/vacan6.png")),
        A_vacan7  (TextureData("textures/all/details/vacan7.png")),
        A_vacan8  (TextureData("textures/all/details/vacan8.png")),
        A_vacan9  (TextureData("textures/all/details/vacan9.png")),
        A_vacan10 (TextureData("textures/all/details/vacan10.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}