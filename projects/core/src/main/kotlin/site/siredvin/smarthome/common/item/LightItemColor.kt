package site.siredvin.smarthome.common.item

import net.minecraft.client.color.item.ItemColor
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.BlockItemStateProperties
import site.siredvin.smarthome.common.block.ColoredLightBlock

object LightItemColor : ItemColor {
    override fun getColor(p0: ItemStack, p1: Int): Int {
        val color = p0.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).get(ColoredLightBlock.COLOR)
        if (color != null) {
            return if (color == DyeColor.BLACK) 0x212121 else color.textColor
        }
        return DyeColor.WHITE.textColor
    }
}
