package site.siredvin.smarthome.common.setup

import site.siredvin.broccolium.modules.platform.PlatformToolkit
import site.siredvin.smarthome.common.blockentity.SwitchBlockEntity
import site.siredvin.smarthome.xplat.ModPlatform

object BlockEntityTypes {
    val SWITCH = ModPlatform.registerBlockEntity("switch") {
        PlatformToolkit.get().createBlockEntityType(
            ::SwitchBlockEntity,
            Blocks.SWITCH.get()
        )
    }

    fun doSomething() {

    }
}