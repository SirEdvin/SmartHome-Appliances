package site.siredvin.smarthome.common.item

import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.BlockItemStateProperties
import net.minecraft.world.level.block.Block
import site.siredvin.broccolium.modules.base.item.DescriptiveBlockItem
import site.siredvin.smarthome.common.block.ColoredLightBlock

class LampItem(block: Block): DescriptiveBlockItem(block, Properties().stacksTo(64)) {

    override fun getDescriptionId(stack: ItemStack): String {
        val color = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).get(ColoredLightBlock.COLOR) ?: DyeColor.WHITE
        return "${descriptionId}.${color.getName().lowercase()}"
    }
}