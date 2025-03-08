package site.siredvin.smarthome.common.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.IntArrayTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.nbt.Tag
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import site.siredvin.broccolium.modules.base.blockentity.MutableNBTBlockEntity
import site.siredvin.smarthome.common.setup.BlockEntityTypes

class SwitchBlockEntity(blockPos: BlockPos, blockState: BlockState): MutableNBTBlockEntity(BlockEntityTypes.SWITCH.get(), blockPos, blockState) {

    companion object {
        const val CONNECTED_BLOCKS_TAG = "connectedBlocks"
    }

    private val connectedBlocks: MutableSet<BlockPos> = mutableSetOf()

    override fun loadInternalData(data: CompoundTag, state: BlockState?): BlockState {
        if (data.contains(CONNECTED_BLOCKS_TAG)) {
            val blocks = data.getList(CONNECTED_BLOCKS_TAG, Tag.TAG_INT_ARRAY.toInt())
            blocks.forEach {
                if (it is IntArrayTag) {
                    if (it.size == 3)
                        connectedBlocks.add(BlockPos(
                            it[0].asInt,
                            it[1].asInt,
                            it[2].asInt
                        ))
                }
            }
        }
        return state?: blockState
    }

    fun connect(target: BlockPos, level: Level): Boolean {
        val blockEntity = level.getBlockEntity(target)
        if (blockEntity is LampBlockEntity) {
            val result = connectedBlocks.add(target)
            pushInternalDataChangeToClient()
            return result
        }
        return false
    }

    override fun saveInternalData(data: CompoundTag): CompoundTag {
        if (connectedBlocks.isNotEmpty()) {
            val list = ListTag()
            connectedBlocks.forEach {
                list.add(NbtUtils.writeBlockPos(it))
            }
            data.put(CONNECTED_BLOCKS_TAG, list)
        }
        return data
    }

    fun switch(player: Player?, level: Level) {
        val targetValue = !blockState.getValue(
            BlockStateProperties.ENABLED)
        pushInternalDataChangeToClient(blockState.setValue(
            BlockStateProperties.ENABLED, targetValue))
        connectedBlocks.forEach {
            val targetBlockEntity = level.getBlockEntity(it)
            if (targetBlockEntity is LampBlockEntity) {
                targetBlockEntity.set(targetValue)
            }
        }
        if (player != null) {
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
}