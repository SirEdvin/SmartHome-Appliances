package site.siredvin.smarthome.common.item

import net.minecraft.client.color.item.ItemColor
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData

class LampItemColor: ItemColor {
    override fun getColor(p0: ItemStack, p1: Int): Int {
        if (p1 == 1)
            return DyeColor.GREEN.textColor
        val customData = p0.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
        if (customData.contains("color")) {
            return customData.getInt("color")
        }
        return DyeColor.WHITE.textColor
    }
}