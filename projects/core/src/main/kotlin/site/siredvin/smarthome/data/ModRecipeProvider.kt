package site.siredvin.smarthome.data

import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.references.Blocks
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.Items
import site.siredvin.broccolium.modules.data.recipe.TweakedShapedRecipeBuilder
import site.siredvin.broccolium.modules.data.recipe.TweakedShapelessRecipeBuilder
import site.siredvin.smarthome.common.setup.ModBlocks
import java.util.concurrent.CompletableFuture

class ModRecipeProvider(output: PackOutput, registries: CompletableFuture<HolderLookup.Provider>) :
    RecipeProvider(
        output,
        registries,
    ) {
    override fun buildRecipes(consumer: RecipeOutput) {
        TweakedShapedRecipeBuilder(ModBlocks.LAMP.get().createColoredItemStack(DyeColor.WHITE).copyWithCount(4))
            .define('P', Items.GLASS_PANE)
            .define('T', Items.TORCH)
            .define('S', Items.SMOOTH_STONE)
            .pattern(" P ")
            .pattern("PTP")
            .pattern(" S ")
            .save(consumer)
        DyeColor.entries.forEach {
            TweakedShapelessRecipeBuilder(ModBlocks.LAMP.get().createColoredItemStack(it))
                .requires(ModBlocks.LAMP.get().asItem()).requires(DyeItem.byColor(it)).save(consumer, "lamp_${it.name.lowercase()}")
            TweakedShapelessRecipeBuilder(ModBlocks.LAMP.get().createColoredItemStack(it).copyWithCount(8))
                .requires(ModBlocks.LAMP.get().asItem(), count = 8).requires(DyeItem.byColor(it)).save(consumer, "lamp_${it.name.lowercase()}_8")
        }
    }
}
