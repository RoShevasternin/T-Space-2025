package com.figidnansovich.glamour.game.manager.util

import com.figidnansovich.glamour.game.manager.SpriteManager

class SpriteUtil {

    class Loader {

        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.LOADERAS.data.atlas.findRegion(regionName)

        val initer = getRegion("initer")
        val loader = getRegion("loader")
        val logo   = getRegion("logo")

        val B_Loader = SpriteManager.EnumTexture.loader.data.texture
        val maska    = SpriteManager.EnumTexture.maska.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)
        private fun getNine(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(regionName)

        val balance_panel_center = getRegion("balance_panel_center")
        val back_def   = getRegion("back_def")
        val back_prss  = getRegion("back_prss")
        val dalee_def  = getRegion("dalee_def")
        val dalee_prss = getRegion("dalee_prss")
        val def        = getRegion("def")
        val _false      = getRegion("false")
        val manu_main  = getRegion("manu_main")
        val next_def   = getRegion("next_def")
        val next_prss  = getRegion("next_prss")
        val _true       = getRegion("true")

        val white      = getNine("white")

        val bottom        = SpriteManager.EnumTexture.bottom.data.texture
        val chel          = SpriteManager.EnumTexture.chel.data.texture
        val cubek         = SpriteManager.EnumTexture.cubek.data.texture
        val fon           = SpriteManager.EnumTexture.fon.data.texture
        val mishen        = SpriteManager.EnumTexture.mishen.data.texture
        val ogo           = SpriteManager.EnumTexture.ogo.data.texture
        val persona_padae = SpriteManager.EnumTexture.persona_padae.data.texture
        val strela        = SpriteManager.EnumTexture.strela.data.texture
        val stupeni       = SpriteManager.EnumTexture.stupeni.data.texture
        val vau           = SpriteManager.EnumTexture.vau.data.texture


    }

}