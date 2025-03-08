package site.siredvin.smarthome.common.item

import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Pose
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import site.siredvin.broccolium.modules.base.item.DescriptiveItem
import site.siredvin.smarthome.common.blockentity.LampBlockEntity
import site.siredvin.smarthome.common.blockentity.SwitchBlockEntity
import site.siredvin.smarthome.data.ModText

class SmartScrewdriver: DescriptiveItem(Properties().stacksTo(1)) {

    companion object {
        val TARGET_BLOCK_TAG = "targetBlock"
    }

    override fun use(level: Level, player: Player, interactionHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemInHand = player.getItemInHand(interactionHand)
        if (interactionHand == InteractionHand.OFF_HAND)
            return InteractionResultHolder.pass(itemInHand)
        val customData = itemInHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
        if (customData.isEmpty)
            return InteractionResultHolder.pass(itemInHand)
        val data = customData.copyTag()
        if (!data.contains(TARGET_BLOCK_TAG))
            return InteractionResultHolder.pass(itemInHand)
        itemInHand.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
        return InteractionResultHolder.pass(itemInHand)
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        if (context.player?.pose != Pose.CROUCHING)
            return InteractionResult.PASS
        val blockState = context.level.getBlockState(context.clickedPos)
        val itemInHand = context.itemInHand
        if (blockState.isAir) {
            val customData = itemInHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
            if (customData.isEmpty)
                return InteractionResult.PASS
            val data = customData.copyTag()
            if (!data.contains(TARGET_BLOCK_TAG))
                return InteractionResult.PASS
            itemInHand.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
            return InteractionResult.SUCCESS
        }
        val blockEntity = context.level.getBlockEntity(context.clickedPos)
        val data = itemInHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
        if (blockEntity is SwitchBlockEntity) {
            data.put(TARGET_BLOCK_TAG, NbtUtils.writeBlockPos(context.clickedPos))
            itemInHand.set(DataComponents.CUSTOM_DATA, CustomData.of(data))
            return InteractionResult.SUCCESS
        }
        if (data.contains(TARGET_BLOCK_TAG) && blockEntity is LampBlockEntity) {
            val attachedBlockEntity = context.level.getBlockEntity(NbtUtils.readBlockPos(data, TARGET_BLOCK_TAG).get())
            if (attachedBlockEntity !is SwitchBlockEntity)
                return InteractionResult.PASS
            val result = attachedBlockEntity.toggle(blockEntity.blockPos, context.level)
            return if (result) InteractionResult.CONSUME else InteractionResult.SUCCESS
        }
        return super.useOn(context)
    }

    override fun appendHoverText(
        itemStack: ItemStack,
        context: TooltipContext,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val data = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
        if (!data.isEmpty) {
            val tag = data.copyTag()
            if (tag.contains(TARGET_BLOCK_TAG)) {
                val targetPos = NbtUtils.readBlockPos(tag, TARGET_BLOCK_TAG)
                if (targetPos.isPresent)
                    list.add(ModText.SCREWDRIVER_TARGET_BLOCK.format(targetPos.get()))
            }
        }
        super.appendHoverText(itemStack, context, list, tooltipFlag)
    }
}