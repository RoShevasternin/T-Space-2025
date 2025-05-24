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

class Rating_5_Screen(override val game: LibGDXGame) : AdvancedScreen() {

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
    private val lbl2     = Label("5/10", ls54_Bol)
    private val lbl3     = Label("Вы показали хороший уровень, но есть ещё пространство для улучшения", ls64_Bol)
    private val lbl4     = Label("Замечательный результат, который говорит о глубоком понимании материала", ls54_Reg)

   private val img1 = Image(game.all.bottom)
   private val img2 = Image(game.all.stupeni)
   private val img3 = Image(game.all.vau)
   private val img4 = Image(game.all.chel)

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
        btnDalee.setBounds(277f,870f,459f,108f)

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
            setBounds(54f, 1248f, 905f, 176f)
            setAlignment(Align.center)
            wrap = true
        }
        lbl4.apply {
            setBounds(54f, 1070f, 905f, 102f)
            setAlignment(Align.center)
            wrap = true
        }

        addPricoluxa()

    }

    private fun AdvancedStage.addPricoluxa() {
        addActors(img1, img2, img3, img4)
        img1.setBounds(65f, -70f, 888f, 70f)
        img2.setBounds(121f, 24f, 776f, 0f)
        img3.setBounds(406f, 296f, 279f, 110f)
        img3.animHide()
        img4.setBounds(-340f, 405f, 331f, 396f)
    }

    private fun animPricol() {
        game.soundUtil.apply { play(win, 0.5f) }

        img1.addAction(Actions.sequence(
            Actions.moveTo(65f, -9f, 0.3f, Interpolation.swingOut),
            Actions.run {
                img2.addAction(Actions.sequence(
                    Actions.sizeTo(776f, 370f, 0.5f, Interpolation.swingOut),
                    Actions.run {
                        img4.addAction(Actions.sequence(
                            Actions.moveTo(12f, 116f, 0.35f, Interpolation.linear),
                            Actions.run {
                                img4.addAction(Actions.sequence(
                                    Actions.moveTo(96f, 292f, 0.4f, Interpolation.sine),
                                    Actions.run {
                                        img4.addAction(Actions.sequence(
                                            Actions.moveTo(229f, 238f, 0.3f, Interpolation.slowFast),
                                            Actions.run {
                                                img4.addAction(Actions.sequence(
                                                    Actions.moveTo(464f, 421f, 0.4f, Interpolation.sine),
                                                    Actions.run {
                                                       img3.animShow(0.25f)
                                                    }
                                                ))
                                            }
                                        ))
                                    }
                                ))
                            }
                        ))
                    }
                ))
            }
        ))
    }

}