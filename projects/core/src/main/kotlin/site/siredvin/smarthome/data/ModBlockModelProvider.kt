package site.siredvin.smarthome.data

import net.minecraft.core.Direction
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.blockstates.PropertyDispatch
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.blockstates.VariantProperties.*
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.properties.BooleanProperty
import site.siredvin.smarthome.common.block.LampBlock
import site.siredvin.smarthome.common.block.LedPanelBlock
import site.siredvin.smarthome.common.block.SwitchBlock
import site.siredvin.smarthome.common.setup.Blocks
import kotlin.math.min

object ModBlockModelProvider {

    private fun toYAnglePedestal(direction: Direction): VariantProperties.Rotation = when (direction) {
        Direction.NORTH -> VariantProperties.Rotation.R0
        Direction.SOUTH -> VariantProperties.Rotation.R180
        Direction.EAST -> VariantProperties.Rotation.R90
        Direction.WEST -> VariantProperties.Rotation.R270
        else -> VariantProperties.Rotation.R0
    }

    private fun toLedPanelRotation(direction: Direction): VariantProperties.Rotation = when (direction) {
        Direction.SOUTH -> VariantProperties.Rotation.R0
        Direction.NORTH -> VariantProperties.Rotation.R180
        Direction.WEST -> VariantProperties.Rotation.R90
        Direction.EAST -> VariantProperties.Rotation.R270
        else -> VariantProperties.Rotation.R0
    }

    private fun toXAnglePedestal(direction: Direction): VariantProperties.Rotation = when (direction) {
        Direction.NORTH -> VariantProperties.Rotation.R90
        Direction.SOUTH -> VariantProperties.Rotation.R90
        Direction.EAST -> VariantProperties.Rotation.R90
        Direction.WEST -> VariantProperties.Rotation.R90
        Direction.DOWN -> VariantProperties.Rotation.R180
        Direction.UP -> VariantProperties.Rotation.R0
    }

    private fun createBooleanDispatching(offVariant: ResourceLocation, onVariant: ResourceLocation, property: BooleanProperty): PropertyDispatch {
        val dispatch = PropertyDispatch.property(property)
        dispatch.select(false, Variant.variant().with(VariantProperties.MODEL, offVariant))
        dispatch.select(true, Variant.variant().with(VariantProperties.MODEL, onVariant))
        return dispatch
    }

    private fun createFacingDispatch(): PropertyDispatch {
        val dispatch = PropertyDispatch.property(LampBlock.FACING)
        for (direction in LampBlock.FACING.possibleValues) {
            dispatch.select(
                direction,
                Variant.variant().with(
                    VariantProperties.Y_ROT,
                    toYAnglePedestal(direction),
                ).with(VariantProperties.X_ROT, toXAnglePedestal(direction)),
            )
        }
        return dispatch
    }


