package com.traoya.yatirimya.game.manager.util

import com.traoya.yatirimya.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")
          val red  = getRegion("red")
          val trao = getRegion("trao")

          val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture
          val MASKA      = SpriteManager.EnumTexture.L_MASKA.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val barel       = getRegionAll("barel")
          val btn_def     = getRegionAll("btn_def")
          val btn_press   = getRegionAll("btn_press")
          val gel_def     = getRegionAll("gel_def")
          val gel_press   = getRegionAll("gel_press")
          val nafta_def   = getRegionAll("nafta_def")
          val nafta_press = getRegionAll("nafta_press")
          val rd_def      = getRegionAll("rd_def")
          val rd_press    = getRegionAll("rd_press")
          val toper       = getRegionAll("toper")
          val wht         = getRegionAll("wht")

          // textures ------------------------------------------------------------------------------

          val BACKGROUND_GAME = SpriteManager.EnumTexture.BACKGROUND_GAME.data.texture
          val BACKGROUND_SHOP = SpriteManager.EnumTexture.BACKGROUND_SHOP.data.texture
          val BUTTONS         = SpriteManager.EnumTexture.BUTTONS.data.texture
          val T1              = SpriteManager.EnumTexture.T1.data.texture
          val T2              = SpriteManager.EnumTexture.T2.data.texture
          val T3              = SpriteManager.EnumTexture.T3.data.texture

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

          val listDashboard = listOf(
               _1, _2, _3, _4, _5,
               _6, _7, _8, _9, _10,
          )

     }

}