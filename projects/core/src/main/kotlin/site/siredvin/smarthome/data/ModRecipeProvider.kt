package site.siredvin.smarthome.data

import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.Items
import site.siredvin.broccolium.modules.data.recipe.TweakedShapedRecipeBuilder
import site.siredvin.broccolium.modules.data.recipe.TweakedShapelessRecipeBuilder
import site.siredvin.smarthome.ModCore
import site.siredvin.smarthome.common.setup.ModBlocks
import site.siredvin.smarthome.xplat.ModRecipeIngredients
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
        TweakedShapedRecipeBuilder(ModBlocks.LED_PANEL.get().createColoredItemStack(DyeColor.WHITE).copyWithCount(9))
            .define('P', Items.GLASS_PANE)
            .define('T', Items.TORCH)
            .define('S', Items.SMOOTH_STONE)
            .pattern("PPP")
            .pattern("TTT")
            .pattern("SSS")
            .save(consumer)
        TweakedShapelessRecipeBuilder(ModBlocks.SWITCH.get().createItemStack().copyWithCount(2))
            .requires(Items.STONE_BUTTON).requires(Items.SMOOTH_STONE).save(consumer)
        DyeColor.entries.forEach {
            TweakedShapelessRecipeBuilder(ModBlocks.LAMP.get().createColoredItemStack(it))
                .requires(ModBlocks.LAMP.get().asItem()).requires(DyeItem.byColor(it)).save(consumer, "${ModCore.MOD_ID}:lamp_${it.name.lowercase()}")
            TweakedShapelessRecipeBuilder(ModBlocks.LAMP.get().createColoredItemStack(it).copyWithCount(8))
                .requires(ModBlocks.LAMP.get().asItem(), count = 8).requires(DyeItem.byColor(it)).save(consumer, "${ModCore.MOD_ID}:lamp_${it.name.lowercase()}_8")
            TweakedShapelessRecipeBuilder(ModBlocks.LED_PANEL.get().createColoredItemStack(it))
                .requires(ModBlocks.LED_PANEL.get().asItem()).requires(DyeItem.byColor(it)).save(consumer, "${ModCore.MOD_ID}:led_panel_${it.name.lowercase()}")
            TweakedShapelessRecipeBuilder(ModBlocks.LED_PANEL.get().createColoredItemStack(it).copyWithCount(8))
                .requires(ModBlocks.LED_PANEL.get().asItem(), count = 8).requires(DyeItem.byColor(it)).save(consumer, "${ModCore.MOD_ID}:led_panel_${it.name.lowercase()}_8")
            TweakedShapelessRecipeBuilder(ModBlocks.SMOOTH_LED_PANEL.get().createColoredItemStack(it))
                .requires(ModRecipeIngredients.get().itemStackIngredient(ModBlocks.LED_PANEL.get().createColoredItemStack(it))).save(consumer, "${ModCore.MOD_ID}:smooth_led_panel_${it.name.lowercase()}")
            TweakedShapelessRecipeBuilder(ModBlocks.LED_PANEL.get().createColoredItemStack(it))
                .requires(ModRecipeIngredients.get().itemStackIngredient(ModBlocks.SMOOTH_LED_PANEL.get().createColoredItemStack(it))).save(consumer, "${ModCore.MOD_ID}:led_panel_${it.name.lowercase()}_back")
        }
    }
}
