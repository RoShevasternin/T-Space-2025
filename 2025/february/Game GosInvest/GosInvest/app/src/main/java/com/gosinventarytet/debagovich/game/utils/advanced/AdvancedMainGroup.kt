package com.gosinventarytet.debagovich.game.utils.advanced

import com.gosinventarytet.debagovich.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}