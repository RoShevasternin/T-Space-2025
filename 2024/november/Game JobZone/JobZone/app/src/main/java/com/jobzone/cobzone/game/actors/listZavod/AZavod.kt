package com.jobzone.cobzone.game.actors.listZavod

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jobzone.cobzone.game.actors.button.AButton
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen

class AZavod(
    override val screen: AdvancedScreen,
    val zavodID: Int,
): AdvancedGroup() {

    private val imgPanel     = Image(screen.game.assetsAll.panel)
    private val imgPotencial = Image(screen.game.assetsAll.potencial)
    private val imgText      = Image(screen.game.assetsAll.listVac[zavodID])
    private val btnPodrobne  = AButton(screen, AButton.Type.Podrobnee)

    var blockClick = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActors(imgPotencial, imgText, btnPodrobne)

        imgPotencial.setBounds(58f, 364f, 614f, 42f)
        imgText.setBounds(58f, 107f, 614f, 287f)
        btnPodrobne.setBounds(58f, 44f, 614f, 63f)

        btnPodrobne.setOnClickListener { blockClick() }
    }

}