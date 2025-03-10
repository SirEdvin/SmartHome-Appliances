package site.siredvin.smarthome.data

import site.siredvin.broccolium.modules.data.api.GeneratorSink
import site.siredvin.smarthome.ModCore

object ModDataProviders {
    fun add(generator: GeneratorSink) {
        generator.add(::ModRecipeProvider)
        generator.lootTable(ModLootTableProvider.getTables())
        generator.models(ModBlockModelProvider::addModels, ModItemModelProvider::addModels)
        generator.add(::ModEnLanguageProvider)
        generator.add(::ModUaLanguageProvider)
        generator.itemTags(
            ModCore.MOD_ID, ModTagsProvider::itemTags,
            generator.blockTags(ModCore.MOD_ID, ModTagsProvider::blockTags)
        )
    }
}
