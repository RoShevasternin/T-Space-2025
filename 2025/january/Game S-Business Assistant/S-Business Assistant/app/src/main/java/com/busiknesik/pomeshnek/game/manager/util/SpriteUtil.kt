package com.busiknesik.pomeshnek.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.busiknesik.pomeshnek.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")
          val preg = getRegion("preg")

          val mseker    = SpriteManager.EnumTexture.L_mseker.data.texture
          val backgrand = SpriteManager.EnumTexture.L_backgrand.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val brand   = getRegion("brand")
          val histore = getRegion("histore")
          val home    = getRegion("home")
          val tovars  = getRegion("tovars")

          // 9 path ------------------------------------------------------------------------------

          //val nine_frame = get9Path("nine_frame")

          // textures ------------------------------------------------------------------------------

          val edit_tovar = SpriteManager.EnumTexture.edit_tovar.data.texture
          val panel      = SpriteManager.EnumTexture.panel.data.texture
          val tovar      = SpriteManager.EnumTexture.tovar.data.texture
     }

}