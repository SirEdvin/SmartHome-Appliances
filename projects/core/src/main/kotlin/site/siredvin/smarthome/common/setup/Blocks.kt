package site.siredvin.smarthome.common.setup

import site.siredvin.smarthome.common.block.LampBlock
import site.siredvin.smarthome.xplat.ModPlatform

object Blocks {
    val LAMP = ModPlatform.registerBlock("lamp", ::LampBlock)
    fun doSomething() {}
}
