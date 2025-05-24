package com.gosinventarytet.debagovich.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gosinventarytet.debagovich.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val goslogo = getRegion("goslogo")
          val progo   = getRegion("progo")

          val light = SpriteManager.EnumTexture.L_light.data.texture
          val mask  = SpriteManager.EnumTexture.L_mask.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val all_def       = getRegionAll("all_def")
          val all_press     = getRegionAll("all_press")
          val ans_def       = getRegionAll("ans_def")
          val ans_press     = getRegionAll("ans_press")
          val back_def      = getRegionAll("back_def")
          val back_press    = getRegionAll("back_press")
          val circle        = getRegionAll("circle")
          val quiz_01_def   = getRegionAll("quiz_01_def")
          val quiz_01_press = getRegionAll("quiz_01_press")
          val quiz_02_def   = getRegionAll("quiz_02_def")
          val quiz_02_press = getRegionAll("quiz_02_press")
          val quiz_03_def   = getRegionAll("quiz_03_def")
          val quiz_03_press = getRegionAll("quiz_03_press")
          val random_def    = getRegionAll("random_def")
          val random_press  = getRegionAll("random_press")
          val select        = getRegionAll("select")
          val title         = getRegionAll("title")
          val to_main_def   = getRegionAll("to_main_def")
          val to_main_press = getRegionAll("to_main_press")

          //val listItems = List(9) { getRegionItems("${it.inc()}") }

          // textures ------------------------------------------------------------------------------

          val STAR = SpriteManager.EnumTexture.STAR.data.texture
     }

}