package site.siredvin.smarthome.client.renderer

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.debug.DebugRenderer
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.NbtUtils
import net.minecraft.world.item.component.CustomData
import site.siredvin.smarthome.common.blockentity.SwitchBlockEntity
import site.siredvin.smarthome.common.item.SmartScrewdriver
import site.siredvin.smarthome.common.setup.Items
import kotlin.math.max
import kotlin.math.min

class SwitchBlockEntityRenderer: BlockEntityRenderer<SwitchBlockEntity> {

    fun renderBox(minecraft: Minecraft, p0: SwitchBlockEntity, p1: Float, p2: PoseStack, p3: MultiBufferSource, p4: Int, p5: Int) {
        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()
        RenderSystem.lineWidth(2.0f)

        val shape = p0.blockState.getShape(p0.level!!, p0.blockPos)
        val box = shape.bounds().inflate(0.05)
        LevelRenderer.renderLineBox(
            p2, p3.getBuffer(RenderType.lines()),
            box.minX, box.minY, box.minZ,
            min(box.maxX, 1.0),  min(box.maxY, 1.0),  min(box.maxZ, 1.0),
            1.0F, 0.5F, 0.5F, 1.0F // Color (Red) and alpha
        )

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    override fun render(p0: SwitchBlockEntity, p1: Float, p2: PoseStack, p3: MultiBufferSource, p4: Int, p5: Int) {
        val minecraft = Minecraft.getInstance()
        val selectedItem = minecraft.player?.inventory?.getSelected()
        if (selectedItem != null && selectedItem.`is`(Items.SMART_SCREWDRIVER.get())) {
            val selectedBlock = NbtUtils.readBlockPos(selectedItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag(), SmartScrewdriver.TARGET_BLOCK_TAG)
            if (selectedBlock.isPresent && selectedBlock.get() == p0.blockPos) {
                renderBox(minecraft, p0, p1, p2, p3, p4, p5)
            }
        }
    }
}