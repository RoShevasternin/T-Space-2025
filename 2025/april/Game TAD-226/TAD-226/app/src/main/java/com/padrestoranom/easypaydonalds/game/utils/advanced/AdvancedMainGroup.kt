package com.padrestoranom.easypaydonalds.game.utils.advanced

import com.padrestoranom.easypaydonalds.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}