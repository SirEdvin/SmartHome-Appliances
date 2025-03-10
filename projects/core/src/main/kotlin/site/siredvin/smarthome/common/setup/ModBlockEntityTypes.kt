package site.siredvin.smarthome.common.setup

import site.siredvin.broccolium.modules.platform.PlatformToolkit
import site.siredvin.smarthome.common.blockentity.SwitchBlockEntity
import site.siredvin.smarthome.xplat.ModPlatform

object ModBlockEntityTypes {
    val SWITCH = ModPlatform.registerBlockEntity("switch") {
        PlatformToolkit.get().createBlockEntityType(
            ::SwitchBlockEntity,
            ModBlocks.SWITCH.get()
        )
    }

    fun doSomething() {

    }
}