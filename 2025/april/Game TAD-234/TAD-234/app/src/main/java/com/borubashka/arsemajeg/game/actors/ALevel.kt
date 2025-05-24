package com.borubashka.arsemajeg.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.borubashka.arsemajeg.game.data.DataLevel
import com.borubashka.arsemajeg.game.utils.SizeScaler
import com.borubashka.arsemajeg.game.utils.actor.setBoundsScaled
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedGroup
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedScreen
import com.borubashka.arsemajeg.game.utils.gdxGame

class ALevel(
    override val screen: AdvancedScreen,
    val dataLevel: DataLevel,
    val ls59: Label.LabelStyle,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 125f)

    private val indexStars = if (dataLevel.stars == -1) 0 else dataLevel.stars.dec()

    private val imgCircle = Image(gdxGame.assetsAll.circle_red)
    private val imgLock   = Image(gdxGame.assetsAll.lock)
    private val lblLvL    = Label(dataLevel.lvlNum.toString(), ls59)
    private val imgStars  = Image(gdxGame.assetsAll.listStars[indexStars])

    override fun addActorsOnGroup() {
        addAndFillActor(imgCircle)
        addImgLock()
        addLblLvL()
        addImgStars()

        setUpChildrenVisibility()
    }

    // Actors ------------------------------------------------------------

    private fun addImgLock() {
        addActor(imgLock)
        imgLock.setBoundsScaled(sizeScaler, 30f, 25f, 64f, 75f)
    }

    private fun addLblLvL() {
        addActor(lblLvL)
        lblLvL.setBoundsScaled(sizeScaler, 46f, 42f, 31f, 42f)
        lblLvL.setAlignment(Align.center)
    }

    private fun addImgStars() {
        addActor(imgStars)
        imgStars.setBoundsScaled(sizeScaler, -41f, -49f, 207f, 88f)
    }

    // Logic ------------------------------------------------------------

    private fun setUpChildrenVisibility() {
        children.forEach { it.color.a = 0f }
        imgCircle.color.a = 1f

        if (dataLevel.isLock) {
            imgLock.color.a = 1f
        } else {
            lblLvL.color.a = 1f
            if (dataLevel.stars != -1) imgStars.color.a = 1f
        }
    }

}