package com.sberigatelny.finexpertaizer.game.manager.util

import com.sberigatelny.finexpertaizer.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val check      = getRegion("check")
          val progress   = getRegion("progress")
          val loader_bar = getRegion("loader_bar")

          val mask = SpriteManager.EnumTexture.L_mask.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9patch(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val pereezd        = getRegionAll("pereezd")
          val redfalse       = getRegionAll("redfalse")
          val to_work        = getRegionAll("to_work")
          val tut_hand_click = getRegionAll("tut_hand_click")
          val tutorial_hand  = getRegionAll("tutorial_hand")
          val to_work_press  = getRegionAll("to_work_press")
          val pereezd_press  = getRegionAll("pereezd_press")
          val home_press     = getRegionAll("home_press")
          val home_def       = getRegionAll("home_def")

          //val bottom = get9patch("bottom")

          // textures ------------------------------------------------------------------------------

          val back_1 = SpriteManager.EnumTexture.back_1.data.texture
          val back_2 = SpriteManager.EnumTexture.back_2.data.texture
          val back_3 = SpriteManager.EnumTexture.back_3.data.texture
          val back_4 = SpriteManager.EnumTexture.back_4.data.texture
          val back_5 = SpriteManager.EnumTexture.back_5.data.texture

          val _1  = SpriteManager.EnumTexture._1.data.texture
          val _2  = SpriteManager.EnumTexture._2.data.texture
          val _3  = SpriteManager.EnumTexture._3.data.texture
          val _4  = SpriteManager.EnumTexture._4.data.texture
          val _5  = SpriteManager.EnumTexture._5.data.texture
          val _6  = SpriteManager.EnumTexture._6.data.texture
          val _7  = SpriteManager.EnumTexture._7.data.texture
          val _8  = SpriteManager.EnumTexture._8.data.texture
          val _9  = SpriteManager.EnumTexture._9.data.texture
          val _10 = SpriteManager.EnumTexture._10.data.texture

          val chel           = SpriteManager.EnumTexture.chel.data.texture
          val d1             = SpriteManager.EnumTexture.d1.data.texture
          val d2             = SpriteManager.EnumTexture.d2.data.texture
          val greet_10k      = SpriteManager.EnumTexture.greet_10k.data.texture
          val greet_1000     = SpriteManager.EnumTexture.greet_1000.data.texture
          val greet_new_work = SpriteManager.EnumTexture.greet_new_work.data.texture
          val nema_rabota    = SpriteManager.EnumTexture.nema_rabota.data.texture
          val time_and_gold  = SpriteManager.EnumTexture.time_and_gold.data.texture

          val listResult = listOf(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10)
     }

}