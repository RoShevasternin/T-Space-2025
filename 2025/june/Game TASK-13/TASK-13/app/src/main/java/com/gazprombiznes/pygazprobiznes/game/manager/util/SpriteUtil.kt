package com.gazprombiznes.pygazprobiznes.game.manager.util

import com.gazprombiznes.pygazprobiznes.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val blue  = getRegion("blue")
          val logo  = getRegion("logo")
          val start = getRegion("start")

          val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture
          val MASKA      = SpriteManager.EnumTexture.L_MASKA.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val balance     = getRegionAll("balance")
          val bluer       = getRegionAll("bluer")
          val molotok     = getRegionAll("molotok")
          val oil         = getRegionAll("oil")
          val start_def   = getRegionAll("start_def")
          val start_press = getRegionAll("start_press")
          val whiter      = getRegionAll("whiter")

          // textures ------------------------------------------------------------------------------

          val BAGA      = SpriteManager.EnumTexture.BAGA.data.texture
          val DED_LEFT  = SpriteManager.EnumTexture.DED_LEFT.data.texture
          val DED_RIGHT = SpriteManager.EnumTexture.DED_RIGHT.data.texture
          val DEROP     = SpriteManager.EnumTexture.DEROP.data.texture
          val MARSK     = SpriteManager.EnumTexture.MARSK.data.texture
          val PERRDO    = SpriteManager.EnumTexture.PERRDO.data.texture

          private val _1  = SpriteManager.EnumTexture._1.data.texture
          private val _2  = SpriteManager.EnumTexture._2.data.texture
          private val _3  = SpriteManager.EnumTexture._3.data.texture
          private val _4  = SpriteManager.EnumTexture._4.data.texture
          private val _5  = SpriteManager.EnumTexture._5.data.texture
          private val _6  = SpriteManager.EnumTexture._6.data.texture
          private val _7  = SpriteManager.EnumTexture._7.data.texture

          val listItemeser = listOf(
               _1, _2, _3, _4, _5, _6, _7,
          )

     }

}