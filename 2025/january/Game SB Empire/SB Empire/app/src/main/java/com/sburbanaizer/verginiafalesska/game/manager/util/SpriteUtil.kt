package com.sburbanaizer.verginiafalesska.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.sburbanaizer.verginiafalesska.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")
          val prog = getRegion("prog")

          val mask   = SpriteManager.EnumTexture.L_mask.data.texture
          val Loader = SpriteManager.EnumTexture.L_Loader.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val dob_def      = getRegion("dob_def")
          val dob_press    = getRegion("dob_press")
          val gotodo_def   = getRegion("gotodo_def")
          val gotodo_press = getRegion("gotodo_press")
          val gotope_def   = getRegion("gotope_def")
          val gotope_press = getRegion("gotope_press")
          val inv_def      = getRegion("inv_def")
          val inv_press    = getRegion("inv_press")
          val prodav_def   = getRegion("prodav_def")
          val prodav_press = getRegion("prodav_press")
          val uluch_def    = getRegion("uluch_def")
          val uluch_press  = getRegion("uluch_press")

          private val p10yell   = getRegion("p10yell")
          private val p10yell25 = getRegion("p10yell25")
          private val p10yell20 = getRegion("p10yell20")
          private val p10yell15 = getRegion("p10yell15")
          private val p10blak   = getRegion("p10blak")
          private val p10blak25 = getRegion("p10blak25")
          private val p10blak20 = getRegion("p10blak20")
          private val p10blak15 = getRegion("p10blak15")

          val listP10Gold  = listOf(p10yell, p10yell15, p10yell20, p10yell25)
          val listP10Black = listOf(p10blak, p10blak15, p10blak20, p10blak25)

          // 9 path ------------------------------------------------------------------------------

          //val nine_frame = get9Path("nine_frame")

          // textures ------------------------------------------------------------------------------

          val improvment = SpriteManager.EnumTexture.improvment.data.texture
          val investment = SpriteManager.EnumTexture.investment.data.texture
          val p1         = SpriteManager.EnumTexture.p1.data.texture
          val p2         = SpriteManager.EnumTexture.p2.data.texture
          val p3         = SpriteManager.EnumTexture.p3.data.texture
          val top        = SpriteManager.EnumTexture.top.data.texture
          val Tutorial_1 = SpriteManager.EnumTexture.Tutorial_1.data.texture
          val Tutorial_2 = SpriteManager.EnumTexture.Tutorial_2.data.texture

          private val _1 = SpriteManager.EnumTexture._1.data.texture
          private val _2 = SpriteManager.EnumTexture._2.data.texture
          private val _3 = SpriteManager.EnumTexture._3.data.texture
          private val _4 = SpriteManager.EnumTexture._4.data.texture

          private val d1 = SpriteManager.EnumTexture.d1.data.texture
          private val d2 = SpriteManager.EnumTexture.d2.data.texture
          private val d3 = SpriteManager.EnumTexture.d3.data.texture
          private val d4 = SpriteManager.EnumTexture.d4.data.texture

          val listDobicha = listOf(_1, _2, _3, _4)
          val listProdaja = listOf(d1, d2, d3, d4)

     }

}