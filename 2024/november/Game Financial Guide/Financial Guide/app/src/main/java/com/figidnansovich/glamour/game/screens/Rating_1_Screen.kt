package com.figidnansovich.glamour.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.figidnansovich.glamour.game.LibGDXGame
import com.figidnansovich.glamour.game.actors.AButton
import com.figidnansovich.glamour.game.utils.GColor
import com.figidnansovich.glamour.game.utils.TIME_ANIM
import com.figidnansovich.glamour.game.utils.actor.animHide
import com.figidnansovich.glamour.game.utils.actor.animShow
import com.figidnansovich.glamour.game.utils.actor.setOnClickListener
import com.figidnansovich.glamour.game.utils.advanced.AdvancedScreen
import com.figidnansovich.glamour.game.utils.advanced.AdvancedStage
import com.figidnansovich.glamour.game.utils.font.FontParameter
import com.figidnansovich.glamour.game.utils.region

class Rating_1_Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val fontR_54 = fontGenerator_Roboto_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC).setSize(54))
    private val fontB_54 = fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC).setSize(54))
    private val fontB_64 = fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC).setSize(64))

    private val ls54_Reg = LabelStyle(fontR_54, GColor.text)
    private val ls54_Bol = LabelStyle(fontB_54, GColor.text)
    private val ls64_Bol = LabelStyle(fontB_64, GColor.text)

    private val imgLogo  = Image(game.loader.logo)
    private val btnDalee = AButton(this, AButton.Type.Dalee)
    private val lbl1     = Label("Ваша оценка", ls54_Reg)
    private val lbl2     = Label("1/10", ls54_Bol)
    private val lbl3     = Label("Вы определённо нуждаетесь\nв значительных улучшениях", ls64_Bol)
    private val lbl4     = Label("С каждой попыткой вы\nстановитесь увереннее.", ls54_Reg)

   private val img1 = Image(game.all.fon)
   private val img2 = Image(game.all.persona_padae)
   private val img3 = Image(game.all.ogo)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loader.B_Loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM) {
            animPricol()
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLogo, btnDalee)
        imgLogo.setBounds(178f,1893f,657f,180f)
        btnDalee.setBounds(277f,970f,460f,110f)

        btnDalee.setOnClickListener(game.soundUtil) {
            root.animHide(TIME_ANIM) {
                game.navigationManager.clearBackStack()
                game.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }

        addActors(lbl1, lbl2, lbl3, lbl4)
        lbl1.apply {
            setBounds(340f, 1540f, 332f, 38f)
            setAlignment(Align.center)
        }
        lbl2.apply {
            setBounds(449f, 1450f, 114f, 38f)
            setAlignment(Align.center)
        }
        lbl3.apply {
            setBounds(54f, 1313f, 905f, 111f)
            setAlignment(Align.center)
            wrap = true
        }
        lbl4.apply {
            setBounds(54f, 1189f, 905f, 92f)
            setAlignment(Align.center)
            wrap = true
        }

        addPricoluxa()

    }

    private fun AdvancedStage.addPricoluxa() {
        addActors(img1, img2, img3)
        img1.setBounds(27f, -1f, 960f, 860f)
        img1.animHide()
        img2.setBounds(129f, 2200f, 635f, 579f)
        img2.animHide()
        img3.setBounds(136f, 564f, 618f, 352f)
        img3.animHide()
    }

    private fun animPricol() {
        game.soundUtil.apply { play(win, 0.5f) }

        img1.animShow(0.35f) {
            img2.addAction(
                Actions.sequence(
                    Actions.parallel(
                        Actions.fadeIn(0.3f),
                        Actions.moveTo(129f, 308f, 0.6f, Interpolation.sine),
                    ),
                    Actions.run {
                        img3.animShow(0.25f) {
                            img2.addAction(Actions.forever(
                                Actions.sequence(
                                    Actions.moveBy(0f, 20f, 0.3f, Interpolation.sine),
                                    Actions.moveBy(0f, -20f, 0.3f, Interpolation.sine),
                                )
                            ))
                            img3.addAction(Actions.forever(
                                Actions.sequence(
                                    Actions.moveBy(0f, 20f, 0.3f, Interpolation.sine),
                                    Actions.moveBy(0f, -20f, 0.3f, Interpolation.sine),
                                )
                            ))
                        }
                    }
                )
            )
        }

    }

}