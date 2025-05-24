package com.pulser.purlembager.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.pulser.purlembager.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val gear     = getRegion("gear")
          val logo     = getRegion("logo")
          val progress = getRegion("progress")

          val a = SpriteManager.EnumTexture.L_a.data.texture
          val b = SpriteManager.EnumTexture.L_b.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val add_button  = getRegion("add_button")
          val back_black  = getRegion("back_black")
          val back_white  = getRegion("back_white")
          val desire_back = getRegion("desire_back")
          val menu        = getRegion("menu")
          val next_def    = getRegion("next_def")
          val next_press  = getRegion("next_press")
          val p_b         = getRegion("p_b")
          val p_g         = getRegion("p_g")
          val p_y         = getRegion("p_y")
          val panel_add   = getRegion("panel_add")
          val panel_blog  = getRegion("panel_blog")
          val panel_home  = getRegion("panel_home")

          val rr  = getRegion("rr")
          val bb  = getRegion("bb")

          // 9 path ------------------------------------------------------------------------------

          val selected  = get9Path("selected")

          // textures ------------------------------------------------------------------------------

          val Blog        = SpriteManager.EnumTexture.Blog.data.texture
          val blog_button = SpriteManager.EnumTexture.blog_button.data.texture
          val input_back  = SpriteManager.EnumTexture.input_back.data.texture
          val list        = SpriteManager.EnumTexture.list.data.texture
          val mask        = SpriteManager.EnumTexture.mask.data.texture
          val numbers     = SpriteManager.EnumTexture.numbers.data.texture

          val test = SpriteManager.EnumTexture.test.data.texture
          val elp_mask = SpriteManager.EnumTexture.elp_mask.data.texture

          val _1 = SpriteManager.EnumTexture._1.data.texture
          val _2 = SpriteManager.EnumTexture._2.data.texture
          val _3 = SpriteManager.EnumTexture._3.data.texture
          val _4 = SpriteManager.EnumTexture._4.data.texture
          val _5 = SpriteManager.EnumTexture._5.data.texture

          val listBlog = listOf(
               _1,
                       _2,
                       _3,
                       _4,
                       _5,
          )

     }

}