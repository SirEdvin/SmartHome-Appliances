package site.siredvin.smarthome.data

import net.minecraft.data.PackOutput
import net.minecraft.world.item.DyeColor
import site.siredvin.smarthome.common.setup.ModBlocks
import site.siredvin.smarthome.common.setup.ModItems
import java.util.function.Consumer

class ModUaLanguageProvider(
    output: PackOutput,
) : ModLanguageProvider(output, "uk_ua") {

    companion object {
        private val hooks: MutableList<Consumer<ModUaLanguageProvider>> = mutableListOf()

        private val COLOR_MAP: Map<DyeColor, String> = mapOf(
            DyeColor.WHITE to "Біла",
            DyeColor.ORANGE to "Помаранчева",
            DyeColor.MAGENTA to "Пурпурова",
            DyeColor.LIGHT_BLUE to "Світло-блакитна",
            DyeColor.YELLOW to "Жовта",
            DyeColor.LIME to "Лаймова",
            DyeColor.PINK to "Рожева",
            DyeColor.GRAY to "Сіра",
            DyeColor.LIGHT_GRAY to "Світло-сіра",
            DyeColor.CYAN to "Блакитна",
            DyeColor.PURPLE to "Фіолетова",
            DyeColor.BLUE to "Синя",
            DyeColor.BROWN to "Коричнева",
            DyeColor.GREEN to "Зелена",
            DyeColor.RED to "Червона",
            DyeColor.BLACK to "Чорна",
        )

        fun addHook(hook: Consumer<ModUaLanguageProvider>) {
            hooks.add(hook)
        }
    }

    override fun addTranslations() {
        add(ModItems.SMART_SCREWDRIVER.get(), "Розумна викрутна", "§6Іноді вона видає доволі високий звук")
        add(ModBlocks.LAMP.get(), "Лампа")
        add(ModBlocks.SWITCH.get(), "Перемикач")
        add(ModBlocks.LED_PANEL.get(), "Led-панель")
        add(ModBlocks.SMOOTH_LED_PANEL.get(), "Гладка led-панель")
        DyeColor.entries.forEach { it ->
            add(ModBlocks.LAMP.get().createColoredItemStack(it).descriptionId, "${COLOR_MAP[it]} лампа")
            add(ModBlocks.LED_PANEL.get().createColoredItemStack(it).descriptionId, "${COLOR_MAP[it]} led-панель")
            add(ModBlocks.SMOOTH_LED_PANEL.get().createColoredItemStack(it).descriptionId, "${COLOR_MAP[it]} гладка led-панель")
        }
        add(ModText.CREATIVE_TAB, "SmartHome Appliances")
        add(ModText.SCREWDRIVER_TARGET_BLOCK, "Цільовий блок %s")
        hooks.forEach { it.accept(this) }
    }
}
