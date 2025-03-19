package site.siredvin.smarthome

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.DyeColor
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import site.siredvin.broccolium.modules.platform.api.InnerBasePlatform
import site.siredvin.smarthome.common.setup.ModBlocks
import site.siredvin.smarthome.data.ModText
import site.siredvin.smarthome.xplat.ModPlatform
import site.siredvin.smarthome.xplat.ModRecipeIngredients

object ModCore {
    const val MOD_ID = "smarthome_appliances"

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun configureCreativeTab(builder: CreativeModeTab.Builder): CreativeModeTab.Builder = builder.icon { ModBlocks.LAMP.get().createColoredItemStack(DyeColor.LIGHT_BLUE) }
        .title(ModText.CREATIVE_TAB.text)
        .displayItems { _, output ->
            ModPlatform.holder.blocks.forEach { output.accept(it.get()) }
            ModPlatform.holder.items.forEach { output.accept(it.get()) }
            DyeColor.entries.filter { it != DyeColor.WHITE }.forEach {
                output.accept(ModBlocks.LAMP.get().createColoredItemStack(it))
                output.accept(ModBlocks.LED_PANEL.get().createColoredItemStack(it))
                output.accept(ModBlocks.SMOOTH_LED_PANEL.get().createColoredItemStack(it))
            }
        }

    fun configure(platform: InnerBasePlatform, ingredients: ModRecipeIngredients) {
        ModPlatform.configure(platform)
        ModRecipeIngredients.configure(ingredients)
    }
}
