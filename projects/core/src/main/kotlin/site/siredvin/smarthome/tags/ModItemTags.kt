package site.siredvin.smarthome.tags

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import site.siredvin.smarthome.ModCore

object ModItemTags {
    val LIGHT_BLOCK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "light_block"))
}