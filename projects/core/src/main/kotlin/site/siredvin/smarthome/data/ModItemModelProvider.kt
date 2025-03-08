package site.siredvin.smarthome.data

import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates
import site.siredvin.smarthome.common.setup.Items

object ModItemModelProvider {

    fun addModels(generators: ItemModelGenerators) {
        generators.generateFlatItem(Items.SMART_SCREWDRIVER.get(), ModelTemplates.FLAT_ITEM)
    }
}
