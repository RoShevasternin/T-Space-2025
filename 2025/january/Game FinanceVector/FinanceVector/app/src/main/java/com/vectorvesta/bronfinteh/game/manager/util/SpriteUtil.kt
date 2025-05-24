package com.vectorvesta.bronfinteh.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.vectorvesta.bronfinteh.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo  = getRegion("logo")
          val title = getRegion("title")

          val CUBECE = SpriteManager.EnumTexture.L_CUBECE.data.texture
     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val back_def        = getRegionAll("back_def")
          val back_press      = getRegionAll("back_press")
          val calculate_def   = getRegionAll("calculate_def")
          val calculate_press = getRegionAll("calculate_press")
          val coin            = getRegionAll("coin")
          val hist            = getRegionAll("hist")
          val line            = getRegionAll("line")
          val main            = getRegionAll("main")
          val remove_def      = getRegionAll("remove_def")
          val remove_press    = getRegionAll("remove_press")
          val sine            = getRegionAll("sine")

          //val listItems = List(9) { getRegionItems("${it.inc()}") }

          // textures ------------------------------------------------------------------------------

          val DEPOSIT_DEF       = SpriteManager.EnumTexture.DEPOSIT_DEF.data.texture
          val DEPOSIT_PRESS     = SpriteManager.EnumTexture.DEPOSIT_PRESS.data.texture
          val INVESTMENTS_DEF   = SpriteManager.EnumTexture.INVESTMENTS_DEF.data.texture
          val INVESTMENTS_PRESS = SpriteManager.EnumTexture.INVESTMENTS_PRESS.data.texture
          val LEASING_DEF       = SpriteManager.EnumTexture.LEASING_DEF.data.texture
          val LEASING_PRESS     = SpriteManager.EnumTexture.LEASING_PRESS.data.texture
          val LOAN_DEF          = SpriteManager.EnumTexture.LOAN_DEF.data.texture
          val LOAN_PRESS        = SpriteManager.EnumTexture.LOAN_PRESS.data.texture
          val MORTGAGE_DEF      = SpriteManager.EnumTexture.MORTGAGE_DEF.data.texture
          val MORTGAGE_PRESS    = SpriteManager.EnumTexture.MORTGAGE_PRESS.data.texture
          val VISION            = SpriteManager.EnumTexture.VISION.data.texture
     }

}