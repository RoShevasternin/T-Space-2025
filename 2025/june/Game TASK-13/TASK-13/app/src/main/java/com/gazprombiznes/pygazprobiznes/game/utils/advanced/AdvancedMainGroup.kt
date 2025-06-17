package com.gazprombiznes.pygazprobiznes.game.utils.advanced

import com.gazprombiznes.pygazprobiznes.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}