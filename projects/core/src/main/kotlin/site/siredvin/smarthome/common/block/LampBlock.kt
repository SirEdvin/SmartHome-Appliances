package site.siredvin.smarthome.common.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.stream.Stream

class LampBlock: ColoredLightBlock() {

    companion object {
        val FACING = BlockStateProperties.FACING
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
        registerDefaultState(buildDeafaultState().setValue(FACING, Direction.UP))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(FACING)
    }

    override fun createItemStack(state: BlockState): ItemStack {
        return asItem().defaultInstance
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION", "KotlinRedundantDiagnosticSuppress")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = state.rotate(mirror.getRotation(state.getValue(
        FACING
    )))

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
}