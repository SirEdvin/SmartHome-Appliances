package site.siredvin.smarthome

import net.minecraft.world.item.CreativeModeTab
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import site.siredvin.broccolium.modules.platform.api.InnerBasePlatform
import site.siredvin.smarthome.common.setup.Items
import site.siredvin.smarthome.data.ModText
import site.siredvin.smarthome.xplat.ModPlatform
import site.siredvin.smarthome.xplat.ModRecipeIngredients

object ModCore {
    const val MOD_ID = "smarthome_appliances"

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun configureCreativeTab(builder: CreativeModeTab.Builder): CreativeModeTab.Builder = builder.icon { Items.SMART_SCREWDRIVER.get().defaultInstance }
        .title(ModText.CREATIVE_TAB.text)
        .displayItems { _, output ->
            ModPlatform.holder.blocks.forEach { output.accept(it.get()) }
            ModPlatform.holder.items.forEach { output.accept(it.get()) }
        }

    fun configure(platform: InnerBasePlatform, ingredients: ModRecipeIngredients) {
        ModPlatform.configure(platform)
        ModRecipeIngredients.configure(ingredients)
    }
}
