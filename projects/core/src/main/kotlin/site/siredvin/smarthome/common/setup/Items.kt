package site.siredvin.smarthome.common.setup

import net.minecraft.world.item.Item
import site.siredvin.broccolium.modules.base.item.DescriptiveItem
import site.siredvin.smarthome.common.item.SmartScrewdriver
import site.siredvin.smarthome.xplat.ModPlatform

object Items {
    val SMART_SCREWDRIVER = ModPlatform.registerItem("smart_screwdriver") {
        SmartScrewdriver()
    }

    fun doSomething() {
    }
}
