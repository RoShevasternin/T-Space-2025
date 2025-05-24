package com.gpecspertisa.sollomka.game.manager.util

import com.gpecspertisa.sollomka.game.manager.SpriteManager

class SpriteUtil {

    class Loader {

        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(regionName)

        val bar      = getRegion("bar")
        val logo     = getRegion("logo")
        val panel    = getRegion("panel")
        val progress = getRegion("progress")

        val MASK   = SpriteManager.EnumTexture.LOADER_MASK.data.texture
        val LOADER = SpriteManager.EnumTexture.LOADER.data.texture
    }

    class All {
        private fun getRegion(regionName: String) = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(regionName)
        private fun getRegionPRIVACY(regionName: String) = SpriteManager.EnumAtlas.PRIVACY.data.atlas.findRegion(regionName)
        private fun get9Path(regionName: String = "_9_header") = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(regionName)

        val balance                = getRegion("balance")
        val bar                    = getRegion("bar")
        val improve_def_1          = getRegion("improve_def_1")
        val improve_def_2          = getRegion("improve_def_2")
        val improve_def_3          = getRegion("improve_def_3")
        val improve_def_4          = getRegion("improve_def_4")
        val improve_press_1        = getRegion("improve_press_1")
        val improve_press_2        = getRegion("improve_press_2")
        val improve_press_3        = getRegion("improve_press_3")
        val improve_press_4        = getRegion("improve_press_4")
        val invest_def             = getRegion("invest_def")
        val invest_press           = getRegion("invest_press")
        val left_def               = getRegion("left_def")
        val left_press             = getRegion("left_press")
        val plus_coin              = getRegion("plus_coin")
        val prog_1                 = getRegion("prog_1")
        val prog_2                 = getRegion("prog_2")
        val prog_3                 = getRegion("prog_3")
        val prog_4                 = getRegion("prog_4")
        val right_def              = getRegion("right_def")
        val right_press            = getRegion("right_press")
        val work_def_1             = getRegion("work_def_1")
        val work_def_2             = getRegion("work_def_2")
        val work_def_3             = getRegion("work_def_3")
        val work_def_4             = getRegion("work_def_4")
        val work_press_1           = getRegion("work_press_1")
        val work_press_2           = getRegion("work_press_2")
        val work_press_3           = getRegion("work_press_3")
        val work_press_4           = getRegion("work_press_4")

        val privacy_policy_def_1   = getRegionPRIVACY("privacy_policy_def_1")
        val privacy_policy_def_2   = getRegionPRIVACY("privacy_policy_def_2")
        val privacy_policy_def_3   = getRegionPRIVACY("privacy_policy_def_3")
        val privacy_policy_def_4   = getRegionPRIVACY("privacy_policy_def_4")
        val privacy_policy_press_1 = getRegionPRIVACY("privacy_policy_press_1")
        val privacy_policy_press_2 = getRegionPRIVACY("privacy_policy_press_2")
        val privacy_policy_press_3 = getRegionPRIVACY("privacy_policy_press_3")
        val privacy_policy_press_4 = getRegionPRIVACY("privacy_policy_press_4")

        val _9_panel = get9Path()

        val MASK   = SpriteManager.EnumTexture.MASK.data.texture

