package com.proccaptald.proffesionalestic.game.manager.util

import com.proccaptald.proffesionalestic.game.manager.SpriteManager

class SpriteUtil {

    class Splash {

        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(regionName)

        val logo     = getRegion("logo")
        val progress = getRegion("progress")
        val white    = getRegion("white")

        val BLACK      = SpriteManager.EnumTexture.BLACK.data.texture
        val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)

        val add_pers_def     = getRegion("add_pers_def")
        val add_pers_press   = getRegion("add_pers_press")
        val arr_left_def     = getRegion("arr_left_def")
        val arr_left_press   = getRegion("arr_left_press")
        val arr_right_def    = getRegion("arr_right_def")
        val arr_right_press  = getRegion("arr_right_press")
        val back_def         = getRegion("back_def")
        val back_press       = getRegion("back_press")
        val coin             = getRegion("coin")
        val cube             = getRegion("cube")
        val exit_def         = getRegion("exit_def")
        val exit_press       = getRegion("exit_press")
        val bros_def         = getRegion("bros_def")
        val bros_press       = getRegion("bros_press")
        val info_def         = getRegion("info_def")
        val info_press       = getRegion("info_press")
        val pers_panel       = getRegion("pers_panel")
        val play_def         = getRegion("play_def")
        val play_press       = getRegion("play_press")
        val pp               = getRegion("pp")
        val restart_check    = getRegion("restart_check")
        val restart_def      = getRegion("restart_def")
        val rules            = getRegion("rules")
        val rules_def        = getRegion("rules_def")
        val rules_press      = getRegion("rules_press")
        val sound_check      = getRegion("sound_check")
        val sound_def        = getRegion("sound_def")
        val suitcase         = getRegion("suitcase")
        val target           = getRegion("target")
        val target_panel     = getRegion("target_panel")
        val to_game_def      = getRegion("to_game_def")
        val to_game_press    = getRegion("to_game_press")
        val winner           = getRegion("winner")

        val listPers     = List(8) { getRegion("pers_${it.inc()}") }
        val listPersIcon = List(8) { getRegion("pers_icon_${it.inc()}") }

        val CIRCLES = SpriteManager.EnumTexture.CIRCLES.data.texture
        val PANEL   = SpriteManager.EnumTexture.PANEL.data.texture
        val PP_TEXT = SpriteManager.EnumTexture.PP_TEXT.data.texture
        val RULES   = SpriteManager.EnumTexture.RULES.data.texture


    }

}