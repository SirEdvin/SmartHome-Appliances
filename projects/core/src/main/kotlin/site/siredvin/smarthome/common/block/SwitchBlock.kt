package site.siredvin.smarthome.common.block

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import site.siredvin.broccolium.modules.base.block.BaseNBTBlock
import site.siredvin.broccolium.modules.base.codec.BlockCodec
import site.siredvin.broccolium.modules.base.codec.BlockCodec.blockEntityCodec
import site.siredvin.broccolium.modules.base.util.BlockUtil
import site.siredvin.smarthome.common.blockentity.SwitchBlockEntity
import site.siredvin.smarthome.common.setup.ModBlockEntityTypes
import site.siredvin.smarthome.common.setup.ModBlocks

class SwitchBlock: BaseNBTBlock<SwitchBlockEntity>(false, BlockUtil.decoration()) {
    companion object {
        val FACING = BlockStateProperties.FACING
        val ENALBED = BlockStateProperties.ENABLED
        val SWITCH = Shapes.box(0.3125, 0.0, 0.25, 0.6875, 0.125, 0.75)
        val DOWN_SWITCH = Shapes.box(0.3125, 0.875, 0.25, 0.6875, 1.0, 0.75)
        val EAST_SWITCH = Shapes.box(0.0, 0.25, 0.3125, 0.125, 0.75, 0.6875)
        val NORTH_SWITCH = Shapes.box(0.3125, 0.25, 0.875, 0.6875, 0.75, 1.0)
        val WEST_SWITCH = Shapes.box(0.875, 0.25, 0.3125, 1.0, 0.75, 0.6875)
        val SOUTH_SWITCH = Shapes.box(0.3125, 0.25, 0.0, 0.6875, 0.75, 0.125)
    }

    init {
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP).setValue(ENALBED, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(FACING)
        builder.add(ENALBED)
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION", "KotlinRedundantDiagnosticSuppress")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = state.rotate(mirror.getRotation(state.getValue(
        LampBlock.FACING
    )))

    @Deprecated("Deprecated in Java")
    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.setValue(
        LampBlock.FACING, rotation.rotate(state.getValue(
            LampBlock.FACING
        )))

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(LampBlock.FACING, context.clickedFace)

    override fun codec(): MapCodec<SwitchBlock> {
        return RecordCodecBuilder.mapCodec {
            return@mapCodec it.group(
                blockEntityCodec<SwitchBlock, BlockEntityType<SwitchBlockEntity>, SwitchBlockEntity> { ModBlockEntityTypes.SWITCH },
                BlockCodec.propertiesCodec<SwitchBlock>(),
            ).apply(it) { _, _ -> SwitchBlock() }
        }
    }

    override fun createItemStack(): ItemStack {
        return ModBlocks.SWITCH.get().asItem().defaultInstance
    }

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): SwitchBlockEntity? {
        return ModBlockEntityTypes.SWITCH.get().create(p0, p1)
    }

    override fun playerDestroy(
        level: Level,
        player: Player,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BlockEntity?,
        itemStack: ItemStack
    ) {
        if (blockEntity is SwitchBlockEntity) {
            blockEntity.disconnectAll(level)
        }
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack)
    }

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext,
    ): VoxelShape = when (state.getValue(LampBlock.FACING)) {
        Direction.SOUTH -> SOUTH_SWITCH
        Direction.NORTH -> NORTH_SWITCH
        Direction.EAST -> EAST_SWITCH
        Direction.WEST -> WEST_SWITCH
        Direction.DOWN -> DOWN_SWITCH
        else -> SWITCH
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
        val blockEntity = level.getBlockEntity(blockPos) as? SwitchBlockEntity ?: return ItemInteractionResult.FAIL
        blockEntity.switch(player, level)
        return ItemInteractionResult.SUCCESS
    }
}