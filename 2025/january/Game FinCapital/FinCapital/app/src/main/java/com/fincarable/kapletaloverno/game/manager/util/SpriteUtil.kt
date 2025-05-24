package com.fincarable.kapletaloverno.game.manager.util

import com.fincarable.kapletaloverno.game.manager.SpriteManager

class SpriteUtil {

    class Loader {

        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(regionName)

        val loader_frame    = getRegion("loader_frame")
        val loader_logo     = getRegion("loader_logo")
        val loader_progress = getRegion("loader_progress")

        val background_1 = SpriteManager.EnumTexture.background_1.data.texture
        val loader_mask  = SpriteManager.EnumTexture.loader_mask.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)

        val attention       = getRegion("attention")
        val blue_prog       = getRegion("blue_prog")
        val chose_color_bwb = getRegion("chose_color_bwb")
        val chose_color_rbg = getRegion("chose_color_rbg")
        val create_def      = getRegion("create_def")
        val create_press    = getRegion("create_press")
        val logo            = getRegion("logo")
        val next_def        = getRegion("next_def")
        val next_press      = getRegion("next_press")
        val orange_prog     = getRegion("orange_prog")
        val order           = getRegion("order")
        val selector        = getRegion("selector")
        val sell_def        = getRegion("sell_def")
        val sell_press      = getRegion("sell_press")
        val text_1          = getRegion("text_1")
        val text_2          = getRegion("text_2")
        val to_game_def     = getRegion("to_game_def")
        val to_game_press   = getRegion("to_game_press")
        val try_again_def   = getRegion("try_again_def")
        val try_again_press = getRegion("try_again_press")
        val tutorial_order  = getRegion("tutorial_order")
        val br              = getRegion("br")
        val wh              = getRegion("wh")
        val bl              = getRegion("bl")
        val r               = getRegion("r")
        val b               = getRegion("b")
        val g               = getRegion("g")

        val listColorBWB = listOf(br, wh, bl)
        val listColorRBG = listOf(r, b, g)

        val background_2 = SpriteManager.EnumTexture.background_2.data.texture
        val background_3 = SpriteManager.EnumTexture.background_3.data.texture
        val text_3       = SpriteManager.EnumTexture.text_3.data.texture
        val mask         = SpriteManager.EnumTexture.mask.data.texture

        // Technika --------------------------------------------------------------

