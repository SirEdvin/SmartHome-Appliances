package site.siredvin.template.xplat

import net.minecraft.resources.ResourceLocation
import site.siredvin.broccolium.modules.platform.PlatformToolkit
import site.siredvin.template.ModCore
import site.siredvin.template.common.setup.Blocks
import site.siredvin.template.common.setup.Items

object ModCommonHooks {

    fun onRegister() {
        Items.doSomething()
        Blocks.doSomething()
        ModPlatform.registerCreativeTab(
            ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "tab"),
            ModCore.configureCreativeTab(PlatformToolkit.get().createTabBuilder()).build(),
        )
    }
}
