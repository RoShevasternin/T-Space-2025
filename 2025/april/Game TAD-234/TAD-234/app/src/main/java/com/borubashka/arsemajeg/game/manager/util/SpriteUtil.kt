package com.borubashka.arsemajeg.game.manager.util

import com.borubashka.arsemajeg.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val plus = getRegion("plus")
          val r    = getRegion("r")

          val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val blue            = getRegionAll("blue")
          val btn_white_def   = getRegionAll("btn_white_def")
          val btn_white_press = getRegionAll("btn_white_press")
          val circle_red      = getRegionAll("circle_red")
          val back_def        = getRegionAll("back_def")
          val back_press      = getRegionAll("back_press")
          val green           = getRegionAll("green")
          val lock            = getRegionAll("lock")
          val progress_big    = getRegionAll("progress_big")
          val progress_mini   = getRegionAll("progress_mini")
          val red             = getRegionAll("red")
          val star_1          = getRegionAll("star_1")
          val star_2          = getRegionAll("star_2")
          val star_3          = getRegionAll("star_3")
          val white_back_big  = getRegionAll("white_back_big")
          val white_back_mini = getRegionAll("white_back_mini")

          val panel_white = getNine("panel_white")

          val listStars = listOf(star_1, star_2, star_3)

          // textures ------------------------------------------------------------------------------

          val MASK_BIG  = SpriteManager.EnumTexture.MASK_BIG.data.texture
          val MASK_MINI = SpriteManager.EnumTexture.MASK_MINI.data.texture

          private val MAP1      = SpriteManager.EnumTexture.MAP1.data.texture
          private val MAP2      = SpriteManager.EnumTexture.MAP2.data.texture

          private val RESULT1   = SpriteManager.EnumTexture.RESULT1.data.texture
          private val RESULT2   = SpriteManager.EnumTexture.RESULT2.data.texture
          private val RESULT3   = SpriteManager.EnumTexture.RESULT3.data.texture

          val listMap    = listOf(MAP1, MAP2)
          val listResult = listOf(RESULT1, RESULT2, RESULT3)

     }

}