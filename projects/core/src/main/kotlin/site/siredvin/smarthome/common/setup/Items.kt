package site.siredvin.smarthome.common.setup

import net.minecraft.world.item.Item
import site.siredvin.broccolium.modules.base.item.DescriptiveItem
import site.siredvin.smarthome.xplat.ModPlatform

object Items {
    val TEMPLATE_ITEM = ModPlatform.registerItem("template_item") {
        DescriptiveItem(
            Item.Properties(),
        )
    }

    fun doSomething() {
    }
}
