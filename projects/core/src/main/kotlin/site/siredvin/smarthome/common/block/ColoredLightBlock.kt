package site.siredvin.smarthome.common.block

import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.BlockHitResult
import site.siredvin.broccolium.modules.base.util.BlockUtil

abstract class ColoredLightBlock: BaseItemBlock(BlockUtil.decoration().lightLevel {  if (it.getValue(BlockStateProperties.ENABLED)) 15 else 0 }) {
    companion object {
        val ENALBED = BlockStateProperties.ENABLED
        val CONNECTED = BooleanProperty.create("connected")
        val COLOR = EnumProperty.create("color", DyeColor::class.java)
    }
    override val savableProperties: List<Property<*>>
        get() = listOf(COLOR)

    fun buildDeafaultState() = getStateDefinition().any().setValue(ENALBED, false).setValue(
        COLOR, DyeColor.WHITE).setValue(CONNECTED, false)

    fun createColoredItemStack(color: DyeColor): ItemStack {
        return prepareItemStack(defaultBlockState().setValue(COLOR, color))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(ENALBED)
        builder.add(COLOR)
        builder.add(CONNECTED)
    }

    override fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        hand: InteractionHand,
        blockHitResult: BlockHitResult,
    ): ItemInteractionResult {
        val item = itemStack.item
        if (item is DyeItem) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(COLOR, item.dyeColor))
            if (!player.isCreative) itemStack.shrink(1)
            return ItemInteractionResult.CONSUME
        }
        level.setBlockAndUpdate(blockPos, blockState.setValue(ENALBED, !blockState.getValue(ENALBED)))
        level.playSound(
            player,
            blockPos,
            SoundEvents.LEVER_CLICK,
            SoundSource.BLOCKS,
            0.3f,
            if (blockState.getValue(BlockStateProperties.ENABLED)) 0.5f else 0.6f
        )
        return ItemInteractionResult.SUCCESS
    }
}