package site.siredvin.smarthome.fabric

import site.siredvin.broccolium.modules.platform.FabricInnerBasePlatform
import site.siredvin.smarthome.ModCore

object FabricModInnerPlatform : FabricInnerBasePlatform() {
    override val modID: String
        get() = ModCore.MOD_ID
}
