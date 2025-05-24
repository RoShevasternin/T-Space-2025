package com.liberator.wisoliter.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.liberator.wisoliter.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo    = getRegion("logo")
          val progres = getRegion("progres")

          val background = SpriteManager.EnumTexture.L_background.data.texture
          val grid       = SpriteManager.EnumTexture.L_grid.data.texture
          val msek       = SpriteManager.EnumTexture.L_msek.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val army_def       = getRegion("army_def")
          val army_press     = getRegion("army_press")
          val atak_def       = getRegion("atak_def")
          val atak_press     = getRegion("atak_press")
          val balance        = getRegion("balance")
          val continue_def   = getRegion("continue_def")
          val continue_press = getRegion("continue_press")
          val create_def     = getRegion("create_def")
          val create_press   = getRegion("create_press")
          val green_panel    = getRegion("green_panel")
          val long_progress  = getRegion("long_progress")
          val longer         = getRegion("longer")
          val magaz_def      = getRegion("magaz_def")
          val magaz_press    = getRegion("magaz_press")
          val mini_progres   = getRegion("mini_progres")
          val minus10        = getRegion("minus10")
          val plus10         = getRegion("plus10")
          val sto_def        = getRegion("sto_def")
          val sto_press      = getRegion("sto_press")
          val x_def          = getRegion("x_def")
          val x_press        = getRegion("x_press")

          val listItem = List(7) { getRegion("${it.inc()}") }

          // 9 path ------------------------------------------------------------------------------

          val orange = get9Path("orange")

          // textures ------------------------------------------------------------------------------

          val long_mask      = SpriteManager.EnumTexture.long_mask.data.texture
          val mini_mask      = SpriteManager.EnumTexture.mini_mask.data.texture
          val pop_ended_army = SpriteManager.EnumTexture.pop_ended_army.data.texture
          val pop_greet      = SpriteManager.EnumTexture.pop_greet.data.texture
          val pop_need_xp    = SpriteManager.EnumTexture.pop_need_xp.data.texture

          val test = SpriteManager.EnumTexture.test.data.texture

          private val w1 = SpriteManager.EnumTexture.w1.data.texture
          private val w2 = SpriteManager.EnumTexture.w2.data.texture
          private val w3 = SpriteManager.EnumTexture.w3.data.texture
          private val w4 = SpriteManager.EnumTexture.w4.data.texture
          private val w5 = SpriteManager.EnumTexture.w5.data.texture
          private val w6 = SpriteManager.EnumTexture.w6.data.texture
          private val w7 = SpriteManager.EnumTexture.w7.data.texture
          private val w8 = SpriteManager.EnumTexture.w8.data.texture
          private val w9 = SpriteManager.EnumTexture.w9.data.texture

          private val c1  = SpriteManager.EnumTexture.c1.data.texture
          private val c2  = SpriteManager.EnumTexture.c2.data.texture
          private val c3  = SpriteManager.EnumTexture.c3.data.texture
          private val c4  = SpriteManager.EnumTexture.c4.data.texture
          private val c5  = SpriteManager.EnumTexture.c5.data.texture
          private val c6  = SpriteManager.EnumTexture.c6.data.texture
          private val c7  = SpriteManager.EnumTexture.c7.data.texture
          private val c8  = SpriteManager.EnumTexture.c8.data.texture
          private val c9  = SpriteManager.EnumTexture.c9.data.texture
          private val c10 = SpriteManager.EnumTexture.c10.data.texture
          private val c11 = SpriteManager.EnumTexture.c11.data.texture
          private val c12 = SpriteManager.EnumTexture.c12.data.texture
          private val c13 = SpriteManager.EnumTexture.c13.data.texture
          private val c14 = SpriteManager.EnumTexture.c14.data.texture
          private val c15 = SpriteManager.EnumTexture.c15.data.texture
          private val c16 = SpriteManager.EnumTexture.c16.data.texture
          private val c17 = SpriteManager.EnumTexture.c17.data.texture
          private val c18 = SpriteManager.EnumTexture.c18.data.texture
          private val c19 = SpriteManager.EnumTexture.c19.data.texture
          private val c20 = SpriteManager.EnumTexture.c20.data.texture
          private val c21 = SpriteManager.EnumTexture.c21.data.texture
          private val c22 = SpriteManager.EnumTexture.c22.data.texture
          private val c23 = SpriteManager.EnumTexture.c23.data.texture
          private val c24 = SpriteManager.EnumTexture.c24.data.texture
          private val c25 = SpriteManager.EnumTexture.c25.data.texture
          private val c26 = SpriteManager.EnumTexture.c26.data.texture
          private val c27 = SpriteManager.EnumTexture.c27.data.texture
          private val c28 = SpriteManager.EnumTexture.c28.data.texture
          private val c29 = SpriteManager.EnumTexture.c29.data.texture
          private val c30 = SpriteManager.EnumTexture.c30.data.texture

          val listWorldBackground = listOf(w1, w2, w3, w4, w5, w6, w7, w8, w9)
          val listCountry         = listOf(
               c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15,
               c16, c17, c18, c19, c20, c21, c22, c23, c24, c25, c26, c27, c28, c29, c30,
          )
     }

}