package site.siredvin.smarthome.common.setup

import org.lwjgl.system.Platform
import site.siredvin.broccolium.modules.platform.PlatformToolkit
import site.siredvin.smarthome.common.blockentity.LampBlockEntity
import site.siredvin.smarthome.common.blockentity.SwitchBlockEntity
import site.siredvin.smarthome.xplat.ModPlatform

object BlockEntityTypes {
    val LAMP = ModPlatform.registerBlockEntity("lamp") {
        PlatformToolkit.get().createBlockEntityType(
            ::LampBlockEntity,
            Blocks.LAMP.get()
        )
    }

    val SWITCH = ModPlatform.registerBlockEntity("switch") {
        PlatformToolkit.get().createBlockEntityType(
            ::SwitchBlockEntity,
            Blocks.SWITCH.get()
        )
    }

    fun doSomething() {

    }
}