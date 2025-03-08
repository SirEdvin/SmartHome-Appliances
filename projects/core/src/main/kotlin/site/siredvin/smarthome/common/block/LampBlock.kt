package site.siredvin.smarthome.common.block

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import site.siredvin.broccolium.modules.base.block.BaseNBTBlock
import site.siredvin.broccolium.modules.base.codec.BlockCodec
import site.siredvin.broccolium.modules.base.codec.BlockCodec.blockEntityCodec
import site.siredvin.smarthome.common.blockentity.LampBlockEntity
import site.siredvin.smarthome.common.setup.BlockEntityTypes
import java.util.stream.Stream

class LampBlock: BaseNBTBlock<LampBlockEntity>(false, Properties.of().strength(1f, 5f).sound(SoundType.WOOD).noOcclusion().lightLevel { if (it.getValue(BlockStateProperties.ENABLED)) 15 else 0 }) {

    companion object {
        val FACING = BlockStateProperties.FACING
        val ENALBED = BlockStateProperties.ENABLED
        val FAKE = BooleanProperty.create("fake")
        val CONNECTED = BooleanProperty.create("connected")
        val LAMP = Stream.of(
            Shapes.box(0.25, 0.0, 0.25, 0.75, 0.0625, 0.75),
            Shapes.box(0.3125, 0.0625, 0.3125, 0.6875, 0.4375, 0.6875)
        ).reduce { s1, s2 -> Shapes.join(s1, s2, BooleanOp.OR) }.get()
        val DOWN_LAMP = Shapes.join(
            Shapes.box(0.25, 0.9375, 0.25, 0.75, 1.0, 0.75),
            Shapes.box(0.3125, 0.5625, 0.3125, 0.6875, 0.9375, 0.6875),
            BooleanOp.OR
        )
        val SOUTH_LAMP = Shapes.join(
            Shapes.box(0.25, 0.25, 0.0, 0.75, 0.75, 0.0625),
            Shapes.box(0.3125, 0.3125, 0.0625, 0.6875, 0.6875, 0.4375),
            BooleanOp.OR
        )
        val NORTH_LAMP = Shapes.join(
            Shapes.box(0.25, 0.25, 0.9375, 0.75, 0.75, 1.0),
            Shapes.box(0.3125, 0.3125, 0.5625, 0.6875, 0.6875, 0.9375),
            BooleanOp.OR
        )
        val WEST_LAMP = Shapes.join(
            Shapes.box(0.9375, 0.25, 0.25, 1.0, 0.75, 0.75),
            Shapes.box(0.5625, 0.3125, 0.3125, 0.9375, 0.6875, 0.6875),
            BooleanOp.OR
        )
        val EAST_LAMP = Shapes.join(
            Shapes.box(0.0, 0.25, 0.25, 0.0625, 0.75, 0.75),
            Shapes.box(0.0625, 0.3125, 0.3125, 0.4375, 0.6875, 0.6875),
            BooleanOp.OR
        )
    }

    init {
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP).setValue(ENALBED, false).setValue(
            FAKE, false).setValue(CONNECTED, false))
    }

    override fun codec(): MapCodec<LampBlock> {
        return RecordCodecBuilder.mapCodec {
            return@mapCodec it.group(
                blockEntityCodec<LampBlock, BlockEntityType<LampBlockEntity>, LampBlockEntity> { BlockEntityTypes.LAMP },
                BlockCodec.propertiesCodec<LampBlock>(),
            ).apply(it) { _, _ -> LampBlock() }
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(FACING)
        builder.add(ENALBED)
        builder.add(FAKE)
        builder.add(CONNECTED)
    }

    override fun createItemStack(): ItemStack {
        return asItem().defaultInstance
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION", "KotlinRedundantDiagnosticSuppress")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = state.rotate(mirror.getRotation(state.getValue(
        FACING
    )))

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): LampBlockEntity? {
        return BlockEntityTypes.LAMP.get().create(p0, p1)
    }

    @Deprecated("Deprecated in Java")
    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.setValue(
        FACING, rotation.rotate(state.getValue(
            FACING
        )))

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? = defaultBlockState().setValue(FACING, context.clickedFace)

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext,
    ): VoxelShape = when (state.getValue(FACING)) {
        Direction.SOUTH -> SOUTH_LAMP
        Direction.NORTH -> NORTH_LAMP
        Direction.EAST -> EAST_LAMP
        Direction.WEST -> WEST_LAMP
        Direction.DOWN -> DOWN_LAMP
        else -> LAMP
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
        val blockEntity = level.getBlockEntity(blockPos) as? LampBlockEntity ?: return ItemInteractionResult.FAIL
        if (item is DyeItem) {
            if (item.dyeColor == DyeColor.BLACK) {
                blockEntity.color = 0x212121 // Basically, dark gray2
            } else {
                blockEntity.color = item.dyeColor.textColor
            }
            if (!player.isCreative) itemStack.shrink(1)
            return ItemInteractionResult.CONSUME
        }
        blockEntity.switch(player, level)
        return ItemInteractionResult.SUCCESS
    }
}