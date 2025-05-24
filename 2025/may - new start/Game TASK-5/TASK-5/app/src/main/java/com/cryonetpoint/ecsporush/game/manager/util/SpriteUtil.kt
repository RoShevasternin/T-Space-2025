package com.cryonetpoint.ecsporush.game.manager.util

import com.cryonetpoint.ecsporush.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")

          val GLOW = SpriteManager.EnumTexture.L_GLOW.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val all_def       = getRegionAll("all_def")
          val all_press     = getRegionAll("all_press")
          val answer_def    = getRegionAll("answer_def")
          val answer_press  = getRegionAll("answer_press")
          val back_def      = getRegionAll("back_def")
          val back_press    = getRegionAll("back_press")
          val box_def       = getRegionAll("box_def")
          val box_press     = getRegionAll("box_press")
          val next_def      = getRegionAll("next_def")
          val next_press    = getRegionAll("next_press")
          val qwiz_1_def    = getRegionAll("qwiz_1_def")
          val qwiz_1_press  = getRegionAll("qwiz_1_press")
          val qwiz_2_def    = getRegionAll("qwiz_2_def")
          val qwiz_2_press  = getRegionAll("qwiz_2_press")
          val qwiz_3_def    = getRegionAll("qwiz_3_def")
          val qwiz_3_press  = getRegionAll("qwiz_3_press")
          val random_def    = getRegionAll("random_def")
          val random_press  = getRegionAll("random_press")
          val to_main_def   = getRegionAll("to_main_def")
          val to_main_press = getRegionAll("to_main_press")

          // textures ------------------------------------------------------------------------------

          val RESULT_TEXT = SpriteManager.EnumTexture.RESULT_TEXT.data.texture
          val STAR        = SpriteManager.EnumTexture.STAR.data.texture

          private val _1 = SpriteManager.EnumTexture._1.data.texture
          private val _2 = SpriteManager.EnumTexture._2.data.texture
          private val _3 = SpriteManager.EnumTexture._3.data.texture

          val listDashboard = listOf(_1, _2, _3)

     }

}