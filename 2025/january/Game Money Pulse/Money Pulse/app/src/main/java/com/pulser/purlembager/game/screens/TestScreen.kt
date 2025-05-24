//package com.pulser.purlembager.game.screens
//
//import com.badlogic.gdx.graphics.Color
//import com.badlogic.gdx.graphics.OrthographicCamera
//import com.badlogic.gdx.scenes.scene2d.ui.Image
//import com.pulser.purlembager.game.actors.AHomePulse
//import com.pulser.purlembager.game.actors.ATmpGroup
//import com.pulser.purlembager.game.actors.button.AButton
//import com.pulser.purlembager.game.actors.main.AMainMenu
//import com.pulser.purlembager.game.actors.shader.AMaskGroup
//import com.pulser.purlembager.game.utils.Block
//import com.pulser.purlembager.game.utils.actor.disable
//import com.pulser.purlembager.game.utils.actor.setBoundsScaled
//import com.pulser.purlembager.game.utils.actor.setOnClickListener
//import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
//import com.pulser.purlembager.game.utils.advanced.AdvancedStage
//import com.pulser.purlembager.game.utils.gdxGame
//import com.pulser.purlembager.game.utils.runGDX
//import kotlinx.coroutines.launch
//
//class TestScreen: AdvancedScreen() {
//
//    override fun AdvancedStage.addActorsOnStageUI() {
//        gdxGame.backgroundColor = Color.valueOf("565656")
//        backBackgroundImage.setOnClickListener {
//            (viewportUI.camera as OrthographicCamera).zoom += 0.25f
//        }
//
//        val imgFrame = Image(drawerUtil.getTexture(Color.valueOf("565656")))
//        addActor(imgFrame)
//        imgFrame.setBounds(0f, 1945f, 1177f, 605f)
//
//        val imgA0 = Image(drawerUtil.getTexture(Color.valueOf("D00000")))
//        val imgB0 = Image(drawerUtil.getTexture(Color.valueOf("0048CD")))
//
//        addActors(imgA0, imgB0)
//        imgA0.apply {
//            debug()
//            setBounds(100f, 2159f-500, 330f, 330f)
//            color.a = 1f
//        }
//        imgB0.apply {
//            debug()
//            setBounds(265f, 1994f-500, 330f, 330f)
//            color.a = 0.5f
//
////            addAction(Actions.forever(Actions.sequence(
////                Actions.fadeOut(2f),
////                Actions.fadeIn(2f),
////            )))
//        }
//
//        val imgA = Image(drawerUtil.getTexture(Color.valueOf("D00000")))
//        val imgB = Image(drawerUtil.getTexture(Color.valueOf("0048CD")))
//
//        val maskGroup = AMaskGroup(this@TestScreen, gdxGame.assetsAll.test)
//        addActor(maskGroup)
//        maskGroup.setBounds(100f, 100f, 741f, 741f)
//        maskGroup.debug()
//
////        maskGroup.addActors(imgA, imgB)
////        imgA.apply {
////            this.debug()
////            this.setBounds(-100f, 244f, 597f, 597f)
////            this.color.a = 1f
////        }
////        imgB.apply {
////            this.debug()
////            this.setBounds(244f, -100f, 597f, 597f)
////            this.color.a = 0.5f
////        }
//
//        maskGroup.addActors(imgA, imgB)
//        imgA.apply {
//            this.debug()
//            this.setBounds(122f, 288f, 330f, 330f)
//            this.color.a = 1f
//        }
//        imgB.apply {
//            this.debug()
//            this.setBounds(287f, 123f, 330f, 330f)
//            this.color.a = 0.5f
//        }
//
//    }
//
//}