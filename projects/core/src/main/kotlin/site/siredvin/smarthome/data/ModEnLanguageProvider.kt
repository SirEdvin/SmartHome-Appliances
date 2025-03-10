package site.siredvin.smarthome.data

import net.minecraft.data.PackOutput
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
        add(ModText.CREATIVE_TAB, "Rename this, pal")
        add(ModText.SCREWDRIVER_TARGET_BLOCK, "Target block %s")
        hooks.forEach { it.accept(this) }
    }
}
