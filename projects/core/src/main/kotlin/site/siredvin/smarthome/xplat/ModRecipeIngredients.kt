package site.siredvin.smarthome.xplat

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

interface ModRecipeIngredients {

    fun itemStackIngredient(itemStack: ItemStack): Ingredient

    companion object {
        private var impl: ModRecipeIngredients? = null

        fun configure(impl: ModRecipeIngredients) {
            this.impl = impl
        }

        fun get(): ModRecipeIngredients {
            if (impl == null) {
                throw IllegalStateException("You should init PeripheralWorks Platform first")
            }
            return impl!!
        }
    }
}
