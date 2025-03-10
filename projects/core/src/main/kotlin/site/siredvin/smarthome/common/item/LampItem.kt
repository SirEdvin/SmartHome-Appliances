package site.siredvin.smarthome.common.item

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import site.siredvin.broccolium.modules.base.item.DescriptiveBlockItem

class LampItem(block: Block): DescriptiveBlockItem(block, Properties().stacksTo(64)) {
    override fun getName(itemStack: ItemStack): Component {
        return super.getName(itemStack)
    }
}