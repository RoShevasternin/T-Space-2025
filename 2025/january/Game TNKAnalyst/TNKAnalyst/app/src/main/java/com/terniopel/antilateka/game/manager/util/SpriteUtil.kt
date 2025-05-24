package com.terniopel.antilateka.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.terniopel.antilateka.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val main = getRegion("main")
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val k1_def           = getRegion("k1_def")
          val k1_press         = getRegion("k1_press")
          val k2_def           = getRegion("k2_def")
          val k2_press         = getRegion("k2_press")
          val k3_def           = getRegion("k3_def")
          val k3_press         = getRegion("k3_press")
          val k4_def           = getRegion("k4_def")
          val k4_press         = getRegion("k4_press")
          val logo             = getRegion("logo")
          val next_def         = getRegion("next_def")
          val next_press       = getRegion("next_press")
          val podilka          = getRegion("podilka")
          val progress         = getRegion("progress")
          val start_test_def   = getRegion("start_test_def")
          val start_test_press = getRegion("start_test_press")
          val title            = getRegion("title")

          // 9 path ------------------------------------------------------------------------------

          val panel     = get9Path("panel")
          val ans_def   = get9Path("ans_def")
          val ans_press = get9Path("ans_press")

          // textures ------------------------------------------------------------------------------

          private val i1 = SpriteManager.EnumTexture.i1.data.texture
          private val i2 = SpriteManager.EnumTexture.i2.data.texture
          private val i3 = SpriteManager.EnumTexture.i3.data.texture
          private val i4 = SpriteManager.EnumTexture.i4.data.texture

          private val r1 = SpriteManager.EnumTexture.r1.data.texture
          private val r2 = SpriteManager.EnumTexture.r2.data.texture
          private val r3 = SpriteManager.EnumTexture.r3.data.texture

          val p2_def   = SpriteManager.EnumTexture.p2_def.data.texture
          val p2_press = SpriteManager.EnumTexture.p2_press.data.texture

          val listI = listOf(i1, i2, i3, i4)
          val listR = listOf(r1, r2, r3)
     }

}