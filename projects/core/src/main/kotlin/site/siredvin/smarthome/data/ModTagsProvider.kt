package site.siredvin.smarthome.data

import net.minecraft.world.level.block.Block
import site.siredvin.broccolium.modules.data.api.ItemTagConsumer
import site.siredvin.broccolium.modules.data.api.TagConsumer
import site.siredvin.smarthome.common.setup.Blocks
import site.siredvin.smarthome.tags.ModBlockTags
import site.siredvin.smarthome.tags.ModItemTags

object ModTagsProvider {
    val LIGHT_BLOCKS = listOf(
        Blocks.LAMP,
        Blocks.LED_PANEL,
        Blocks.SMOOTH_LED_PANEL
    )

    @JvmStatic
    fun blockTags(consumer: TagConsumer<Block>) {
        LIGHT_BLOCKS.forEach { consumer.tag(ModBlockTags.LIGHT_BLOCK).add(it.get()) }
    }

    @JvmStatic
    fun itemTags(consumer: ItemTagConsumer) {
        LIGHT_BLOCKS.forEach { consumer.tag(ModItemTags.LIGHT_BLOCK).add(it.get().asItem()) }
    }
}