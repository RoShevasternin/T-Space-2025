package com.lebany.lechebnik.game.manager

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
        LOADER(AtlasData("atlas/loader.atlas")),
        ALL   (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        LOADER_MASK(TextureData("texture/loader/mask.png")),

        MASK(TextureData("texture/all/mask.png")),

        DIALOG_DEPOSIT_1    (TextureData("texture/all/dialog/dialog_deposit_1.png")),
        DIALOG_DEPOSIT_2    (TextureData("texture/all/dialog/dialog_deposit_2.png")),
        DIALOG_DEPOSIT_3    (TextureData("texture/all/dialog/dialog_deposit_3.png")),
        DIALOG_DEPOSIT_4    (TextureData("texture/all/dialog/dialog_deposit_4.png")),
        DIALOG_INVESTMENTS_1(TextureData("texture/all/dialog/dialog_investments_1.png")),
        DIALOG_INVESTMENTS_2(TextureData("texture/all/dialog/dialog_investments_2.png")),
        DIALOG_INVESTMENTS_3(TextureData("texture/all/dialog/dialog_investments_3.png")),
        DIALOG_INVESTMENTS_4(TextureData("texture/all/dialog/dialog_investments_4.png")),
        DIALOG_SAVINGS_1    (TextureData("texture/all/dialog/dialog_savings_1.png")),
        DIALOG_SAVINGS_2    (TextureData("texture/all/dialog/dialog_savings_2.png")),
        DIALOG_SAVINGS_3    (TextureData("texture/all/dialog/dialog_savings_3.png")),
        DIALOG_SAVINGS_4    (TextureData("texture/all/dialog/dialog_savings_4.png")),
        NEXT_LVL_2          (TextureData("texture/all/dialog/next_lvl_2.png")),
        NEXT_LVL_3          (TextureData("texture/all/dialog/next_lvl_3.png")),
        NEXT_LVL_4          (TextureData("texture/all/dialog/next_lvl_4.png")),

        IMPROVE_PANEL_1(TextureData("texture/all/improvements/improve_panel_1.png")),
        IMPROVE_PANEL_2(TextureData("texture/all/improvements/improve_panel_2.png")),
        IMPROVE_PANEL_3(TextureData("texture/all/improvements/improve_panel_3.png")),
        IMPROVE_PANEL_4(TextureData("texture/all/improvements/improve_panel_4.png")),

        INVEST_PANEL_1(TextureData("texture/all/investments/invest_panel_1.png")),
        INVEST_PANEL_2(TextureData("texture/all/investments/invest_panel_2.png")),
        INVEST_PANEL_3(TextureData("texture/all/investments/invest_panel_3.png")),
        INVEST_PANEL_4(TextureData("texture/all/investments/invest_panel_4.png")),

        PROFESSION_1(TextureData("texture/all/profession/profession_1.png")),
        PROFESSION_2(TextureData("texture/all/profession/profession_2.png")),
        PROFESSION_3(TextureData("texture/all/profession/profession_3.png")),
        PROFESSION_4(TextureData("texture/all/profession/profession_4.png")),


    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}