package site.siredvin.smarthome.common.block

import net.minecraft.client.color.block.BlockColor
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import site.siredvin.smarthome.common.blockentity.LampBlockEntity

class LampBlockColor: BlockColor {
    override fun getColor(p0: BlockState, p1: BlockAndTintGetter?, p2: BlockPos?, p3: Int): Int {
        if (p1 != null && p2 != null)
            return (p1.getBlockEntity(p2) as? LampBlockEntity)?.color ?: 0
        return DyeColor.WHITE.textColor
    }
}