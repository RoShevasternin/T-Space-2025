package com.romander.navfenixgater.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.SizeScaler
import com.romander.navfenixgater.game.utils.actor.setBoundsScaled
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.gdxGame

class ATop(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 692f)

    private val imgLogo = Image(gdxGame.assetsAll.logo)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(GameColor.gray)))
        addActor(imgLogo)
        imgLogo.setBoundsScaled(sizeScaler, 188f, 28f, 316f, 94f)
    }

}