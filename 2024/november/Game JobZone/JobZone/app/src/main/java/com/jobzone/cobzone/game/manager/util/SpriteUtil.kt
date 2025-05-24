package com.jobzone.cobzone.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.jobzone.cobzone.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          // regions ------------------------------------------------------------------------------

          val blue  = getRegion("blue")
          val brend = getRegion("brend")
          val line  = getRegion("line")
          val load  = getRegion("load")
          val logo  = getRegion("logo")

          // textures ------------------------------------------------------------------------------

          val background = SpriteManager.EnumTexture.L_background.data.texture
          val mask       = SpriteManager.EnumTexture.L_mask.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // 9 path ------------------------------------------------------------------------------

          val panel = get9Path("panel")

          // regions ------------------------------------------------------------------------------

          val back_def                = getRegion("back_def")
          val back_press              = getRegion("back_press")
          val da_check                = getRegion("da_check")
          val da_def                  = getRegion("da_def")
          val easy                    = getRegion("easy")
          val net_check               = getRegion("net_check")
          val net_def                 = getRegion("net_def")
          val otklik_def              = getRegion("otklik_def")
          val otklik_on_vakansy_def   = getRegion("otklik_on_vakansy_def")
          val otklik_on_vakansy_press = getRegion("otklik_on_vakansy_press")
          val otklik_press            = getRegion("otklik_press")
          val podrobno_def            = getRegion("podrobno_def")
          val podrobno_press          = getRegion("podrobno_press")
          val pole_vvoda              = getRegion("pole_vvoda")
          val potencial               = getRegion("potencial")
          val sapolni_formu           = getRegion("sapolni_formu")
          val thanks                  = getRegion("thanks")
          val vakansi_no_active       = getRegion("vakansi_no_active")

          val listItems = List(6) { getRegion("${it.inc()}") }

          // textures ------------------------------------------------------------------------------

          val gear      = SpriteManager.EnumTexture.A_gear.data.texture
          val gradik    = SpriteManager.EnumTexture.A_gradik.data.texture
          val list_1    = SpriteManager.EnumTexture.A_list_1.data.texture
          val list_2    = SpriteManager.EnumTexture.A_list_2.data.texture
          val lupa      = SpriteManager.EnumTexture.A_lupa.data.texture
          val rrr       = SpriteManager.EnumTexture.A_rrr.data.texture
          val oklik     = SpriteManager.EnumTexture.A_oklik.data.texture
          val pers      = SpriteManager.EnumTexture.A_pers.data.texture
          val polu_chel = SpriteManager.EnumTexture.A_polu_chel.data.texture

          private val v1  = SpriteManager.EnumTexture.A_v1.data.texture
          private val v2  = SpriteManager.EnumTexture.A_v2.data.texture
          private val v3  = SpriteManager.EnumTexture.A_v3.data.texture
          private val v4  = SpriteManager.EnumTexture.A_v4.data.texture
          private val v5  = SpriteManager.EnumTexture.A_v5.data.texture
          private val v6  = SpriteManager.EnumTexture.A_v6.data.texture
          private val v7  = SpriteManager.EnumTexture.A_v7.data.texture
          private val v8  = SpriteManager.EnumTexture.A_v8.data.texture
          private val v9  = SpriteManager.EnumTexture.A_v9.data.texture
          private val v10 = SpriteManager.EnumTexture.A_v10.data.texture

          private val vacan1  = SpriteManager.EnumTexture.A_vacan1.data.texture
          private val vacan2  = SpriteManager.EnumTexture.A_vacan2.data.texture
          private val vacan3  = SpriteManager.EnumTexture.A_vacan3.data.texture
          private val vacan4  = SpriteManager.EnumTexture.A_vacan4.data.texture
          private val vacan5  = SpriteManager.EnumTexture.A_vacan5.data.texture
          private val vacan6  = SpriteManager.EnumTexture.A_vacan6.data.texture
          private val vacan7  = SpriteManager.EnumTexture.A_vacan7.data.texture
          private val vacan8  = SpriteManager.EnumTexture.A_vacan8.data.texture
          private val vacan9  = SpriteManager.EnumTexture.A_vacan9.data.texture
          private val vacan10 = SpriteManager.EnumTexture.A_vacan10.data.texture

          val listVac     = listOf(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10)
          val listDetails = listOf(vacan1, vacan2, vacan3, vacan4, vacan5, vacan6, vacan7, vacan8, vacan9, vacan10)
     }

}