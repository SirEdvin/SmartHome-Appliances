package site.siredvin.smarthome.common.item

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import site.siredvin.broccolium.modules.base.item.DescriptiveBlockItem
import site.siredvin.smarthome.common.block.LedPanelBlock
import site.siredvin.smarthome.common.setup.Blocks

class LedPanelItem(block: Block): DescriptiveBlockItem(block, Properties().stacksTo(64)) {

    override fun place(context: BlockPlaceContext): InteractionResult {
        if (context.canPlace())
            return super.place(context)
        val targetState = context.level.getBlockState(context.clickedPos)
        if (targetState.`is`(Blocks.LED_PANEL.get())) {
            if (targetState.getValue(LedPanelBlock.AMOUNT) == 1) {
                val facing = targetState.getValue(LedPanelBlock.FACING)
                if (facing == context.clickedFace.opposite) return super.place(context) // We can't extend in the opposite direction
                val newState = targetState.setValue(LedPanelBlock.AMOUNT, 2)
                    .setValue(LedPanelBlock.SECOND_FACING, context.clickedFace)
                context.itemInHand.shrink(1)
                context.level.setBlock(context.clickedPos, newState, 3)
                return InteractionResult.CONSUME
            }
        }
        return super.place(context)
    }
}