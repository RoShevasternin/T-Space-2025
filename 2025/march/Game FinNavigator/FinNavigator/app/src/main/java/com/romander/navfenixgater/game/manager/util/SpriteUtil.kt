package com.romander.navfenixgater.game.manager.util

import com.romander.navfenixgater.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("loader")
          val proger = getRegion("proger")

          val LOADER = SpriteManager.EnumTexture.L_LOADER.data.texture
          val MASKA  = SpriteManager.EnumTexture.L_MASKA.data.texture
          val PANEL  = SpriteManager.EnumTexture.L_PANEL.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val calcul_def    = getRegionAll("calcul_def")
          val calcul_press  = getRegionAll("calcul_press")
          val close         = getRegionAll("close")
          val golovna_def   = getRegionAll("golovna_def")
          val golovna_press = getRegionAll("golovna_press")
          val history_def   = getRegionAll("history_def")
          val history_press = getRegionAll("history_press")
          val list          = getRegionAll("list")
          val logo          = getRegionAll("logo")
          val open          = getRegionAll("open")
          val ras_def       = getRegionAll("ras_def")
          val ras_press     = getRegionAll("ras_press")
          val remove_def    = getRegionAll("remove_def")
          val remove_press  = getRegionAll("remove_press")
          val restart_def   = getRegionAll("restart_def")
          val restart_press = getRegionAll("restart_press")
          val save_def      = getRegionAll("save_def")
          val save_press    = getRegionAll("save_press")
          val yellow_title  = getRegionAll("yellow_title")
          val rasss_def     = getRegionAll("rasss_def")
          val rasss_press   = getRegionAll("rasss_press")

          // textures ------------------------------------------------------------------------------

          val DOWN_LIST = SpriteManager.EnumTexture.DOWN_LIST.data.texture
          val INPUT     = SpriteManager.EnumTexture.INPUT.data.texture
          val MAINTEXT  = SpriteManager.EnumTexture.MAINTEXT.data.texture
          val Y_BOT     = SpriteManager.EnumTexture.Y_BOT.data.texture
          val Y_CENTER  = SpriteManager.EnumTexture.Y_CENTER.data.texture
          val Y_TOP     = SpriteManager.EnumTexture.Y_TOP.data.texture
     }

}