package site.siredvin.smarthome.common.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import site.siredvin.broccolium.modules.base.blockentity.MutableNBTBlockEntity
import site.siredvin.smarthome.common.block.LampBlock
import site.siredvin.smarthome.common.setup.BlockEntityTypes
import kotlin.jvm.optionals.getOrNull

class LampBlockEntity(blockPos: BlockPos, blockState: BlockState): MutableNBTBlockEntity(BlockEntityTypes.LAMP.get(), blockPos, blockState) {
    companion object {
        val COLOR_TAG = "color"
        val CONTROLLER_TAG = "controller"
    }

    override val updateFlag: Int
        get() = Block.UPDATE_ALL_IMMEDIATE

    private var internalColor: Int = DyeColor.WHITE.textColor
    private var internalControllerBlock: BlockPos? = null

    var color: Int
        get() = internalColor
        set(value) {
            internalColor = value
            pushInternalDataChangeToClient(blockState.setValue(LampBlock.FAKE, !blockState.getValue(LampBlock.FAKE)))
        }

    var controlledBlock: BlockPos?
        get() = internalControllerBlock
        set(value) {
            internalControllerBlock = value
            pushInternalDataChangeToClient(blockState.setValue(LampBlock.CONNECTED, internalControllerBlock != null))
        }

    override fun loadInternalData(data: CompoundTag, state: BlockState?): BlockState {
        if (data.contains(COLOR_TAG)) {
            val previousColor = internalColor
            internalColor = data.getInt(COLOR_TAG)
            if (internalColor != previousColor && level?.isClientSide == true){
                triggerRenderUpdate()
            }
        }
        if (data.contains(CONTROLLER_TAG)) {
            internalControllerBlock = NbtUtils.readBlockPos(data, CONTROLLER_TAG).getOrNull()
        }
        return state?: blockState
    }

    override fun saveInternalData(data: CompoundTag): CompoundTag {
        data.putInt(COLOR_TAG, internalColor)
        internalControllerBlock?.let { NbtUtils.writeBlockPos(it) }?.let { data.put(CONTROLLER_TAG, it) }
        return data
    }

    fun set(enabled: Boolean) {
        pushInternalDataChangeToClient(blockState.setValue(BlockStateProperties.ENABLED, enabled))
    }

    fun switch(player: Player, level: Level) {
        set(!blockState.getValue(BlockStateProperties.ENABLED))
        level.playSound(
            player,
            blockPos,
            SoundEvents.LEVER_CLICK,
            SoundSource.BLOCKS,
            0.3f,
            if (blockState.getValue(BlockStateProperties.ENABLED)) 0.5f else 0.6f
        )
    }
}