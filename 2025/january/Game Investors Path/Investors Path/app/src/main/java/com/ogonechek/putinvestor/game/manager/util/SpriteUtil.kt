package com.ogonechek.putinvestor.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.ogonechek.putinvestor.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo  = getRegion("logo")
          val title = getRegion("title")

          val ogon = SpriteManager.EnumTexture.L_ogon.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val add_inv = getRegion("add_inv")
          val blue    = getRegion("blue")
          val check   = getRegion("check")
          val inest   = getRegion("inest")
          val p_def   = getRegion("p_def")
          val p_press = getRegion("p_press")
          val panel   = getRegion("panel")
          val s_all   = getRegion("s_all")
          val s_svern = getRegion("s_svern")

          // 9 path ------------------------------------------------------------------------------

          //val nine_frame = get9Path("nine_frame")

          // textures ------------------------------------------------------------------------------

          val add_panel = SpriteManager.EnumTexture.add_panel.data.texture
     }

}