package com.bagazkz.klarebadew.game.manager.util

import com.bagazkz.klarebadew.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val kzgz = getRegion("kzgz")
          val lod  = getRegion("lod")

          val LOADER = SpriteManager.EnumTexture.L_LOADER.data.texture
          val MASKA  = SpriteManager.EnumTexture.L_MASKA.data.texture
          val BACKDA = SpriteManager.EnumTexture.L_BACKDA.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val blue_plus  = getRegionAll("blue_plus")
          val coin_plus  = getRegionAll("coin_plus")
          val dob_def    = getRegionAll("dob_def")
          val dob_press  = getRegionAll("dob_press")
          val mag_def    = getRegionAll("mag_def")
          val mag_press  = getRegionAll("mag_press")
          val prod_def   = getRegionAll("prod_def")
          val prod_press = getRegionAll("prod_press")
          val progress   = getRegionAll("progress")
          val x_def      = getRegionAll("x_def")
          val x_press    = getRegionAll("x_press")

          // textures ------------------------------------------------------------------------------

          private val _1      = SpriteManager.EnumTexture._1.data.texture
          private val _2      = SpriteManager.EnumTexture._2.data.texture
          private val _3      = SpriteManager.EnumTexture._3.data.texture
          private val _4      = SpriteManager.EnumTexture._4.data.texture
          private val _5      = SpriteManager.EnumTexture._5.data.texture
          private val _6      = SpriteManager.EnumTexture._6.data.texture
          private val _7      = SpriteManager.EnumTexture._7.data.texture

          val BALANCE = SpriteManager.EnumTexture.BALANCE.data.texture
          val DIALOG  = SpriteManager.EnumTexture.DIALOG.data.texture
          val MAGAZIK = SpriteManager.EnumTexture.MAGAZIK.data.texture
          val MASK    = SpriteManager.EnumTexture.MASK.data.texture

          val listZavods = listOf(_1, _2, _3, _4, _5, _6, _7)
     }

}