        private val DIALOG_DEPOSIT_1     = SpriteManager.EnumTexture.DIALOG_DEPOSIT_1.data.texture
        private val DIALOG_DEPOSIT_2     = SpriteManager.EnumTexture.DIALOG_DEPOSIT_2.data.texture
        private val DIALOG_DEPOSIT_3     = SpriteManager.EnumTexture.DIALOG_DEPOSIT_3.data.texture
        private val DIALOG_DEPOSIT_4     = SpriteManager.EnumTexture.DIALOG_DEPOSIT_4.data.texture
        private val DIALOG_INVESTMENTS_1 = SpriteManager.EnumTexture.DIALOG_INVESTMENTS_1.data.texture
        private val DIALOG_INVESTMENTS_2 = SpriteManager.EnumTexture.DIALOG_INVESTMENTS_2.data.texture
        private val DIALOG_INVESTMENTS_3 = SpriteManager.EnumTexture.DIALOG_INVESTMENTS_3.data.texture
        private val DIALOG_INVESTMENTS_4 = SpriteManager.EnumTexture.DIALOG_INVESTMENTS_4.data.texture
        private val DIALOG_SAVINGS_1     = SpriteManager.EnumTexture.DIALOG_SAVINGS_1.data.texture
        private val DIALOG_SAVINGS_2     = SpriteManager.EnumTexture.DIALOG_SAVINGS_2.data.texture
        private val DIALOG_SAVINGS_3     = SpriteManager.EnumTexture.DIALOG_SAVINGS_3.data.texture
        private val DIALOG_SAVINGS_4     = SpriteManager.EnumTexture.DIALOG_SAVINGS_4.data.texture
        private val NEXT_LVL_2           = SpriteManager.EnumTexture.NEXT_LVL_2.data.texture
        private val NEXT_LVL_3           = SpriteManager.EnumTexture.NEXT_LVL_3.data.texture
        private val NEXT_LVL_4           = SpriteManager.EnumTexture.NEXT_LVL_4.data.texture

        private val IMPROVE_PANEL_1      = SpriteManager.EnumTexture.IMPROVE_PANEL_1.data.texture
        private val IMPROVE_PANEL_2      = SpriteManager.EnumTexture.IMPROVE_PANEL_2.data.texture
        private val IMPROVE_PANEL_3      = SpriteManager.EnumTexture.IMPROVE_PANEL_3.data.texture
        private val IMPROVE_PANEL_4      = SpriteManager.EnumTexture.IMPROVE_PANEL_4.data.texture

        private val INVEST_PANEL_1       = SpriteManager.EnumTexture.INVEST_PANEL_1.data.texture
        private val INVEST_PANEL_2       = SpriteManager.EnumTexture.INVEST_PANEL_2.data.texture
        private val INVEST_PANEL_3       = SpriteManager.EnumTexture.INVEST_PANEL_3.data.texture
        private val INVEST_PANEL_4       = SpriteManager.EnumTexture.INVEST_PANEL_4.data.texture

        private val PROFESSION_1         = SpriteManager.EnumTexture.PROFESSION_1.data.texture
        private val PROFESSION_2         = SpriteManager.EnumTexture.PROFESSION_2.data.texture
        private val PROFESSION_3         = SpriteManager.EnumTexture.PROFESSION_3.data.texture
        private val PROFESSION_4         = SpriteManager.EnumTexture.PROFESSION_4.data.texture

        val listDialogInvestments  = listOf(DIALOG_INVESTMENTS_1, DIALOG_INVESTMENTS_2, DIALOG_INVESTMENTS_3, DIALOG_INVESTMENTS_4)
        val listDialogDeposit      = listOf(DIALOG_DEPOSIT_1, DIALOG_DEPOSIT_2, DIALOG_DEPOSIT_3, DIALOG_DEPOSIT_4)
        val listDialogSavings      = listOf(DIALOG_SAVINGS_1, DIALOG_SAVINGS_2, DIALOG_SAVINGS_3, DIALOG_SAVINGS_4)
        val listNextLvl      = listOf(NEXT_LVL_2, NEXT_LVL_3, NEXT_LVL_4)
        val listImprovePanel = listOf(IMPROVE_PANEL_1,IMPROVE_PANEL_2,IMPROVE_PANEL_3,IMPROVE_PANEL_4)
        val listInvestPanel  = listOf(INVEST_PANEL_1,INVEST_PANEL_2,INVEST_PANEL_3,INVEST_PANEL_4)
        val listProfession   = listOf(PROFESSION_1,PROFESSION_2,PROFESSION_3,PROFESSION_4)

        val listProgress = listOf(prog_1, prog_2, prog_3, prog_4)

        val listAllDialogInvest = listOf(
            listDialogSavings,
            listDialogDeposit,
            listDialogInvestments,
        )

    }

}