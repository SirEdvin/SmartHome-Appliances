package site.siredvin.smarthome.common.block

import net.minecraft.client.color.block.BlockColor
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState

class LedPanelBlockColor: BlockColor {
    override fun getColor(p0: BlockState, p1: BlockAndTintGetter?, p2: BlockPos?, p3: Int): Int {
        val color = p0.getValue(LedPanelBlock.COLOR)
        if (color == DyeColor.BLACK) {
            return 0x212121 // Basically, dark gray2
        }
        return color.textColor
    }
}