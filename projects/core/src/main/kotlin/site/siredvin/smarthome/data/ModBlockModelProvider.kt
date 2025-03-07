package site.siredvin.smarthome.data

import net.minecraft.core.Direction
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.blockstates.PropertyDispatch
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BooleanProperty
import site.siredvin.smarthome.common.block.LampBlock
import site.siredvin.smarthome.common.setup.Blocks

object ModBlockModelProvider {

    private fun toYAnglePedestal(direction: Direction): VariantProperties.Rotation = when (direction) {
        Direction.NORTH -> VariantProperties.Rotation.R0
        Direction.SOUTH -> VariantProperties.Rotation.R180
        Direction.EAST -> VariantProperties.Rotation.R90
        Direction.WEST -> VariantProperties.Rotation.R270
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


    fun lampBlock(generators: BlockModelGenerators, block: Block, onModel: ResourceLocation, offModel: ResourceLocation) {
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(VariantProperties.MODEL, onModel),
            ).with(createFacingDispatch()).with(createBooleanDispatching(offModel, onModel, LampBlock.ENALBED)),
        )
        generators.delegateItemModel(block, ModelLocationUtils.getModelLocation(block))
    }

    fun addModels(@Suppress("UNUSED_PARAMETER") generators: BlockModelGenerators) {
        val lamp_on_model =  ModelLocationUtils.getModelLocation(Blocks.LAMP.get(), "_on")
        val lamp_off_model =  ModelLocationUtils.getModelLocation(Blocks.LAMP.get(), "_off")
        lampBlock(generators, Blocks.LAMP.get(), lamp_on_model, lamp_off_model)
    }
}
