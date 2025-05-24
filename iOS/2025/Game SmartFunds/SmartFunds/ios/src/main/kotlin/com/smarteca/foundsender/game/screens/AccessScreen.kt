package com.smarteca.foundsender.game.screens

import com.smarteca.foundsender.game.actors.main.AMainAccess
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainScreen
import com.smarteca.foundsender.game.utils.advanced.AdvancedStage
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.region
import com.smarteca.foundsender.util.AppsFlyerManager
import com.smarteca.foundsender.util.permission.PermissionATT

/**
 * На цей екран ми потрапимо тільки якщо iOS 14+ і ATT = NotDetermined
 */
class AccessScreen: AdvancedMainScreen() {

    //private val aBackground = ABackground(this, gdxGame.currentBackground)

    override val aMain by lazy { AMainAccess(this) }

//    private fun loadAssets() {
//        with(gdxGame.spriteManager) {
//            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.ACCESS.data)
//            loadAtlas()
//            loadableTexturesList = mutableListOf(
//                SpriteManager.EnumTexture.ACCESS_BACKGROUND.data,
//                SpriteManager.EnumTexture.ACCESS_ACCESS.data,
//            )
//            loadTexture()
//        }
//        gdxGame.assetManager.finishLoading()
//        gdxGame.spriteManager.initAtlasAndTexture()
//    }

    override fun show() {
        //loadAssets()
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
        super.show()

        PermissionATT.requestATT {
            AppsFlyerManager.startWithCompletionHandler()

            hideScreen {
                gdxGame.navigationManager.navigate(LoaderScreen2::class.java.name)
            }
        }
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        //addBackground()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Runnable) {
        aMain.animHideMain { block.run() }
    }

    // Actors Back------------------------------------------------------------------------

//    private fun AdvancedStage.addBackground() {
//        addActor(aBackground)
//
//        val screenRatio = viewportBack.screenWidth / viewportBack.screenHeight
//        val imageRatio  = (WIDTH_UI / HEIGHT_UI)
//
//        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
//        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)
//
//        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_BLURED, TIME_ANIM_SCREEN)
//        gdxGame.currentBackground = gdxGame.assetsAll.BACKGROUND_BLURED
//    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}
