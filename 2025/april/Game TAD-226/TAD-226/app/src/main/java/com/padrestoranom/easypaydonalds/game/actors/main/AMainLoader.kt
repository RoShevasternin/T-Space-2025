package com.padrestoranom.easypaydonalds.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.padrestoranom.easypaydonalds.game.actors.button.AButton
import com.padrestoranom.easypaydonalds.game.actors.button.ATextButton
import com.padrestoranom.easypaydonalds.game.screens.LoaderScreen
import com.padrestoranom.easypaydonalds.game.utils.Acts
import com.padrestoranom.easypaydonalds.game.utils.GameColor
import com.padrestoranom.easypaydonalds.game.utils.TIME_ANIM_SCREEN
import com.padrestoranom.easypaydonalds.game.utils.actor.animDelay
import com.padrestoranom.easypaydonalds.game.utils.actor.animShow
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGroup
import com.padrestoranom.easypaydonalds.game.utils.font.FontParameter
import com.padrestoranom.easypaydonalds.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font53        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(53))
    private val font53r       = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(53))

    private val ls53  = Label.LabelStyle(font53, GameColor.black_1B)
    private val ls53r = Label.LabelStyle(font53r, Color.WHITE)

    private val imgLogo   = Image(gdxGame.assetsLoader.logo)
    private val imgLoader = Image(gdxGame.assetsLoader.sambolet)
    private val lblText   = Label("Mali durumunuzu kontrol altında tutun - akıllıca harcayın ve tasarruf edin!", ls53)
    private val btn       = ATextButton(screen, "başla", ls53r, AButton.Type.Blue)

//    private val progress  = AProgressLoader(screen)

    var blockClick = {}

    override fun addActorsOnGroup() {
        addImgLL()
        addLblText()
        addBtn()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActors(imgLogo, imgLoader)
        imgLogo.setBounds(211f, 1005f, 428f, 377f)

        imgLoader.setBounds(340f, 1155f, 154f, 154f)
        imgLoader.setOrigin(Align.center)
        imgLoader.addAction(Acts.forever(
            Acts.parallel(
                Acts.sequence(
                    Acts.rotateBy(20f, 0.5f, Interpolation.sineIn),
                    Acts.rotateBy(-40f, 1f, Interpolation.sineOut),
                    Acts.rotateTo(0f, 0.45f, Interpolation.sineIn),
                ),
                Acts.sequence(
                    Acts.scaleTo(0.85f, 0.85f, 0.5f, Interpolation.sineIn),
                    Acts.scaleTo(1f, 1f, 0.5f, Interpolation.sine),
                ),
            )
        ))
    }

    private fun addLblText() {
        addActor(lblText)
        lblText.color.a = 0f
        lblText.setBounds(95f, 573f, 644f, 144f)
        lblText.setAlignment(Align.center)
        lblText.wrap = true

        lblText.animDelay(0.5f) {
            lblText.animShow(TIME_ANIM_SCREEN)
        }
    }

    private fun addBtn() {
        addActor(btn)
        btn.setBounds(44f, 157f, 745f, 112f)
        btn.color.a = 0f
        btn.disable()

        btn.setOnClickListener {
            blockClick()
        }
    }


    private fun addProgress() {
        //addActor(progress)
        //progress.setBounds(67f, 687f, 558f, 8f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        //progress.progressPercentFlow.value = percent.toFloat()
    }

    fun showBtn() {
        btn.animShow(TIME_ANIM_SCREEN) {
            btn.enable()
        }
    }

}