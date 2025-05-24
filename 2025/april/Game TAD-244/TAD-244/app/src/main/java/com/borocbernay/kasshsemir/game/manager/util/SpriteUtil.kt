package com.borocbernay.kasshsemir.game.manager.util

import com.borocbernay.kasshsemir.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val green  = getRegion("green")
          val logo   = getRegion("logo")
          val title  = getRegion("title")
          val white  = getRegion("white")

          //val BACKGROUND_LOADER = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val botom        = getRegionAll("botom")
          val box_prodaja  = getRegionAll("box_prodaja")
          val box_tovar    = getRegionAll("box_tovar")
          val i1_def       = getRegionAll("i1_def")
          val i1_press     = getRegionAll("i1_press")
          val i2_def       = getRegionAll("i2_def")
          val i2_press     = getRegionAll("i2_press")
          val i3_def       = getRegionAll("i3_def")
          val i3_press     = getRegionAll("i3_press")
          val item_prodaja = getRegionAll("item_prodaja")
          val item_tovar   = getRegionAll("item_tovar")
          val plus_def     = getRegionAll("plus_def")
          val plus_press   = getRegionAll("plus_press")
          val topor        = getRegionAll("topor")

          // textures ------------------------------------------------------------------------------
          val DASHBOARD = SpriteManager.EnumTexture.DASHBOARD.data.texture
          val INPUTS    = SpriteManager.EnumTexture.INPUTS.data.texture
          val PRODAJKA  = SpriteManager.EnumTexture.PRODAJKA.data.texture

     }

}