package site.siredvin.smarthome.common.setup

import site.siredvin.smarthome.common.item.SmartScrewdriver
import site.siredvin.smarthome.xplat.ModPlatform

object ModItems {
    val SMART_SCREWDRIVER = ModPlatform.registerItem("smart_screwdriver") {
        SmartScrewdriver()
    }

    fun doSomething() {
    }
}
