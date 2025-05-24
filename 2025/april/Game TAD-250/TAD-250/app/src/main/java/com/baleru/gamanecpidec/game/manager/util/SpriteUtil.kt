package com.baleru.gamanecpidec.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.baleru.gamanecpidec.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")

          val BACKGROUND_LOADER = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val back_def    = getRegionAll("back_def")
          val back_press  = getRegionAll("back_press")
          val bap         = getRegionAll("bap")
          val bnc         = getRegionAll("bnc")
          val doh_expen   = getRegionAll("doh_expen")
          val expen_doh   = getRegionAll("expen_doh")
          val hist_check  = getRegionAll("hist_check")
          val hist_def    = getRegionAll("hist_def")
          val histr       = getRegionAll("histr")
          val input       = getRegionAll("input")
          val itemp       = getRegionAll("itemp")
          val plus_check  = getRegionAll("plus_check")
          val plus_def    = getRegionAll("plus_def")
          val polza_check = getRegionAll("polza_check")
          val polza_def   = getRegionAll("polza_def")
          val prog        = getRegionAll("prog")
          val za_seg      = getRegionAll("za_seg")
          val za_vse      = getRegionAll("za_vse")

          val listINCOME  = List(4) { getRegionAll("w" + it.inc().toString()) }
          val listEXPENSE = List(11) { getRegionAll(it.inc().toString()) }

          // textures ------------------------------------------------------------------------------
          val DOHED  = SpriteManager.EnumTexture.DOHED .data.texture
          val ITEMER = SpriteManager.EnumTexture.ITEMER.data.texture
          val ROSHED = SpriteManager.EnumTexture.ROSHED.data.texture
          val MASK   = SpriteManager.EnumTexture.MASK.data.texture

     }

}