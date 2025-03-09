package site.siredvin.smarthome.common.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.component.DataComponents
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.BlockItemStateProperties
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import site.siredvin.broccolium.modules.base.util.BlockUtil

class LedPanelBlock: Block(BlockUtil.decoration().lightLevel {  if (it.getValue(BlockStateProperties.ENABLED)) 15 else 0 }) {
    companion object {
        val FACING = BlockStateProperties.FACING
        val ENALBED = BlockStateProperties.ENABLED
        val COLOR = EnumProperty.create("color", DyeColor::class.java)
        val AMOUNT = IntegerProperty.create("amount", 1, 2)
        val SECOND_FACING = EnumProperty.create("second_facing", Direction::class.java)
        val SHAPE = Shapes.box(
            0.0, 0.0, 0.0,
            1.0, 0.0625, 1.0
        )
        val DOWN_SHAPE = Shapes.box(
            0.0, 0.9375, 0.0,
            1.0, 1.0, 1.0
        )
        val NORTH_SHAPE = Shapes.box(
            0.0, 0.0, 0.9375,
            1.0, 1.0, 1.0
        )
        val SOUTH_SHAPE = Shapes.box(
            0.0, 0.0, 0.0,
            1.0, 1.0, 0.0625
        )
        val WEST_SHAPE = Shapes.box(
            0.9375, 0.0, 0.0,
            1.0, 1.0, 1.0
        )
        val EAST_SHAPE = Shapes.box(
            0.0, 0.0, 0.0,
            0.0625, 1.0, 1.0
        )
    }

    init {
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP).setValue(ENALBED, false).setValue(
            COLOR, DyeColor.WHITE).setValue(AMOUNT, 1).setValue(SECOND_FACING, Direction.WEST))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(FACING)
        builder.add(ENALBED)
        builder.add(COLOR)
        builder.add(AMOUNT)
        builder.add(SECOND_FACING)
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

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? = defaultBlockState().setValue(LampBlock.FACING, context.clickedFace)

    fun getShapeByDirection(direction: Direction): VoxelShape = when (direction) {
        Direction.SOUTH -> SOUTH_SHAPE
        Direction.NORTH -> NORTH_SHAPE
        Direction.EAST -> EAST_SHAPE
        Direction.WEST -> WEST_SHAPE
        Direction.DOWN -> DOWN_SHAPE
        else -> SHAPE
    }

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext,
    ): VoxelShape {
        return when (state.getValue(AMOUNT)) {
            1 -> getShapeByDirection(state.getValue(FACING))
            2 -> Shapes.join(getShapeByDirection(state.getValue(FACING)), getShapeByDirection(state.getValue(
                SECOND_FACING)), BooleanOp.OR)
            else -> SHAPE  // This should not be possible for now
        }
    }

    fun prepareItemStack(state: BlockState): ItemStack {
        val stack: ItemStack = this.asItem().defaultInstance
        val savableProperties: List<Property<*>> = savableProperties
        if (savableProperties.isNotEmpty() && !defaultBlockState().equals(state)) {
            val value = BlockItemStateProperties.EMPTY.with(COLOR, state.getValue(COLOR))
            stack.set(DataComponents.BLOCK_STATE, value)
        }
        stack.count = state.getValue(AMOUNT)
        return stack
    }

    val savableProperties: List<Property<*>>
        get() = listOf(COLOR)

    override fun playerWillDestroy(level: Level, pos: BlockPos, state: BlockState, player: Player): BlockState {
        if (!level.isClientSide && !player.isCreative) {
            val stack = prepareItemStack(state)
            val itemDrop = ItemEntity(
                level,
                pos.x.toDouble() + 0.5,
                pos.y.toDouble() + 0.5,
                pos.z.toDouble() + 0.5,
                stack,
            )
            itemDrop.setDefaultPickUpDelay()
            level.addFreshEntity(itemDrop)
        }
        return super.playerWillDestroy(level, pos, state, player)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun setPlacedBy(level: Level, pos: BlockPos, initialState: BlockState, entity: LivingEntity?, stack: ItemStack) {
        var state = initialState
        super.setPlacedBy(level, pos, state, entity, stack)
        if (!level.isClientSide) {
            if (stack.components.has(DataComponents.BLOCK_STATE)) {
                val savedState: BlockState = stack.components.get(
                    DataComponents.BLOCK_STATE,
                )!!.apply(this.defaultBlockState())
                for (property in savableProperties) {
                    @Suppress("UNCHECKED_CAST")
                    property as Property<Comparable<Any>>
                    state = state.setValue(property, savedState.getValue(property) as Comparable<Any>)
                }
            }
        }
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
        return ItemInteractionResult.SUCCESS
    }
}