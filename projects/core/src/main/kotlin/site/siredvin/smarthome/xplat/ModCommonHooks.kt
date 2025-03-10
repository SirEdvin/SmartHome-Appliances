package site.siredvin.smarthome.xplat

import net.minecraft.resources.ResourceLocation
import site.siredvin.broccolium.modules.platform.PlatformToolkit
import site.siredvin.smarthome.ModCore
import site.siredvin.smarthome.common.setup.ModBlockEntityTypes
import site.siredvin.smarthome.common.setup.ModBlocks
import site.siredvin.smarthome.common.setup.ModItems

object ModCommonHooks {

    fun onRegister() {
        ModItems.doSomething()
        ModBlocks.doSomething()
        ModBlockEntityTypes.doSomething()
        ModPlatform.registerCreativeTab(
            ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "tab"),
            ModCore.configureCreativeTab(PlatformToolkit.get().createTabBuilder()).build(),
        )
    }
}
