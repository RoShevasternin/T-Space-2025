package com.easyru.track.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.easyru.track.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val gear          = getRegion("gear")
          val logo          = getRegion("logo")
          val progress      = getRegion("progress")
          val progress_back = getRegion("progress_back")

          val gradient      = SpriteManager.EnumTexture.L_gradient.data.texture
          val mask_logo     = SpriteManager.EnumTexture.L_mask_logo.data.texture
          val mask_progress = SpriteManager.EnumTexture.L_mask_progress.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val _1           = getRegion("1")
          val _2           = getRegion("2")
          val _3           = getRegion("3")
          val _4           = getRegion("4")
          val _5           = getRegion("5")
          val _6           = getRegion("6")
          val back_def     = getRegion("back_def")
          val back_press   = getRegion("back_press")
          val next_def     = getRegion("next_def")
          val next_press   = getRegion("next_press")
          val start_def    = getRegion("start_def")
          val start_press  = getRegion("start_press")
          val text_onboard = getRegion("text_onboard")
          val timer        = getRegion("timer")

          // 9 path ------------------------------------------------------------------------------

          val nine_frame = get9Path("nine_frame")
          val nine_green = get9Path("nine_green")
          val nine_red   = get9Path("nine_red")
          val nine_white = get9Path("nine_white")

          // textures ------------------------------------------------------------------------------

          val test         = SpriteManager.EnumTexture.test.data.texture
          val a_chel       = SpriteManager.EnumTexture.a_chel.data.texture
          val a_coin       = SpriteManager.EnumTexture.a_coin.data.texture
          val a_result     = SpriteManager.EnumTexture.a_result.data.texture
          val a_tel        = SpriteManager.EnumTexture.a_tel.data.texture
          val b_chel       = SpriteManager.EnumTexture.b_chel.data.texture
          val b_coin       = SpriteManager.EnumTexture.b_coin.data.texture
          val b_result     = SpriteManager.EnumTexture.b_result.data.texture
          val c_chel       = SpriteManager.EnumTexture.c_chel.data.texture
          val c_result     = SpriteManager.EnumTexture.c_result.data.texture
          val c_x1         = SpriteManager.EnumTexture.c_x1.data.texture
          val c_x2         = SpriteManager.EnumTexture.c_x2.data.texture
          val menu         = SpriteManager.EnumTexture.menu.data.texture
          val onboard_blob = SpriteManager.EnumTexture.onboard_blob.data.texture
          val onboard_main = SpriteManager.EnumTexture.onboard_main.data.texture
          val result       = SpriteManager.EnumTexture.result.data.texture
          val white_result = SpriteManager.EnumTexture.white_result.data.texture
     }

}