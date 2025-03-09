package site.siredvin.smarthome.data

import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import site.siredvin.broccolium.modules.data.loot.LootTableHelper
import site.siredvin.smarthome.common.setup.Blocks
import site.siredvin.smarthome.xplat.ModPlatform
import java.util.function.BiConsumer

object ModLootTableProvider {
    fun getTables(): List<LootTableProvider.SubProviderEntry> = listOf(
        LootTableProvider.SubProviderEntry({
            LootTableSubProvider {
                registerBlocks(it)
            }
        }, LootContextParamSets.BLOCK),
    )

    fun registerBlocks(@Suppress("UNUSED_PARAMETER") consumer: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {
        val lootTable = LootTableHelper(ModPlatform.holder)
        lootTable.computedDrop(Blocks.LAMP)
        lootTable.computedDrop(Blocks.SWITCH)
        lootTable.computedDrop(Blocks.LED_PANEL)
        lootTable.validate()
    }
}
