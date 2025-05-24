package com.cburaktev.klavregasd.game.manager.util

import com.cburaktev.klavregasd.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("loader")
          val logo   = getRegion("logo")

          val BACKGROUND_LOADER = SpriteManager.EnumTexture.L_BACKGROUND_LOADER.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegionCategory(name: String): TextureRegion = SpriteManager.EnumAtlas.CATEGORY.data.atlas.findRegion(name)
          private fun getRegionIcon(name: String): TextureRegion = SpriteManager.EnumAtlas.ICONS.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val dohod      = getRegionAll("dohod")
          val green      = getRegionAll("green")
          val loga       = getRegionAll("loga")
          val next_def   = getRegionAll("next_def")
          val next_press = getRegionAll("next_press")
          val plus_def   = getRegionAll("plus_def")
          val plus_press = getRegionAll("plus_press")
          val rashod     = getRegionAll("rashod")
          val red        = getRegionAll("red")
          val top        = getRegionAll("top")
          val white      = getRegionAll("white")
          val x_def      = getRegionAll("x_def")
          val x_press    = getRegionAll("x_press")
          val save_def   = getRegionAll("save_def")
          val save_press = getRegionAll("save_press")

          val listCategory = List(15) { getRegionCategory(it.inc().toString()) }
          val listIcon     = List(15) { getRegionIcon(it.inc().toString()) }

          // textures ------------------------------------------------------------------------------
          val BACKGROUND_PREVIEW = SpriteManager.EnumTexture.BACKGROUND_PREVIEW.data.texture
          val BALANCEG           = SpriteManager.EnumTexture.BALANCEG.data.texture
          val BUTTONS            = SpriteManager.EnumTexture.BUTTONS.data.texture
          val INPUTE             = SpriteManager.EnumTexture.INPUTE.data.texture

          private val R1 = SpriteManager.EnumTexture.R1.data.texture
          private val R2 = SpriteManager.EnumTexture.R2.data.texture
          private val R3 = SpriteManager.EnumTexture.R3.data.texture

          private val S1 = SpriteManager.EnumTexture.S1.data.texture
          private val S2 = SpriteManager.EnumTexture.S2.data.texture
          private val S3 = SpriteManager.EnumTexture.S3.data.texture

          private val B1 = SpriteManager.EnumTexture.B1.data.texture
          private val B2 = SpriteManager.EnumTexture.B2.data.texture
          private val B3 = SpriteManager.EnumTexture.B3.data.texture

          val listR = listOf(R1, R2, R3)
          val listS = listOf(S1, S2, S3)
          val listB = listOf(B1, B2, B3)

     }

}