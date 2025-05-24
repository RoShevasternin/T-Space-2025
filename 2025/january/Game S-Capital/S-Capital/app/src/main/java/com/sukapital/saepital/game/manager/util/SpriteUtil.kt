package com.sukapital.saepital.game.manager.util

import com.sukapital.saepital.game.manager.SpriteManager

class SpriteUtil {

    class Splash {

        private fun get9Path(regionName: String) = SpriteManager.EnumAtlas.LOADER.data.atlas.createPatch(regionName)
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(regionName)

        val logo          = getRegion("logo")
        val progress      = getRegion("progress")
        val progress_back = getRegion("progress_back")

        val panel_9 = get9Path("_9_white")

        val MASK          = SpriteManager.EnumTexture.SPLASH_MASK.data.texture
        val SPLASH_LOADER = SpriteManager.EnumTexture.SPLASH_LOADER.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)

        val add_doh_ros_def   = getRegion("add_doh_ros_def")
        val add_doh_ros_press = getRegion("add_doh_ros_press")
        val blog_def          = getRegion("blog_def")
        val blog_press        = getRegion("blog_press")
        val btn_desire        = getRegion("btn_desire")
        val delete_def        = getRegion("delete_def")
        val delete_press      = getRegion("delete_press")
        val desc              = getRegion("desc")
        val logo              = getRegion("logo")
        val main_def          = getRegion("main_def")
        val main_press        = getRegion("main_press")
        val plus_def          = getRegion("plus_def")
        val plus_press        = getRegion("plus_press")
        val see_all_def       = getRegion("see_all_def")
        val see_all_press     = getRegion("see_all_press")
        val your_desire       = getRegion("your_desire")
        val box_expense       = getRegion("box_expense")
        val box_income        = getRegion("box_income")
        val Desire            = getRegion("Desire")
        val IncomeExpense     = getRegion("IncomeExpense")

        private val color_1   = getRegion("color_1")
        private val color_2   = getRegion("color_2")
        private val color_3   = getRegion("color_3")

        val BLOG_LIST          = SpriteManager.EnumTexture.BLOG_LIST.data.texture
        val IncomeExpensePanel = SpriteManager.EnumTexture.IncomeExpensePanel.data.texture
        private val TEXT_1    = SpriteManager.EnumTexture.TEXT_1.data.texture
        private val TEXT_2    = SpriteManager.EnumTexture.TEXT_2.data.texture
        private val TEXT_3    = SpriteManager.EnumTexture.TEXT_3.data.texture
        private val TEXT_4    = SpriteManager.EnumTexture.TEXT_4.data.texture
        private val TEXT_5    = SpriteManager.EnumTexture.TEXT_5.data.texture

        val listColor = listOf(color_1, color_2, color_3)
        val listBlog  = listOf(TEXT_1, TEXT_2, TEXT_3, TEXT_4, TEXT_5)


    }

}