        val t0     = SpriteManager.EnumTexture.t0.data.texture
        val t1_0   = SpriteManager.EnumTexture.t1_0.data.texture
        val t1_1   = SpriteManager.EnumTexture.t1_1.data.texture
        val t1_2   = SpriteManager.EnumTexture.t1_2.data.texture
        val t2_0   = SpriteManager.EnumTexture.t2_0.data.texture
        val t2_1   = SpriteManager.EnumTexture.t2_1.data.texture
        val t2_2   = SpriteManager.EnumTexture.t2_2.data.texture
        val t3     = SpriteManager.EnumTexture.t3.data.texture
        val t4_0   = SpriteManager.EnumTexture.t4_0.data.texture
        val t4_1   = SpriteManager.EnumTexture.t4_1.data.texture
        val t4_2   = SpriteManager.EnumTexture.t4_2.data.texture
        val t5_0_0 = SpriteManager.EnumTexture.t5_0_0.data.texture
        val t5_0_1 = SpriteManager.EnumTexture.t5_0_1.data.texture
        val t5_0_2 = SpriteManager.EnumTexture.t5_0_2.data.texture
        val t5_1_0 = SpriteManager.EnumTexture.t5_1_0.data.texture
        val t5_1_1 = SpriteManager.EnumTexture.t5_1_1.data.texture
        val t5_1_2 = SpriteManager.EnumTexture.t5_1_2.data.texture
        val t5_2_0 = SpriteManager.EnumTexture.t5_2_0.data.texture
        val t5_2_1 = SpriteManager.EnumTexture.t5_2_1.data.texture
        val t5_2_2 = SpriteManager.EnumTexture.t5_2_2.data.texture
        val t6_0_0 = SpriteManager.EnumTexture.t6_0_0.data.texture
        val t6_0_1 = SpriteManager.EnumTexture.t6_0_1.data.texture
        val t6_0_2 = SpriteManager.EnumTexture.t6_0_2.data.texture
        val t6_1_0 = SpriteManager.EnumTexture.t6_1_0.data.texture
        val t6_1_1 = SpriteManager.EnumTexture.t6_1_1.data.texture
        val t6_1_2 = SpriteManager.EnumTexture.t6_1_2.data.texture
        val t6_2_0 = SpriteManager.EnumTexture.t6_2_0.data.texture
        val t6_2_1 = SpriteManager.EnumTexture.t6_2_1.data.texture
        val t6_2_2 = SpriteManager.EnumTexture.t6_2_2.data.texture
        val t7     = SpriteManager.EnumTexture.t7.data.texture
        val t8_0_0 = SpriteManager.EnumTexture.t8_0_0.data.texture
        val t8_0_1 = SpriteManager.EnumTexture.t8_0_1.data.texture
        val t8_0_2 = SpriteManager.EnumTexture.t8_0_2.data.texture
        val t8_1_0 = SpriteManager.EnumTexture.t8_1_0.data.texture
        val t8_1_1 = SpriteManager.EnumTexture.t8_1_1.data.texture
        val t8_1_2 = SpriteManager.EnumTexture.t8_1_2.data.texture
        val t8_2_0 = SpriteManager.EnumTexture.t8_2_0.data.texture
        val t8_2_1 = SpriteManager.EnumTexture.t8_2_1.data.texture
        val t8_2_2 = SpriteManager.EnumTexture.t8_2_2.data.texture
        val t9_0_0 = SpriteManager.EnumTexture.t9_0_0.data.texture
        val t9_0_1 = SpriteManager.EnumTexture.t9_0_1.data.texture
        val t9_0_2 = SpriteManager.EnumTexture.t9_0_2.data.texture
        val t9_1_0 = SpriteManager.EnumTexture.t9_1_0.data.texture
        val t9_1_1 = SpriteManager.EnumTexture.t9_1_1.data.texture
        val t9_1_2 = SpriteManager.EnumTexture.t9_1_2.data.texture
        val t9_2_0 = SpriteManager.EnumTexture.t9_2_0.data.texture
        val t9_2_1 = SpriteManager.EnumTexture.t9_2_1.data.texture
        val t9_2_2 = SpriteManager.EnumTexture.t9_2_2.data.texture

        val listT_1 = listOf(t1_0, t1_1, t1_2)
        val listT_2 = listOf(t2_0, t2_1, t2_2)
        val listT_4 = listOf(t4_0, t4_1, t4_2)
        val listT_5 = listOf(
            listOf(t5_0_0, t5_0_1, t5_0_2),
            listOf(t5_1_0, t5_1_1, t5_1_2),
            listOf(t5_2_0, t5_2_1, t5_2_2),
        )
        val listT_6 = listOf(
            listOf(t6_0_0, t6_0_1, t6_0_2),
            listOf(t6_1_0, t6_1_1, t6_1_2),
            listOf(t6_2_0, t6_2_1, t6_2_2),
        )
        val listT_8 = listOf(
            listOf(t8_0_0, t8_0_1, t8_0_2),
            listOf(t8_1_0, t8_1_1, t8_1_2),
            listOf(t8_2_0, t8_2_1, t8_2_2),
        )
        val listT_9 = listOf(
            listOf(t9_0_0, t9_0_1, t9_0_2),
            listOf(t9_1_0, t9_1_1, t9_1_2),
            listOf(t9_2_0, t9_2_1, t9_2_2),
        )


    }

}