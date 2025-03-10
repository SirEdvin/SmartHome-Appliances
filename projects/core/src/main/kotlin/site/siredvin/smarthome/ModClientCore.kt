package site.siredvin.smarthome

import net.minecraft.client.color.block.BlockColors
import net.minecraft.client.color.item.ItemColors
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import site.siredvin.smarthome.client.renderer.SwitchBlockEntityRenderer
import site.siredvin.smarthome.common.block.LightBlockColor
import site.siredvin.smarthome.common.item.LightItemColor
import site.siredvin.smarthome.common.setup.BlockEntityTypes
import site.siredvin.smarthome.common.setup.Blocks
import java.util.function.Consumer
import java.util.function.Supplier

object ModClientCore {
    val EXTRA_MODELS = emptyArray<String>()

    fun registerExtraModels(register: Consumer<ResourceLocation>) {
        EXTRA_MODELS.forEach { register.accept(ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, it)) }
    }

    fun registerBlockColors(blockColors: BlockColors) {
        blockColors.register(LightBlockColor, Blocks.LAMP.get())
        blockColors.register(LightBlockColor, Blocks.LED_PANEL.get())
        blockColors.register(LightBlockColor, Blocks.SMOOTH_LED_PANEL.get())
    }

    fun registerItemColors(itemColors: ItemColors) {
        itemColors.register(LightItemColor, Blocks.LAMP.get().asItem())
        itemColors.register(LightItemColor, Blocks.LED_PANEL.get().asItem())
        itemColors.register(LightItemColor, Blocks.SMOOTH_LED_PANEL.get().asItem())
    }

    @Suppress("UNCHECKED_CAST")
    val EXTRA_BLOCK_ENTITY_RENDERERS: Array<Supplier<BlockEntityType<BlockEntity>>> = arrayOf(
//        BlockEntityTypes.SWITCH as Supplier<BlockEntityType<BlockEntity>>,
    )

    @Suppress("UNCHECKED_CAST")
    fun getBlockEntityRendererProvider(type: BlockEntityType<BlockEntity>): BlockEntityRendererProvider<BlockEntity> {
        if (type == BlockEntityTypes.SWITCH.get()) {
            return BlockEntityRendererProvider { SwitchBlockEntityRenderer() } as BlockEntityRendererProvider<BlockEntity>
        }
        throw IllegalArgumentException("There is no extra renderer for $type")
    }

    fun onInit() {
    }
}
