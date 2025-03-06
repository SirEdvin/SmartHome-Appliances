package site.siredvin.template.fabric

import site.siredvin.broccolium.modules.platform.FabricInnerBasePlatform
import site.siredvin.template.ModCore

object FabricModInnerPlatform : FabricInnerBasePlatform() {
    override val modID: String
        get() = ModCore.MOD_ID
}
