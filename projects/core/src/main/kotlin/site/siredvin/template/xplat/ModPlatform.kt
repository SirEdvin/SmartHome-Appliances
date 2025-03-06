package site.siredvin.template.xplat

import site.siredvin.broccolium.modules.platform.BasePlatform
import site.siredvin.broccolium.modules.platform.ModInformationTracker
import site.siredvin.broccolium.modules.platform.api.InnerBasePlatform

object ModPlatform : BasePlatform() {
    private var impl: InnerBasePlatform? = null
    private val innerTracker = ModInformationTracker()

    fun configure(impl: InnerBasePlatform) {
        this.impl = impl
    }

    override val baseInnerPlatform: InnerBasePlatform
        get() {
            if (impl == null) {
                throw IllegalStateException("You should configure upw ModPlatform first")
            }
            return impl!!
        }

    override val modInformationTracker: ModInformationTracker
        get() = innerTracker
}
