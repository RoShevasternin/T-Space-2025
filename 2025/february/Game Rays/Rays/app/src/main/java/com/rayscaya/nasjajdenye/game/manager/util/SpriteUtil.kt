package com.rayscaya.nasjajdenye.game.manager.util

import com.rayscaya.nasjajdenye.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo       = getRegion("logo")
          val logo_panel = getRegion("logo_panel")

          val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND_1.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val back_def        = getRegionAll("back_def")
          val back_press      = getRegionAll("back_press")
          val delete_def      = getRegionAll("delete_def")
          val delete_press    = getRegionAll("delete_press")
          val edit_def        = getRegionAll("edit_def")
          val edit_press      = getRegionAll("edit_press")
          val mini_edit_def   = getRegionAll("mini_edit_def")
          val mini_edit_press = getRegionAll("mini_edit_press")
          val plus_def        = getRegionAll("plus_def")
          val plus_press      = getRegionAll("plus_press")

          // textures ------------------------------------------------------------------------------

          val BACKGROUND_2 = SpriteManager.EnumTexture.BACKGROUND_2.data.texture
          val INPUT        = SpriteManager.EnumTexture.INPUT.data.texture
          val PANEL        = SpriteManager.EnumTexture.PANEL.data.texture
          val PRAMO        = SpriteManager.EnumTexture.PRAMO.data.texture
          val RECT         = SpriteManager.EnumTexture.RECT.data.texture
          val TOP          = SpriteManager.EnumTexture.TOP.data.texture
          val TXT1         = SpriteManager.EnumTexture.TXT1.data.texture
          val TXT2         = SpriteManager.EnumTexture.TXT2.data.texture
     }

}