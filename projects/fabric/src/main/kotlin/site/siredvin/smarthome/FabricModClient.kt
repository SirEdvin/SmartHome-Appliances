package site.siredvin.smarthome

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.fabricmc.fabric.mixin.client.rendering.WorldRendererMixin
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers
import net.minecraft.resources.ResourceLocation
import site.siredvin.smarthome.client.render.SmartScrewdriverRenderingLayer

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
        ModClientCore.EXTRA_BLOCK_ENTITY_RENDERERS.forEach {
            BlockEntityRenderers.register(it.get(), ModClientCore.getBlockEntityRendererProvider(it.get()))
        }
        WorldRenderEvents.AFTER_ENTITIES.register {
            val matrix = it.matrixStack()
            val consumers = it.consumers()
            if (matrix != null && consumers != null) {
                SmartScrewdriverRenderingLayer.render(matrix, consumers, it.world(), it.camera())
            }
        }
    }
}
