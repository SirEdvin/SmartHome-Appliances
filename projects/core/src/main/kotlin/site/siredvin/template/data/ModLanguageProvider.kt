package site.siredvin.template.data

import net.minecraft.data.PackOutput
import site.siredvin.broccolium.modules.data.lang.LanguageProvider
import site.siredvin.template.ModCore
import site.siredvin.template.xplat.ModPlatform
import java.util.stream.Stream

abstract class ModLanguageProvider(output: PackOutput, locale: String) :
    LanguageProvider(
        output,
        ModCore.MOD_ID,
        locale,
        ModPlatform.holder,
        *ModText.entries.toTypedArray(),
    ) {

    companion object {
        private val extraExpectedKeys: MutableList<String> = mutableListOf()

        fun addExpectedKey(key: String) {
            extraExpectedKeys.add(key)
        }
    }

    override fun getExpectedKeys(): Stream<String> = Stream.concat(super.getExpectedKeys(), extraExpectedKeys.stream())
}
