package com.funkun.kylkan.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.funkun.kylkan.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val logo = getRegion("logo")
          val prog = getRegion("prog")

          val background = SpriteManager.EnumTexture.L_background.data.texture
          val mogo       = SpriteManager.EnumTexture.L_mogo.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val add_def       = getRegion("add_def")
          val add_press     = getRegion("add_press")
          val add_sum_def   = getRegion("add_sum_def")
          val add_sum_press = getRegion("add_sum_press")
          val frame         = getRegion("frame")
          val m_home        = getRegion("m_home")
          val m_hystory     = getRegion("m_hystory")
          val progress      = getRegion("progress")
          val top           = getRegion("top")
          val your_poezd    = getRegion("your_poezd")
          val m_def         = getRegion("m_def")

          // 9 path ------------------------------------------------------------------------------

          //val nine_frame = get9Path("nine_frame")

          // textures ------------------------------------------------------------------------------

          val green         = SpriteManager.EnumTexture.green.data.texture
          val kuda_data_cum = SpriteManager.EnumTexture.kuda_data_cum.data.texture
          val masaka        = SpriteManager.EnumTexture.masaka.data.texture
          val poezdka       = SpriteManager.EnumTexture.poezdka.data.texture
          val trata_cena    = SpriteManager.EnumTexture.trata_cena.data.texture
     }

}