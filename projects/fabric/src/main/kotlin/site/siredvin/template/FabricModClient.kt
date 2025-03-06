package site.siredvin.template

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.minecraft.resources.ResourceLocation

object FabricModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModClientCore.onInit()
        ModelLoadingPlugin.register {
            it.addModels(ModClientCore.EXTRA_MODELS.map { id -> ResourceLocation(ModCore.MOD_ID, id) })
        }
    }
}
