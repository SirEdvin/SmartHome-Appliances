package site.siredvin.smarthome.common.setup

import site.siredvin.smarthome.common.block.LampBlock
import site.siredvin.smarthome.common.block.SwitchBlock
import site.siredvin.smarthome.common.block.LedPanelBlock
import site.siredvin.smarthome.common.item.LedPanelItem
import site.siredvin.smarthome.xplat.ModPlatform

object Blocks {
    val LAMP = ModPlatform.registerBlock("lamp", ::LampBlock)
    val SWITCH = ModPlatform.registerBlock("switch", ::SwitchBlock)
    val LED_PANEL = ModPlatform.registerBlock("led_panel", ::LedPanelBlock, ::LedPanelItem)
    fun doSomething() {}
}
