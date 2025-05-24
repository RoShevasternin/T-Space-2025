package com.pezdunkov.sberdarorcassa.game.manager.util

import com.pezdunkov.sberdarorcassa.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("loader")
          val logo   = getRegion("logo")

          //val BACKGROUND_LOADER = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val amount_sales = getRegionAll("amount_sales")
          val home_def     = getRegionAll("home_def")
          val home_press   = getRegionAll("home_press")
          val s1_def       = getRegionAll("s1_def")
          val s1_press     = getRegionAll("s1_press")
          val s2_def       = getRegionAll("s2_def")
          val s2_press     = getRegionAll("s2_press")
          val s3_def       = getRegionAll("s3_def")
          val s3_press     = getRegionAll("s3_press")
          val s4_def       = getRegionAll("s4_def")
          val s4_press     = getRegionAll("s4_press")
          val save_def     = getRegionAll("save_def")
          val save_press   = getRegionAll("save_press")

          // textures ------------------------------------------------------------------------------
          val INPUTES      = SpriteManager.EnumTexture.INPUTES.data.texture
          val ITEM         = SpriteManager.EnumTexture.ITEM.data.texture
          val ITEM_PRODANO = SpriteManager.EnumTexture.ITEM_PRODANO.data.texture
          val SALES        = SpriteManager.EnumTexture.SALES.data.texture
          val TOP          = SpriteManager.EnumTexture.TOP.data.texture

     }

}