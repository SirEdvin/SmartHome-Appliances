package site.siredvin.template.data

import site.siredvin.broccolium.modules.data.api.TextRecord
import site.siredvin.template.ModCore

enum class ModText : TextRecord {
    CREATIVE_TAB,
    ;

    override val textID: String by lazy {
        String.format("text.%s.%s", ModCore.MOD_ID, name.lowercase())
    }
}
