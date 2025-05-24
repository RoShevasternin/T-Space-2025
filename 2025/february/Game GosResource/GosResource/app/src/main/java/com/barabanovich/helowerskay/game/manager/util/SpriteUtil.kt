package com.barabanovich.helowerskay.game.manager.util

import com.barabanovich.helowerskay.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val gos_res = getRegion("gos_res")
          val logo    = getRegion("logo")

          val background_1 = SpriteManager.EnumTexture.L_background_1.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9patch(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val back_def    = getRegionAll("back_def")
          val back_press  = getRegionAll("back_press")
          val green       = getRegionAll("green")
          val next_def    = getRegionAll("next_def")
          val next_press  = getRegionAll("next_press")
          val red         = getRegionAll("red")
          val reset_def   = getRegionAll("reset_def")
          val reset_press = getRegionAll("reset_press")
          val theme       = getRegionAll("theme")

          //val bottom = get9patch("bottom")

          // textures ------------------------------------------------------------------------------

          val background_2 = SpriteManager.EnumTexture.background_2.data.texture
          val _1_def       = SpriteManager.EnumTexture._1_def.data.texture
          val _1_press     = SpriteManager.EnumTexture._1_press.data.texture
          val _2_def       = SpriteManager.EnumTexture._2_def.data.texture
          val _2_press     = SpriteManager.EnumTexture._2_press.data.texture
          val _3_def       = SpriteManager.EnumTexture._3_def.data.texture
          val _3_press     = SpriteManager.EnumTexture._3_press.data.texture
          val _4_def       = SpriteManager.EnumTexture._4_def.data.texture
          val _4_press     = SpriteManager.EnumTexture._4_press.data.texture

          val forma        = SpriteManager.EnumTexture.forma.data.texture
          val prognoz      = SpriteManager.EnumTexture.prognoz.data.texture

          private val start   = SpriteManager.EnumTexture.start.data.texture
          private val znatok  = SpriteManager.EnumTexture.znatok.data.texture
          private val ekspert = SpriteManager.EnumTexture.ekspert.data.texture

          val listResult = listOf(start, znatok, ekspert)
     }

}