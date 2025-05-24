package com.tinfenker.capitalnoestroy.game.manager.util

import com.tinfenker.capitalnoestroy.game.manager.SpriteManager

class SpriteUtil {

    class Splash {

        private fun get9Path(regionName: String) = SpriteManager.EnumAtlas.SPLASH.data.atlas.createPatch(regionName)
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(regionName)

        val logo          = getRegion("logo")
        val progress      = getRegion("progress")
        val progress_back = getRegion("progress_back")

        val panel = get9Path("panel")

        val MASK = SpriteManager.EnumTexture.SPLASH_MASK.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)

        val _0_1m            = getRegion("_0_1m")
        val _0_12            = getRegion("_0_12")
        val _0_100           = getRegion("_0_100")
        val add_invest_def   = getRegion("add_invest_def")
        val add_invest_press = getRegion("add_invest_press")
        val circle_big       = getRegion("circle_big")
        val circle_mini      = getRegion("circle_mini")
        val done_def         = getRegion("done_def")
        val done_press       = getRegion("done_press")
        val header           = getRegion("header")
        val logo             = getRegion("logo")
        val home_check       = getRegion("home_check")
        val home_def         = getRegion("home_def")
        val invest_check     = getRegion("invest_check")
        val invest_def       = getRegion("invest_def")
        val panel_data       = getRegion("panel_data")
        val percent          = getRegion("percent")
        val period           = getRegion("period")
        val plus_def         = getRegion("plus_def")
        val plus_press       = getRegion("plus_press")
        val remove_def       = getRegion("remove_def")
        val remove_press     = getRegion("remove_press")
        val scroller         = getRegion("scroller")
        val see_all_def      = getRegion("see_all_def")
        val see_all_press    = getRegion("see_all_press")
        val shkala           = getRegion("shkala")
        val shkala_back      = getRegion("shkala_back")
        val summa            = getRegion("summa")
        val bottom_panel     = getRegion("bottom_panel")

        val PANEL_BIGGER   = SpriteManager.EnumTexture.PANEL_BIGGER.data.texture
        val BIGGER_INPUT   = SpriteManager.EnumTexture.BIGGER_INPUT.data.texture
        val SHKALA_MASK    = SpriteManager.EnumTexture.SHKALA_MASK.data.texture
        val TOP_INVEST     = SpriteManager.EnumTexture.TOP_INVEST.data.texture
        val PANEL_DATA_BIG = SpriteManager.EnumTexture.PANEL_DATA_BIG.data.texture


    }

}