package com.basarili.baslangisc.game.manager.util

import com.basarili.baslangisc.game.manager.SpriteManager

class SpriteUtil {

    class Loader {

        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(regionName)

        val indir  = getRegion("indir")
        val loader = getRegion("loader")
        val logo   = getRegion("logo")

        val background_loader = SpriteManager.EnumTexture.background_loader.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)
        private fun getRegionBuilding(regionName: String) = SpriteManager.EnumAtlas.BUILDINGS.data.atlas.findRegion(regionName)
        private fun getRegionDetails(regionName: String) = SpriteManager.EnumAtlas.DETAILS.data.atlas.findRegion(regionName)

        val balance_panel_center = getRegion("balance_panel_center")
        val balance_panel_left   = getRegion("balance_panel_left")
        val btn_hello_def        = getRegion("btn_hello_def")
        val btn_hello_press      = getRegion("btn_hello_press")
        val closed               = getRegion("closed")
        val dukan_def            = getRegion("dukan_def")
        val dukan_press          = getRegion("dukan_press")
        val gari_gel_def         = getRegion("gari_gel_def")
        val gari_gel_press       = getRegion("gari_gel_press")
        val gas                  = getRegion("gas")
        val gaz_satmak_def       = getRegion("gaz_satmak_def")
        val gaz_satmak_press     = getRegion("gaz_satmak_press")
        val hand_big             = getRegion("hand_big")
        val hand_mini            = getRegion("hand_mini")
        val panel_lvl            = getRegion("panel_lvl")
        val text_hello           = getRegion("text_hello")

        val building_1 = getRegionBuilding("1")
        val building_2 = getRegionBuilding("2")
        val building_3 = getRegionBuilding("3")
        val building_4 = getRegionBuilding("4")
        val building_5 = getRegionBuilding("5")
        val building_6 = getRegionBuilding("6")

        val listDetails = List(3) { getRegionDetails("d${it.inc()}") }

        val background_game = SpriteManager.EnumTexture.background_game.data.texture
        val market  = SpriteManager.EnumTexture.market.data.texture
        val pop_1   = SpriteManager.EnumTexture.pop_1.data.texture
        val pop_2   = SpriteManager.EnumTexture.pop_2.data.texture
        val pop_3   = SpriteManager.EnumTexture.pop_3.data.texture


    }

}