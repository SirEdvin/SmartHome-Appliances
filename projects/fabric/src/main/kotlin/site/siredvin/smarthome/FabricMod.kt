package site.siredvin.smarthome
import net.fabricmc.api.ModInitializer
import site.siredvin.broccolium.FabricBroccolium
import site.siredvin.broccolium.modules.platform.FabricPlatformToolkit
import site.siredvin.smarthome.fabric.FabricModInnerPlatform
import site.siredvin.smarthome.fabric.FabricModRecipeIngredients
import site.siredvin.smarthome.xplat.ModCommonHooks

@Suppress("UNUSED")
object FabricMod : ModInitializer {

    override fun onInitialize() {
        // Register configuration
        FabricBroccolium.sayHi()
        ModCore.configure(FabricModInnerPlatform, FabricModRecipeIngredients)
        // Register items and blocks
        ModCommonHooks.onRegister()
    }
}
