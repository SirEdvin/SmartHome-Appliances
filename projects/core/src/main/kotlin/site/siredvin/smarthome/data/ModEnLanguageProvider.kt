package site.siredvin.smarthome.data

import net.minecraft.data.PackOutput
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import site.siredvin.smarthome.common.setup.ModBlocks
import site.siredvin.smarthome.common.setup.ModItems
import java.util.function.Consumer

class ModEnLanguageProvider(
    output: PackOutput,
) : ModLanguageProvider(output, "en_us") {

    companion object {
        private val hooks: MutableList<Consumer<ModEnLanguageProvider>> = mutableListOf()

        fun addHook(hook: Consumer<ModEnLanguageProvider>) {
            hooks.add(hook)
        }
    }

    override fun addTranslations() {
        add(ModItems.SMART_SCREWDRIVER.get(), "Smart screwdriver", "§6Sometimes you can hear it makes a pretty pitch sound")
        add(ModBlocks.LAMP.get(), "Lamp")
        add(ModBlocks.SWITCH.get(), "Switch")
        add(ModBlocks.LED_PANEL.get(), "Led panel")
        add(ModBlocks.SMOOTH_LED_PANEL.get(), "Smooth led panel")
        DyeColor.entries.forEach { it ->
            add(ModBlocks.LAMP.get().createColoredItemStack(it).descriptionId, "${it.name.lowercase().replaceFirstChar { chr -> chr.titlecaseChar() } } lamp")
            add(ModBlocks.LED_PANEL.get().createColoredItemStack(it).descriptionId, "${it.name.lowercase().replaceFirstChar { chr -> chr.titlecaseChar() } } led panel")
            add(ModBlocks.SMOOTH_LED_PANEL.get().createColoredItemStack(it).descriptionId, "${it.name.lowercase().replaceFirstChar { chr -> chr.titlecaseChar() } } smooth led panel")
        }
        add(ModText.CREATIVE_TAB, "Rename this, pal")
        add(ModText.SCREWDRIVER_TARGET_BLOCK, "Target block %s")
        hooks.forEach { it.accept(this) }
    }
}
