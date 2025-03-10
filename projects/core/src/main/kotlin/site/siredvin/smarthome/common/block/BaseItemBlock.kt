package site.siredvin.smarthome.common.block

import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponents
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.BlockItemStateProperties
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property

abstract class BaseItemBlock(properties: Properties): Block(properties) {

    abstract val savableProperties: List<Property<*>>

    abstract fun createItemStack(state: BlockState): ItemStack

    open fun prepareItemStack(state: BlockState): ItemStack {
        val stack: ItemStack = createItemStack(state)
        val savableProperties: List<Property<*>> = savableProperties
        if (savableProperties.isNotEmpty()) {
            var value = BlockItemStateProperties.EMPTY
            savableProperties.forEach{
                @Suppress("UNCHECKED_CAST")
                it as Property<Comparable<Any>>
                value = value.with(it, state.getValue(it))
            }
            stack.set(DataComponents.BLOCK_STATE, value)
        }
        return stack
    }

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
}