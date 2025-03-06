package site.siredvin.template
import net.fabricmc.api.ModInitializer
import site.siredvin.broccolium.FabricBroccolium
import site.siredvin.template.fabric.FabricModInnerPlatform
import site.siredvin.template.fabric.FabricModRecipeIngredients
import site.siredvin.template.xplat.ModCommonHooks

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
