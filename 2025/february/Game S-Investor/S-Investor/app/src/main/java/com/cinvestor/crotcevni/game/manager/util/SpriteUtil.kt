package com.cinvestor.crotcevni.game.manager.util

import com.cinvestor.crotcevni.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val c_logo = getRegion("c_logo")
          val prg    = getRegion("prg")
          val rbl    = getRegion("rbl")

          val BACKER = SpriteManager.EnumTexture.L_backer.data.texture
          val MSK    = SpriteManager.EnumTexture.L_msk.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val conf_def     = getRegionAll("conf_def")
          val conf_press   = getRegionAll("conf_press")
          val impr_def     = getRegionAll("impr_def")
          val impr_press   = getRegionAll("impr_press")
          val invest_def   = getRegionAll("invest_def")
          val invest_press = getRegionAll("invest_press")
          val one          = getRegionAll("one")
          val progress     = getRegionAll("progress")
          val tre          = getRegionAll("tre")
          val two          = getRegionAll("two")
          val work_def     = getRegionAll("work_def")
          val work_press   = getRegionAll("work_press")
          val c            = getRegionAll("c")

          // textures ------------------------------------------------------------------------------

          val _1      = SpriteManager.EnumTexture._1.data.texture
          val _2      = SpriteManager.EnumTexture._2.data.texture
          val _3      = SpriteManager.EnumTexture._3.data.texture
          val IMPROVE = SpriteManager.EnumTexture.IMPROVE.data.texture
          val INVEST  = SpriteManager.EnumTexture.INVEST.data.texture
          val MASKA   = SpriteManager.EnumTexture.MASKA.data.texture
          val OFFICE  = SpriteManager.EnumTexture.OFFICE.data.texture
          val TOP     = SpriteManager.EnumTexture.TOP.data.texture
     }

}