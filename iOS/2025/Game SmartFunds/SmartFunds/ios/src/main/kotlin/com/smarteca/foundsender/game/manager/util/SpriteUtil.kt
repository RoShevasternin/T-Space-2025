package com.smarteca.foundsender.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.smarteca.foundsender.game.manager.SpriteManager

class SpriteUtil {

    class Access {
        private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ACCESS.data.atlas.findRegion(name)

        val you_can_cancel  = getRegion("you_can_cancel")
        val allow_def       = getRegion("allow_def")
        val allow_press     = getRegion("allow_press")
        val ask_not_def     = getRegion("ask_not_def")
        val ask_not_press   = getRegion("ask_not_press")

        val ACCESS     = SpriteManager.EnumTexture.ACCESS_ACCESS.data.texture
        val BACKGROUND = SpriteManager.EnumTexture.ACCESS_BACKGROUND.data.texture
    }

     class Loader {
         private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

         val loader   = getRegion("loader")
         val progress = getRegion("progress")
         val wallet   = getRegion("wallet")

         val BACKGROUND = SpriteManager.EnumTexture.LOADER_BACKGROUND.data.texture
         val MASK       = SpriteManager.EnumTexture.LOADER_MASK.data.texture
     }

    class All {
        private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
        private fun getRegionTest(name: String): TextureRegion = SpriteManager.EnumAtlas.TEST.data.atlas.findRegion(name)

        // atlas All ------------------------------------------------------------------------------

        val you_can_cancel  = getRegionAll("you_can_cancel")
        val allow_def       = getRegionAll("allow_def")
        val allow_press     = getRegionAll("allow_press")
        val ask_not_def     = getRegionAll("ask_not_def")
        val ask_not_press   = getRegionAll("ask_not_press")
        val btn_green_def   = getRegionAll("btn_green_def")
        val btn_green_press = getRegionAll("btn_green_press")
        val dialogs_def     = getRegionAll("dialogs_def")
        val dialogs_press   = getRegionAll("dialogs_press")
        val reset_def       = getRegionAll("reset_def")
        val reset_press     = getRegionAll("reset_press")
        val savings_press   = getRegionAll("savings_press")
        val delete_def      = getRegionAll("delete_def")
        val delete_press    = getRegionAll("delete_press")
        val savings_def     = getRegionAll("savings_def")
        val box_compound    = getRegionAll("box_compound")
        val box_simple      = getRegionAll("box_simple")

        val circle                   = getRegionTest("circle")
        val green                    = getRegionTest("green")
        val red                      = getRegionTest("red")
        val test_def                 = getRegionTest("test_def")
        val test_press               = getRegionTest("test_press")
        val test_progress            = getRegionTest("test_progress")
        val test_with_progress_def   = getRegionTest("test_with_progress_def")
        val test_with_progress_press = getRegionTest("test_with_progress_press")
        val all_test_def             = getRegionTest("all_test_def")
        val all_test_press           = getRegionTest("all_test_press")

        // atlas Items ------------------------------------------------------------------------------

        val listMenuItemsDef   = List(5) { getRegionAll("i${it.inc()}_def") }
        val listMenuItemsPress = List(5) { getRegionAll("i${it.inc()}_press") }

        // textures ------------------------------------------------------------------------------

        val ACCESS           = SpriteManager.EnumTexture.ACCESS.data.texture
        val DASHBOARD_TEXT   = SpriteManager.EnumTexture.DASHBOARD_TEXT.data.texture
        val SAVINGS_FORMA    = SpriteManager.EnumTexture.SAVINGS_FORMA.data.texture
        val CALCULATOR_FORMA = SpriteManager.EnumTexture.CALCULATOR_FORMA.data.texture

        val ANSWERS       = SpriteManager.EnumTexture.ANSWERS.data.texture
        val TEST_PROGRESS = SpriteManager.EnumTexture.TEST_PROGRESS.data.texture

        private val G1 = SpriteManager.EnumTexture.G1.data.texture
        private val G2 = SpriteManager.EnumTexture.G2.data.texture
        private val G3 = SpriteManager.EnumTexture.G3.data.texture
        private val G4 = SpriteManager.EnumTexture.G4.data.texture

        private val R1 = SpriteManager.EnumTexture.R1.data.texture
        private val R2 = SpriteManager.EnumTexture.R2.data.texture
        private val R3 = SpriteManager.EnumTexture.R3.data.texture

        val listGlossary = listOf(G1, G2, G3, G4)
        val listResult   = listOf(R1, R2, R3)
    }

}
