package site.siredvin.smarthome.common.setup

import site.siredvin.broccolium.modules.platform.PlatformToolkit
import site.siredvin.smarthome.common.blockentity.LampBlockEntity
import site.siredvin.smarthome.xplat.ModPlatform

object BlockEntityTypes {
    val LAMP = ModPlatform.registerBlockEntity("lamp") {
        PlatformToolkit.get().createBlockEntityType(
            ::LampBlockEntity,
            Blocks.LAMP.get()
        )
    }

    fun doSomething() {

    }
}