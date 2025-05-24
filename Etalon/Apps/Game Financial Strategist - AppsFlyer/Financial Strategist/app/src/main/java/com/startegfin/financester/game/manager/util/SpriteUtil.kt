package com.startegfin.financester.game.manager.util

import com.startegfin.financester.game.manager.SpriteManager

class SpriteUtil {

    class Splash {

        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(regionName)

        val back      = getRegion("back")
        val logo      = getRegion("logo")
        val prog      = getRegion("prog")
        val separator = getRegion("separator")

        val MASK = SpriteManager.EnumTexture.SPLASH_MASK.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)

        val back_def         = getRegion("back_def")
        val back_prs         = getRegion("back_prs")
        val circle_separator = getRegion("circle_separator")
        val dohod            = getRegion("dohod")
        val logo             = getRegion("logo")
        val p_and_d          = getRegion("p_and_d")
        val p_and_d_prs      = getRegion("p_and_d_prs")
        val p_back           = getRegion("p_back")
        val p_prog           = getRegion("p_prog")
        val p_separator      = getRegion("p_separator")
        val pls_def          = getRegion("pls_def")
        val pls_prs          = getRegion("pls_prs")
        val roshod           = getRegion("roshod")
        val save             = getRegion("save")
        val save_prs         = getRegion("save_prs")
        val september        = getRegion("september")
        val vse_trat_prs     = getRegion("vse_trat_prs")
        val vse_trat         = getRegion("vse_trat")
        val what             = getRegion("what")
        val ellips           = getRegion("ellips")

        val listRoshodIcon = List(9) { getRegion("r${it.inc()}") }
        val listDohodIcon  = List(3) { getRegion("d${it.inc()}") }
        val listColors     = List(6) { getRegion("${it.inc()}") }

        val ALL_ITEMS_PANEL   = SpriteManager.EnumTexture.ALL_ITEMS_PANEL.data.texture
        val MAIN_PANELS       = SpriteManager.EnumTexture.MAIN_PANELS.data.texture
        val MASK              = SpriteManager.EnumTexture.MASK.data.texture
        val MINUS_ITEMS_PANEL = SpriteManager.EnumTexture.MINUS_ITEMS_PANEL.data.texture
        val MINUS_PANEL       = SpriteManager.EnumTexture.MINUS_PANEL.data.texture
        val PLUS_ITEMS_PANEL  = SpriteManager.EnumTexture.PLUS_ITEMS_PANEL.data.texture
        val PLUS_PANEL        = SpriteManager.EnumTexture.PLUS_PANEL.data.texture
        val TRANSACTION_PANEL = SpriteManager.EnumTexture.TRANSACTION_PANEL.data.texture


    }

}