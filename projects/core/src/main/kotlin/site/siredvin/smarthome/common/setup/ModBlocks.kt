package site.siredvin.smarthome.common.setup

import site.siredvin.smarthome.common.block.LampBlock
import site.siredvin.smarthome.common.block.LedPanelBlock
import site.siredvin.smarthome.common.block.SwitchBlock
import site.siredvin.smarthome.common.item.LampItem
import site.siredvin.smarthome.common.item.LedPanelItem
import site.siredvin.smarthome.xplat.ModPlatform

object ModBlocks {
    val LAMP = ModPlatform.registerBlock("lamp", ::LampBlock, ::LampItem)
    val SWITCH = ModPlatform.registerBlock("switch", ::SwitchBlock)
    val LED_PANEL = ModPlatform.registerBlock("led_panel", ::LedPanelBlock, ::LedPanelItem)
    val SMOOTH_LED_PANEL = ModPlatform.registerBlock("smooth_led_panel", ::LedPanelBlock, ::LedPanelItem)
    fun doSomething() {}
}
