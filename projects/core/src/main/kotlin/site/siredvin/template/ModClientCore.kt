package site.siredvin.template

import net.minecraft.resources.ResourceLocation
import java.util.function.Consumer

object ModClientCore {
    val EXTRA_MODELS = emptyArray<String>()

    fun registerExtraModels(register: Consumer<ResourceLocation>) {
        EXTRA_MODELS.forEach { register.accept(ResourceLocation(ModCore.MOD_ID, it)) }
    }

    fun onInit() {
    }
}