    fun lampBlock(generators: BlockModelGenerators) {
        val block = Blocks.LAMP.get()
        val onModel =  ModelLocationUtils.getModelLocation(block, "_on")
        val offModel =  ModelLocationUtils.getModelLocation(block, "_off")
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(VariantProperties.MODEL, onModel),
            ).with(createFacingDispatch()).with(createBooleanDispatching(offModel, onModel, LampBlock.ENALBED)),
        )
        generators.delegateItemModel(block, ModelLocationUtils.getModelLocation(block))
    }

    fun switchBlock(generators: BlockModelGenerators) {
        val block = Blocks.SWITCH.get()
        val onModel =  ModelLocationUtils.getModelLocation(block, "_on")
        val offModel =  ModelLocationUtils.getModelLocation(block, "_off")
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(VariantProperties.MODEL, onModel),
            ).with(createFacingDispatch()).with(createBooleanDispatching(offModel, onModel, SwitchBlock.ENALBED)),
        )
        generators.delegateItemModel(block, ModelLocationUtils.getModelLocation(block))
    }

    fun panelBlock(generators: BlockModelGenerators) {
        val block = Blocks.LED_PANEL.get()
        val onModel =  ModelLocationUtils.getModelLocation(block, "_on")
        val offModel =  ModelLocationUtils.getModelLocation(block, "_off")
        val onModelTwoSide =  ModelLocationUtils.getModelLocation(block, "_two_side_on")
        val offModelTwoSide =  ModelLocationUtils.getModelLocation(block, "_two_side_off")
        val onModelTwoSideSide =  ModelLocationUtils.getModelLocation(block, "_two_side_side_on")
        val offModelTwoSideSide =  ModelLocationUtils.getModelLocation(block, "_two_side_side_off")
        val brokenModel =  ModelLocationUtils.getModelLocation(block, "_broken")

        val dispatch = PropertyDispatch.properties(LedPanelBlock.AMOUNT, LedPanelBlock.FACING, LedPanelBlock.SECOND_FACING, LedPanelBlock.ENALBED)
        // Single panel
        Direction.entries.forEach { first ->
            val offVariant = Variant.variant().with(VariantProperties.Y_ROT, toYAnglePedestal(first)).with(VariantProperties.X_ROT, toXAnglePedestal(first)).with(VariantProperties.MODEL, offModel)
            val onVariant = Variant.variant().with(VariantProperties.Y_ROT, toYAnglePedestal(first)).with(VariantProperties.X_ROT, toXAnglePedestal(first)).with(VariantProperties.MODEL, onModel)
            Direction.entries.forEach { second ->
                dispatch.select(1, first, second, false, offVariant)
                dispatch.select(1, first, second, true, onVariant)
            }
        }
        // Two panels
        Direction.entries.forEach { first ->
            Direction.entries.forEach { second ->
                if (first == second || first.opposite == second) { // This in general should not appear, so we consider it broker
                    dispatch.select(2, first, second, false, Variant.variant().with(VariantProperties.MODEL, brokenModel))
                    dispatch.select(2, first, second, true, Variant.variant().with(VariantProperties.MODEL, brokenModel))
                } else if (first == Direction.UP || second == Direction.UP) {
                    val important = if (first == Direction.UP) second else first
                    dispatch.select(2, first, second, false, Variant.variant().with(VariantProperties.MODEL, offModelTwoSide).with(VariantProperties.Y_ROT, toYAnglePedestal(important.opposite)))
                    dispatch.select(2, first, second, true, Variant.variant().with(VariantProperties.MODEL, onModelTwoSide).with(VariantProperties.Y_ROT, toYAnglePedestal(important.opposite)))
                } else if (first == Direction.DOWN || second == Direction.DOWN) {
                    val important = if (first == Direction.DOWN) second else first
                    dispatch.select(2, first, second, false, Variant.variant().with(VariantProperties.MODEL, offModelTwoSide).with(VariantProperties.Y_ROT, toYAnglePedestal(important)).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                    dispatch.select(2, first, second, true, Variant.variant().with(VariantProperties.MODEL, onModelTwoSide).with(VariantProperties.Y_ROT, toYAnglePedestal(important)).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                } else {
                    val firstRotation = toLedPanelRotation(first)
                    val secondRotation = toLedPanelRotation(second)
                    val targetRotation = if (firstRotation.ordinal > secondRotation.ordinal) secondRotation else firstRotation
                    dispatch.select(2, first, second, false, Variant.variant().with(VariantProperties.MODEL, offModelTwoSideSide).with(
                        X_ROT, targetRotation))
                    dispatch.select(2, first, second, true, Variant.variant().with(VariantProperties.MODEL, onModelTwoSideSide).with(
                        X_ROT, targetRotation))
                }
            }
        }
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(VariantProperties.MODEL, onModel),
            ).with(dispatch),
        )
        generators.delegateItemModel(block, ModelLocationUtils.getModelLocation(block))
    }

    fun addModels(@Suppress("UNUSED_PARAMETER") generators: BlockModelGenerators) {
        lampBlock(generators)
        switchBlock(generators)
        panelBlock(generators)
    }
}
