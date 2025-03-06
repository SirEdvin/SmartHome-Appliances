package site.siredvin.template.forge

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import site.siredvin.broccolium.modules.platform.ForgeInnerBasePlatform
import site.siredvin.template.ForgeMod
import site.siredvin.template.ModCore

object ForgeModPlatform : ForgeInnerBasePlatform() {
    override val modID: String
        get() = ModCore.MOD_ID

    override val itemsRegistry: DeferredRegister<Item>
        get() = ForgeMod.itemsRegistry

    override val blocksRegistry: DeferredRegister<Block>
        get() = ForgeMod.blocksRegistry

    override val creativeTabRegistry: DeferredRegister<CreativeModeTab>
        get() = ForgeMod.creativeTabRegistry
}
