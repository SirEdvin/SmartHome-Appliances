package site.siredvin.smarthome.common.item

import net.minecraft.core.component.DataComponents
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.BlockItemStateProperties
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import site.siredvin.broccolium.modules.base.item.DescriptiveBlockItem
import site.siredvin.smarthome.common.block.ColoredLightBlock
import site.siredvin.smarthome.common.block.LedPanelBlock
import site.siredvin.smarthome.common.block.LedPanelForm

class LedPanelItem(block: Block): DescriptiveBlockItem(block, Properties().stacksTo(64)) {

    override fun getDescriptionId(stack: ItemStack): String {
        val color = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).get(ColoredLightBlock.COLOR) ?: DyeColor.WHITE
        return "${descriptionId}.${color.getName().lowercase()}"
    }

    override fun place(context: BlockPlaceContext): InteractionResult {
        if (context.canPlace())
            return super.place(context)
        val targetState = context.level.getBlockState(context.clickedPos)
        if (targetState.`is`(block)) {
            val form = targetState.getValue(LedPanelBlock.FORM)
            if (form.directions.size == 1) {
                val newForm = LedPanelForm.searchForm(form.directions[0], context.clickedFace) ?: return super.place(context)
                val newState = targetState.setValue(LedPanelBlock.FORM, newForm)
                context.itemInHand.shrink(1)
                context.level.setBlock(context.clickedPos, newState, 3)
                return InteractionResult.CONSUME
            } else if (form.directions.size == 2) {
                val newForm = LedPanelForm.searchForm(form.directions[0], form.directions[1], context.clickedFace) ?: return super.place(context)
                val newState = targetState.setValue(LedPanelBlock.FORM, newForm)
                context.itemInHand.shrink(1)
                context.level.setBlock(context.clickedPos, newState, 3)
                return InteractionResult.CONSUME
            }
        }
        return super.place(context)
    }
}