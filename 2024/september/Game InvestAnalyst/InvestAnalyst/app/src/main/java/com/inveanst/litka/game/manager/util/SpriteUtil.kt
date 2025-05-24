package com.inveanst.litka.game.manager.util

import com.inveanst.litka.game.manager.SpriteManager

class SpriteUtil {

    class Splash {
        // val SPLASH_BACKGROUND = SpriteManager.EnumTexture.SPLASH_BACKGROUND.data.texture

        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(regionName)

        val loader        = getRegion("loader")
        val logo          = getRegion("logo")
        val panel         = getRegion("panel")
        val post_gotovo   = getRegion("post_gotovo")
        val proidite_test = getRegion("proidite_test")
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)
        private fun getNine(nineName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(nineName)

        val ao_def      = getRegion("ao_def")
        val ao_prs      = getRegion("ao_prs")
        val arr_def     = getRegion("arr_def")
        val arr_prs     = getRegion("arr_prs")
        val box_check   = getRegion("box_check")
        val box_red     = getRegion("box_red")
        val box_def     = getRegion("box_def")
        val gotest_def  = getRegion("gotest_def")
        val gotest_prs  = getRegion("gotest_prs")
        val oi_def      = getRegion("oi_def")
        val oi_prs      = getRegion("oi_prs")
        val rd_def      = getRegion("rd_def")
        val rd_prs      = getRegion("rd_prs")
        val select_tema = getRegion("select_tema")

        val listIcon   = List(3) { getRegion("i${it.inc()}") }
        val listResult = List(3) { getRegion("r${it.inc()}") }


        val panel_9       = getNine("panel_9")
        val frame_9_gray  = getNine("frame_9_gray")
        val frame_9_green = getNine("frame_9_green")
        val frame_9_red   = getNine("frame_red")


    }

}