package com.ayrym.inperdader.game.manager.util

import com.ayrym.inperdader.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val blesk    = getRegion("blesk")
          val coin     = getRegion("coin")
          val logo     = getRegion("logo")
          val panel    = getRegion("panel")
          val prog     = getRegion("prog")
          val prog_pan = getRegion("prog_pan")

          val background = SpriteManager.EnumTexture.L_background.data.texture
          val light      = SpriteManager.EnumTexture.L_light.data.texture
          val mask       = SpriteManager.EnumTexture.L_mask.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun get9patch(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val aurum         = getRegionAll("aurum")
          val back_def      = getRegionAll("back_def")
          val back_press    = getRegionAll("back_press")
          val circle_check  = getRegionAll("circle_check")
          val red           = getRegionAll("red")
          val circle_def    = getRegionAll("circle_def")
          val left_def      = getRegionAll("left_def")
          val left_press    = getRegionAll("left_press")
          val line          = getRegionAll("line")
          val right_def     = getRegionAll("right_def")
          val right_press   = getRegionAll("right_press")
          val timer         = getRegionAll("timer")
          val to_test_def   = getRegionAll("to_test_def")
          val to_test_press = getRegionAll("to_test_press")
          val top           = getRegionAll("top")

          val bottom = get9patch("bottom")

          // textures ------------------------------------------------------------------------------

          val MENU = SpriteManager.EnumTexture.MENU.data.texture
     }

}