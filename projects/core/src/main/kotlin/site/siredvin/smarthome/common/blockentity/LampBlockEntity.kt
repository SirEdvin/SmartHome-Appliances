package site.siredvin.smarthome.common.blockentity

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import site.siredvin.broccolium.modules.base.blockentity.MutableNBTBlockEntity
import site.siredvin.smarthome.common.block.LampBlock
import site.siredvin.smarthome.common.setup.BlockEntityTypes
import java.util.*

class LampBlockEntity(blockPos: BlockPos, blockState: BlockState): MutableNBTBlockEntity(BlockEntityTypes.LAMP.get(), blockPos, blockState) {
    companion object {
        val COLOR_TAG = "color"
    }

    override val updateFlag: Int
        get() = Block.UPDATE_ALL_IMMEDIATE

    private var internalColor: Int = DyeColor.WHITE.textColor

    var color: Int
        get() = internalColor
        set(value) {
            internalColor = value
            pushInternalDataChangeToClient(blockState.setValue(LampBlock.FAKE, !blockState.getValue(LampBlock.FAKE)))
        }

    override fun loadInternalData(data: CompoundTag, state: BlockState?): BlockState {
        if (data.contains(COLOR_TAG)) {
            val previousColor = internalColor
            internalColor = data.getInt(COLOR_TAG)
            if (internalColor != previousColor && level?.isClientSide == true){
                triggerRenderUpdate()
            }
        }
        return state?: blockState
    }

    override fun saveInternalData(data: CompoundTag): CompoundTag {
        data.putInt(COLOR_TAG, internalColor)
        return data
    }
}