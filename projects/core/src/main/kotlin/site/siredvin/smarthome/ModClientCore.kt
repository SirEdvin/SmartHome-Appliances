package site.siredvin.smarthome

import com.mojang.authlib.minecraft.client.MinecraftClient
import net.minecraft.client.Minecraft
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.block.BlockColors
import net.minecraft.client.color.item.ItemColors
import net.minecraft.resources.ResourceLocation
import site.siredvin.smarthome.common.block.LampBlockColor
import site.siredvin.smarthome.common.item.LampItemColor
import site.siredvin.smarthome.common.setup.Blocks
import site.siredvin.smarthome.common.setup.Items
import java.util.function.Consumer

object ModClientCore {
    val EXTRA_MODELS = emptyArray<String>()

    fun registerExtraModels(register: Consumer<ResourceLocation>) {
        EXTRA_MODELS.forEach { register.accept(ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, it)) }
    }

    fun registerBlockColors(blockColors: BlockColors) {
        blockColors.register(LampBlockColor(), Blocks.LAMP.get())
    }

    fun registerItemColors(itemColors: ItemColors) {
        itemColors.register(LampItemColor(), Blocks.LAMP.get().asItem())
    }

    fun onInit() {
    }
}
