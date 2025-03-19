package site.siredvin.smarthome.data

import net.minecraft.world.item.DyeColor
import site.siredvin.broccolium.modules.data.api.GeneratorSink
import site.siredvin.smarthome.ModCore
import site.siredvin.smarthome.common.setup.ModBlocks

object ModDataProviders {
    fun add(generator: GeneratorSink) {
        DyeColor.entries.forEach {
            ModLanguageProvider.addExpectedKey(ModBlocks.LAMP.get().createColoredItemStack(it).descriptionId)
            ModLanguageProvider.addExpectedKey(ModBlocks.LED_PANEL.get().createColoredItemStack(it).descriptionId)
            ModLanguageProvider.addExpectedKey(ModBlocks.SMOOTH_LED_PANEL.get().createColoredItemStack(it).descriptionId)
        }
        generator.add(::ModRecipeProvider)
        generator.lootTable(ModLootTableProvider.getTables())
        generator.models(ModBlockModelProvider::addModels, ModItemModelProvider::addModels)
        generator.add(::ModEnLanguageProvider)
        generator.add(::ModUaLanguageProvider)
        generator.itemTags(
            ModCore.MOD_ID,
            ModTagsProvider::itemTags,
            generator.blockTags(ModCore.MOD_ID, ModTagsProvider::blockTags),
        )
    }
}
