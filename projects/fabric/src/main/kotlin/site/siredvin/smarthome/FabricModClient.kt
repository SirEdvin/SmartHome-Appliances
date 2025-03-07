package site.siredvin.smarthome

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.minecraft.resources.ResourceLocation

object FabricModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModClientCore.onInit()
        ModelLoadingPlugin.register {
            it.addModels(ModClientCore.EXTRA_MODELS.map { id -> ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, id) })
        }
        ClientLifecycleEvents.CLIENT_STARTED.register {
            ModClientCore.registerBlockColors(it.blockColors)
            ModClientCore.registerItemColors(it.itemColors)
        }
    }
}
