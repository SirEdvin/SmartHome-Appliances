package site.siredvin.smarthome.client.render

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.NbtUtils
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.phys.Vec3
import site.siredvin.broccolium.modules.base.ext.toVec3
import site.siredvin.smarthome.common.blockentity.SwitchBlockEntity
import site.siredvin.smarthome.common.item.SmartScrewdriver
import site.siredvin.smarthome.common.setup.Items
import kotlin.math.min

object SmartScrewdriverRenderingLayer {

    private val RED_COLOR = floatArrayOf(1.0F, 0.5F, 0.5F, 1.0F)
    private val BLUE_COLOR = floatArrayOf(0.5F, 0.5F, 1.0F, 1.0F)
    private val GREEN_COLOR = floatArrayOf(0.5F, 1.0F, 0.5F, 1.0F)

    private fun renderTargetBlockBox(pose: PoseStack, bufferSource: MultiBufferSource, level: ClientLevel, blockPos: BlockPos, camera: Camera, color: FloatArray = RED_COLOR, lineTo: Vec3? = null) {
        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()
        RenderSystem.lineWidth(2.0f)
        pose.pushPose()
        pose.translate(-camera.position.x, -camera.position.y, -camera.position.z)
        val blockState = level.getBlockState(blockPos)
        val shape = blockState.getShape(level, blockPos)
        val box = shape.bounds().inflate(0.05)
        LevelRenderer.renderLineBox(
            pose, bufferSource.getBuffer(RenderType.lines()),
            blockPos.x.toDouble() + box.minX, blockPos.y.toDouble() + box.minY, blockPos.z.toDouble() + box.minZ,
            blockPos.x.toDouble() + min(box.maxX, 1.0),  blockPos.y.toDouble() + min(box.maxY, 1.0), blockPos.z.toDouble() + min(box.maxZ, 1.0),
            color[0], color[1], color[2], color[3]
        )
        if (lineTo != null) {
            val buffer = bufferSource.getBuffer(RenderType.lines())
            val centerPoint = blockPos.toVec3().add(box.center)
            buffer.addVertex(pose.last().pose(), centerPoint.x.toFloat(), centerPoint.y.toFloat(), centerPoint.z.toFloat())
                .setColor(BLUE_COLOR[0], BLUE_COLOR[1], BLUE_COLOR[2], BLUE_COLOR[3]) // Color (Red) and alpha
                .setNormal(pose.last(), 0.0F, 1.0F, 0.0F) // Normal vector
            buffer.addVertex(pose.last().pose(), lineTo.x.toFloat(), lineTo.y.toFloat(), lineTo.z.toFloat())
                .setColor(BLUE_COLOR[0], BLUE_COLOR[1], BLUE_COLOR[2], BLUE_COLOR[3]) // Color (Red) and alpha
                .setNormal(pose.last(), 0.0F, 1.0F, 0.0F) // Normal vector
        }
        pose.popPose()
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    fun render(pose: PoseStack, bufferSource: MultiBufferSource, level: ClientLevel, camera: Camera) {
        val minecraft = Minecraft.getInstance()
        val selectedItem = minecraft.player?.inventory?.getSelected()
        if (selectedItem != null && selectedItem.`is`(Items.SMART_SCREWDRIVER.get())) {
            val selectedBlock = NbtUtils.readBlockPos(selectedItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag(), SmartScrewdriver.TARGET_BLOCK_TAG)
            if (selectedBlock.isPresent) {
                renderTargetBlockBox(pose, bufferSource, level, selectedBlock.get(), camera)
                val blockEntity = level.getBlockEntity(selectedBlock.get())
                if (blockEntity is SwitchBlockEntity) {
                    val center = blockEntity.blockPos.toVec3().add(blockEntity.blockState.getShape(level, blockEntity.blockPos).bounds().center)
                    blockEntity.connectedBlocks.forEach {
                        renderTargetBlockBox(pose, bufferSource, level, it, camera, color = GREEN_COLOR, lineTo = center)
                    }
                }
            }
        }
    }
}