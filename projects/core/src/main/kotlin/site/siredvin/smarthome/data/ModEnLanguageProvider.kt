package site.siredvin.smarthome.data

import net.minecraft.data.PackOutput
import site.siredvin.smarthome.common.setup.Blocks
import site.siredvin.smarthome.common.setup.Items
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
        add(Items.TEMPLATE_ITEM.get(), "Template item", "Oh my god, where the texture go?")
        add(Blocks.LAMP.get(), "Lamp")
        add(ModText.CREATIVE_TAB, "Rename this, pal")
        hooks.forEach { it.accept(this) }
    }
}
