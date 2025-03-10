package site.siredvin.smarthome.data

import net.minecraft.data.PackOutput
import site.siredvin.smarthome.common.setup.Blocks
import site.siredvin.smarthome.common.setup.Items
import java.util.function.Consumer

class ModUaLanguageProvider(
    output: PackOutput,
) : ModLanguageProvider(output, "uk_ua") {

    companion object {
        private val hooks: MutableList<Consumer<ModUaLanguageProvider>> = mutableListOf()

        fun addHook(hook: Consumer<ModUaLanguageProvider>) {
            hooks.add(hook)
        }
    }

    override fun addTranslations() {
        add(Items.SMART_SCREWDRIVER.get(), "Розумна викрутна", "§6Іноді вона видає доволі високий звук")
        add(Blocks.LAMP.get(), "Лампа")
        add(Blocks.SWITCH.get(), "Перемикач")
        add(Blocks.LED_PANEL.get(), "Led-панель")
        add(Blocks.SMOOTH_LED_PANEL.get(), "Гладка led-панель")
        add(ModText.CREATIVE_TAB, "А це треба перейменувати, друже")
        add(ModText.SCREWDRIVER_TARGET_BLOCK, "Цільовий блок %s")
        hooks.forEach { it.accept(this) }
    }
}
