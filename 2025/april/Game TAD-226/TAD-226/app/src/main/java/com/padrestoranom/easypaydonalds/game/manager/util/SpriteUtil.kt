package com.padrestoranom.easypaydonalds.game.manager.util

import com.padrestoranom.easypaydonalds.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val btn       = getRegion("btn")
          val btn_press = getRegion("btn_press")
          val logo      = getRegion("logo")
          val sambolet  = getRegion("sambolet")

          val BACKGROUND_LOADER = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegionEXPENSE(name: String): TextureRegion = SpriteManager.EnumAtlas.ICON_EXPENSE.data.atlas.findRegion(name)
          private fun getRegionINCOME(name: String): TextureRegion = SpriteManager.EnumAtlas.ICON_INCOME.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val blur        = getRegionAll("blur")
          val cir_def     = getRegionAll("cir_def")
          val cir_press   = getRegionAll("cir_press")
          val gelir_check = getRegionAll("gelir_check")
          val gelir_def   = getRegionAll("gelir_def")
          val grau        = getRegionAll("grau")
          val har_check   = getRegionAll("har_check")
          val har_def     = getRegionAll("har_def")
          val input       = getRegionAll("input")
          val menu        = getRegionAll("menu")
          val red         = getRegionAll("red")
          val stovp       = getRegionAll("stovp")

          val listINCOME  = List(3) { getRegionINCOME(it.inc().toString()) }
          val listEXPENSE = List(9) { getRegionEXPENSE(it.inc().toString()) }

          // textures ------------------------------------------------------------------------------
          val BLACK = SpriteManager.EnumTexture.BLACK.data.texture
          val DASK  = SpriteManager.EnumTexture.DASK.data.texture
          val FRAME = SpriteManager.EnumTexture.FRAME.data.texture
          val TARIH = SpriteManager.EnumTexture.TARIH.data.texture

     }

}