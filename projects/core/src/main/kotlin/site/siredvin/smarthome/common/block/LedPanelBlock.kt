package site.siredvin.smarthome.common.block

import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class LedPanelBlock : ColoredLightBlock() {
    companion object {
        val FORM = EnumProperty.create("form", LedPanelForm::class.java)
    }

    init {
        registerDefaultState(buildDeafaultState().setValue(FORM, LedPanelForm.UP))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(FORM)
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION", "KotlinRedundantDiagnosticSuppress")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = state.rotate(
        mirror.getRotation(
            state.getValue(
                LampBlock.FACING,
            ),
        ),
    )

    @Deprecated("Deprecated in Java")
    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.setValue(
        LampBlock.FACING,
        rotation.rotate(
            state.getValue(
                LampBlock.FACING,
            ),
        ),
    )

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? = defaultBlockState().setValue(FORM, LedPanelForm.searchForm(context.clickedFace)!!)

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext,
    ): VoxelShape = state.getValue(FORM).shape

    override fun createItemStack(state: BlockState): ItemStack {
        val stack: ItemStack = this.asItem().defaultInstance
        stack.count = state.getValue(FORM).directions.size
        return stack
    }

    override val savableProperties: List<Property<*>>
        get() = listOf(COLOR)
}
