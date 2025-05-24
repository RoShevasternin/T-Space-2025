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

class Rating_10_Screen(override val game: LibGDXGame) : AdvancedScreen() {

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
    private val lbl2     = Label("10/10", ls54_Bol)
    private val lbl3     = Label("Отличная работа!", ls64_Bol)
    private val lbl4     = Label("Замечательный результат, который говорит о глубоком понимании материала", ls54_Reg)

   private val img1 = Image(game.all.cubek)
   private val img2 = Image(game.all.mishen)
   private val img3 = Image(game.all.strela)

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
        btnDalee.setBounds(276f,982f,460f,110f)

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
            setBounds(434f, 1450f, 145f, 38f)
            setAlignment(Align.center)
        }
        lbl3.apply {
            setBounds(237f, 1378f, 538f, 46f)
            setAlignment(Align.center)
        }
        lbl4.apply {
            setBounds(54f, 1197f, 905f, 146f)
            setAlignment(Align.center)
            wrap = true
        }

        addPricoluxa()

    }

    private fun AdvancedStage.addPricoluxa() {
        addActors(img1, img2, img3)
        img1.setBounds(1015f, 0f, 748f, 729f)
        img2.setBounds(110f, -556f, 401f, 556f)
        img3.setBounds(-380f, 270f, 374f, 86f)
    }

    private fun animPricol() {
        game.soundUtil.apply { play(win, 0.5f) }

        img1.addAction(Actions.sequence(
            Actions.moveTo(210f, 0f, 0.4f, Interpolation.swingOut),
            Actions.run {
                img2.addAction(Actions.sequence(
                    Actions.moveTo(110f, -13f, 0.3f, Interpolation.swingOut),
                    Actions.run {
                        img3.addAction(Actions.sequence(
                            Actions.moveTo(-62f, 261f, 0.125f, Interpolation.slowFast),
                            Actions.run {
                                img2.addAction(Actions.moveTo(176f, -13f, 0.08f))
                                img3.addAction(Actions.moveTo(0f, 261f, 0.08f))
                            }
                        ))
                    }
                ))
            }
        ))
    }

}