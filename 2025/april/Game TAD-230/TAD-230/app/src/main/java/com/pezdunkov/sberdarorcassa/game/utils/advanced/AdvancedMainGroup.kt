package com.pezdunkov.sberdarorcassa.game.utils.advanced

import com.pezdunkov.sberdarorcassa.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}