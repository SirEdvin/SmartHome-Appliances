package site.siredvin.smarthome.data

import site.siredvin.broccolium.modules.data.api.TextRecord
import site.siredvin.smarthome.ModCore

enum class ModText : TextRecord {
    CREATIVE_TAB,
    SCREWDRIVER_TARGET_BLOCK,
    ;

    override val textID: String by lazy {
        String.format("text.%s.%s", ModCore.MOD_ID, name.lowercase())
    }
}
