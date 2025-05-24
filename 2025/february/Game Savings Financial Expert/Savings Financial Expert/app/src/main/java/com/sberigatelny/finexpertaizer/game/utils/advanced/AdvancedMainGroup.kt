package com.sberigatelny.finexpertaizer.game.utils.advanced

import com.sberigatelny.finexpertaizer.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}