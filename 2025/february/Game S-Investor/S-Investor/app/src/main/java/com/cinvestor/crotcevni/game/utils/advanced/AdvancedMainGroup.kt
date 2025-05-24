package com.cinvestor.crotcevni.game.utils.advanced

import com.cinvestor.crotcevni.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}