package com.smarters.moneyes.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.smarters.moneyes.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")

          val background = SpriteManager.EnumTexture.L_Loader.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val ans_check   = getRegion("ans_check")
          val ans_def     = getRegion("ans_def")
          val done        = getRegion("done")
          val frame       = getRegion("frame")
          val gray        = getRegion("gray")
          val green       = getRegion("green")
          val next_def    = getRegion("next_def")
          val next_press  = getRegion("next_press")
          val panel       = getRegion("panel")
          val start_def   = getRegion("start_def")
          val start_press = getRegion("start_press")

          // 9 path ------------------------------------------------------------------------------

          //val nine_frame = get9Path("nine_frame")

          // textures ------------------------------------------------------------------------------

          val Back    = SpriteManager.EnumTexture.Back.data.texture
          val Konfety = SpriteManager.EnumTexture.Konfety.data.texture

          val r1  = SpriteManager.EnumTexture.r1.data.texture
          val r5  = SpriteManager.EnumTexture.r5.data.texture
          val r10 = SpriteManager.EnumTexture.r10.data.texture

          private val _1  = SpriteManager.EnumTexture._1.data.texture
          private val _2  = SpriteManager.EnumTexture._2.data.texture
          private val _3  = SpriteManager.EnumTexture._3.data.texture
          private val _4  = SpriteManager.EnumTexture._4.data.texture
          private val _5  = SpriteManager.EnumTexture._5.data.texture
          private val _6  = SpriteManager.EnumTexture._6.data.texture
          private val _7  = SpriteManager.EnumTexture._7.data.texture
          private val _8  = SpriteManager.EnumTexture._8.data.texture
          private val _9  = SpriteManager.EnumTexture._9.data.texture
          private val _10 = SpriteManager.EnumTexture._10.data.texture
          private val _11 = SpriteManager.EnumTexture._11.data.texture
          private val _12 = SpriteManager.EnumTexture._12.data.texture

          val listLevel = listOf(
               _1, _2, _3, _7, _8, _9,
               _4, _5, _6, _10, _11, _12,
          )

          val d1 = SpriteManager.EnumTexture.d1.data.texture
          val d2 = SpriteManager.EnumTexture.d2.data.texture
          val d3 = SpriteManager.EnumTexture.d3.data.texture
     }

}