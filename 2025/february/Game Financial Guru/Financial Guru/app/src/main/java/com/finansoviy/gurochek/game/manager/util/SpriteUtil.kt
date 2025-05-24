package com.finansoviy.gurochek.game.manager.util

import com.finansoviy.gurochek.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")
          val text = getRegion("text")

          //val mask = SpriteManager.EnumTexture.L_mask.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9patch(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val catg        = getRegionAll("catg")
          val green       = getRegionAll("green")
          val next_def    = getRegionAll("next_def")
          val next_press  = getRegionAll("next_press")
          val red         = getRegionAll("red")
          val reset_def   = getRegionAll("reset_def")
          val reset_press = getRegionAll("reset_press")
          val start_def   = getRegionAll("start_def")
          val start_press = getRegionAll("start_press")
          val x_def       = getRegionAll("x_def")
          val x_press     = getRegionAll("x_press")

          //val bottom = get9patch("bottom")

          // textures ------------------------------------------------------------------------------

          private val _1 = SpriteManager.EnumTexture._1.data.texture
          private val _2 = SpriteManager.EnumTexture._2.data.texture
          private val _3 = SpriteManager.EnumTexture._3.data.texture
          private val _4 = SpriteManager.EnumTexture._4.data.texture
          private val _5 = SpriteManager.EnumTexture._5.data.texture

          private val r1 = SpriteManager.EnumTexture.r1.data.texture
          private val r2 = SpriteManager.EnumTexture.r2.data.texture
          private val r3 = SpriteManager.EnumTexture.r3.data.texture

          val game  = SpriteManager.EnumTexture.game.data.texture
          val photo = SpriteManager.EnumTexture.photo.data.texture

          val listItems  = listOf(_1, _2, _3, _4, _5)
          val listResult = listOf(r1, r2, r3)
     }

}