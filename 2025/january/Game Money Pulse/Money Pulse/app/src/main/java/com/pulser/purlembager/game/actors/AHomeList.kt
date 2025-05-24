package com.pulser.purlembager.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.actors.progress.AProgress
import com.pulser.purlembager.game.utils.GameColor
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.font.FontParameter
import com.pulser.purlembager.game.utils.gdxGame

class AHomeList(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font50        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(50))

    private val ls50 = Label.LabelStyle(font50, GameColor.black_31)

    private val imgList = Image(gdxGame.assetsAll.list)

    val progY = AProgress(screen, gdxGame.assetsAll.p_y)
    val progG = AProgress(screen, gdxGame.assetsAll.p_g)
    val progB = AProgress(screen, gdxGame.assetsAll.p_b)

    val lblY = Label("₽ 0", ls50)
    val lblG = Label("₽ 0", ls50)
    val lblB = Label("₽ 0", ls50)

    override fun addActorsOnGroup() {
        addAndFillActor(imgList)
        addActors(progY, progG, progB)
        progY.apply {
            setBounds(170f, 1038f, 834f, 19f)
        }
        progG.apply {
            setBounds(165f, 655f, 834f, 19f)
        }
        progB.apply {
            setBounds(165f, 307f, 834f, 19f)
        }

        addActors(lblY, lblG, lblB)
        lblY.apply {
            setBounds(789f, 1108f, 226f, 64f)
            setAlignment(Align.right)
        }
        lblG.apply {
            setBounds(789f, 740f, 226f, 64f)
            setAlignment(Align.right)
        }
        lblB.apply {
            setBounds(789f, 392f, 226f, 64f)
            setAlignment(Align.right)
        }
    }

}