package site.siredvin.smarthome.fabric

import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import site.siredvin.smarthome.xplat.ModRecipeIngredients

object FabricModRecipeIngredients : ModRecipeIngredients {
    override fun itemStackIngredient(itemStack: ItemStack): Ingredient = ComponentsIngredient(Ingredient.of(itemStack), itemStack.componentsPatch).toVanilla()
}
