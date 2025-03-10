package site.siredvin.smarthome.data

import net.minecraft.core.Direction
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.blockstates.PropertyDispatch
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties.*
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BooleanProperty
import site.siredvin.smarthome.ModCore
import site.siredvin.smarthome.common.block.*
import site.siredvin.smarthome.common.setup.ModBlocks
import java.util.*

object ModBlockModelProvider {

    private val ZERO = TextureSlot.create("0")
    private val ONE = TextureSlot.create("1")

    private val LED_PANEL_MODEL_TEMPLATE = ModelTemplate(
        Optional.of(ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "block/led_panel")),
        Optional.empty(),
        ZERO, ONE,
        TextureSlot.PARTICLE
    )

    private val LED_PANEL_TWO_SIDE_MODEL_TEMPLATE = ModelTemplate(
        Optional.of(ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "block/led_panel_two_side")),
        Optional.empty(),
        ZERO, ONE,
        TextureSlot.PARTICLE
    )

    private val LED_PANEL_TWO_SIDE_SIDE_MODEL_TEMPLATE = ModelTemplate(
        Optional.of(ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "block/led_panel_two_side_side")),
        Optional.empty(),
        ZERO, ONE,
        TextureSlot.PARTICLE
    )

    private val LED_PANEL_THREE_SIDE_MODEL_TEMPLATE = ModelTemplate(
        Optional.of(ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "block/led_panel_three_side")),
        Optional.empty(),
        ZERO, ONE,
        TextureSlot.PARTICLE
    )

    private fun toYAnglePedestal(direction: Direction): Rotation = when (direction) {
        Direction.NORTH -> Rotation.R0
        Direction.SOUTH -> Rotation.R180
        Direction.EAST -> Rotation.R90
        Direction.WEST -> Rotation.R270
        else -> Rotation.R0
    }

    private fun toTwoFaceLedPanelRotation(first: Direction, second: Direction): Pair<Rotation, Rotation> = when (first) {
        Direction.SOUTH -> when (second) {
            Direction.WEST -> Pair(Rotation.R0, Rotation.R90)
            else -> Pair(Rotation.R0, Rotation.R0)
        }
        Direction.NORTH -> when (second) {
            Direction.WEST ->  Pair(Rotation.R180, Rotation.R270)
            else -> Pair(Rotation.R180, Rotation.R0)
        }
        else -> Pair(Rotation.R0, Rotation.R0)
    }

    private fun toThreeFaceRotation(form: LedPanelForm): Pair<Rotation, Rotation> = when(form) {
        LedPanelForm.UP_SOUTH_EAST -> Pair(Rotation.R0, Rotation.R0)
        LedPanelForm.UP_SOUTH_WEST -> Pair(Rotation.R0, Rotation.R90)
        LedPanelForm.UP_NORTH_EAST -> Pair(Rotation.R0, Rotation.R270)
        LedPanelForm.UP_NORTH_WEST -> Pair(Rotation.R0, Rotation.R180)
        LedPanelForm.DOWN_SOUTH_EAST -> Pair(Rotation.R180, Rotation.R90)
        LedPanelForm.DOWN_SOUTH_WEST -> Pair(Rotation.R180, Rotation.R180)
        LedPanelForm.DOWN_NORTH_EAST -> Pair(Rotation.R180, Rotation.R0)
        LedPanelForm.DOWN_NORTH_WEST -> Pair(Rotation.R180, Rotation.R270)
        else -> Pair(Rotation.R0, Rotation.R0)
    }

    private fun toXAnglePedestal(direction: Direction): Rotation = when (direction) {
        Direction.NORTH -> Rotation.R90
        Direction.SOUTH -> Rotation.R90
        Direction.EAST -> Rotation.R90
        Direction.WEST -> Rotation.R90
        Direction.DOWN -> Rotation.R180
        Direction.UP -> Rotation.R0
    }

    private fun createBooleanDispatching(offVariant: ResourceLocation, onVariant: ResourceLocation, property: BooleanProperty): PropertyDispatch {
        val dispatch = PropertyDispatch.property(property)
        dispatch.select(false, Variant.variant().with(MODEL, offVariant))
        dispatch.select(true, Variant.variant().with(MODEL, onVariant))
        return dispatch
    }

    private fun createFacingDispatch(): PropertyDispatch {
        val dispatch = PropertyDispatch.property(LampBlock.FACING)
        for (direction in LampBlock.FACING.possibleValues) {
            dispatch.select(
                direction,
                Variant.variant().with(
                    Y_ROT,
                    toYAnglePedestal(direction),
                ).with(X_ROT, toXAnglePedestal(direction)),
            )
        }
        return dispatch
    }


    fun lampBlock(generators: BlockModelGenerators) {
        val block = ModBlocks.LAMP.get()
        val onModel =  ModelLocationUtils.getModelLocation(block, "_on")
        val offModel =  ModelLocationUtils.getModelLocation(block, "_off")
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(MODEL, onModel),
            ).with(createFacingDispatch()).with(createBooleanDispatching(offModel, onModel, ColoredLightBlock.ENALBED)),
        )
        generators.delegateItemModel(block, ModelLocationUtils.getModelLocation(block))
    }

    private fun switchBlock(generators: BlockModelGenerators) {
        val block = ModBlocks.SWITCH.get()
        val onModel =  ModelLocationUtils.getModelLocation(block, "_on")
        val offModel =  ModelLocationUtils.getModelLocation(block, "_off")
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(MODEL, onModel),
            ).with(createFacingDispatch()).with(createBooleanDispatching(offModel, onModel, SwitchBlock.ENALBED)),
        )
        generators.delegateItemModel(block, ModelLocationUtils.getModelLocation(block))
    }

    private fun panelBlock(generators: BlockModelGenerators, block: Block, fillingTexture: ResourceLocation = ResourceLocation.fromNamespaceAndPath(ModCore.MOD_ID, "block/smooth_stone")) {
        val onMapping = TextureMapping()
        val offMapping = TextureMapping()
        onMapping.put(ONE, TextureMapping.getBlockTexture(block, "_on"))
        offMapping.put(ONE, TextureMapping.getBlockTexture(block, "_off"))
        onMapping.put(ZERO, fillingTexture)
        onMapping.put(TextureSlot.PARTICLE, fillingTexture)
        offMapping.put(ZERO, fillingTexture)
        offMapping.put(TextureSlot.PARTICLE, fillingTexture)
        val onModel =  LED_PANEL_MODEL_TEMPLATE.createWithSuffix(
            block, "_on", onMapping, generators.modelOutput
        )
        val offModel =  LED_PANEL_MODEL_TEMPLATE.createWithSuffix(
            block, "_off", offMapping, generators.modelOutput
        )
        val onModelTwoSide =  LED_PANEL_TWO_SIDE_MODEL_TEMPLATE.createWithSuffix(
            block, "_two_side_on", onMapping, generators.modelOutput
        )
        val offModelTwoSide =  LED_PANEL_TWO_SIDE_MODEL_TEMPLATE.createWithSuffix(
            block, "_two_side_off", offMapping, generators.modelOutput
        )
        val onModelTwoSideSide =  LED_PANEL_TWO_SIDE_SIDE_MODEL_TEMPLATE.createWithSuffix(
            block, "_two_side_side_on", onMapping, generators.modelOutput
        )
        val offModelTwoSideSide =  LED_PANEL_TWO_SIDE_SIDE_MODEL_TEMPLATE.createWithSuffix(
            block, "_two_side_side_off", offMapping, generators.modelOutput
        )
        val onModelThreeSide =  LED_PANEL_THREE_SIDE_MODEL_TEMPLATE.createWithSuffix(
            block, "_three_side_on", onMapping, generators.modelOutput
        )
        val offModelThreeSide =  LED_PANEL_THREE_SIDE_MODEL_TEMPLATE.createWithSuffix(
            block, "_three_side_off", offMapping, generators.modelOutput
        )

        val dispatch = PropertyDispatch.properties(LedPanelBlock.FORM, ColoredLightBlock.ENALBED)
        LedPanelForm.entries.forEach {
            if (it.directions.size == 1) {
                // Single panel
                val direction = it.directions[0]
                dispatch.select(it, false, Variant.variant().with(Y_ROT, toYAnglePedestal(direction)).with(X_ROT, toXAnglePedestal(direction)).with(
                    MODEL, offModel))
                dispatch.select(it, true, Variant.variant().with(Y_ROT, toYAnglePedestal(direction)).with(X_ROT, toXAnglePedestal(direction)).with(
                    MODEL, onModel))
            } else if (it.directions.size == 2) {
                if (it.directions[0] == Direction.UP) {
                    dispatch.select(it, false, Variant.variant().with(MODEL, offModelTwoSide).with(Y_ROT, toYAnglePedestal(it.directions[1].opposite)))
                    dispatch.select(it, true, Variant.variant().with(MODEL, onModelTwoSide).with(Y_ROT, toYAnglePedestal(it.directions[1].opposite)))
                } else if (it.directions[0] == Direction.DOWN) {
                    dispatch.select(it, false, Variant.variant().with(MODEL, offModelTwoSide).with(Y_ROT, toYAnglePedestal(it.directions[1])).with(
                        X_ROT, Rotation.R180))
                    dispatch.select(it, true, Variant.variant().with(MODEL, onModelTwoSide).with(Y_ROT, toYAnglePedestal(it.directions[1])).with(
                        X_ROT, Rotation.R180))
                } else {
                    val rotation = toTwoFaceLedPanelRotation(it.directions[0], it.directions[1])
                    dispatch.select(it, false, Variant.variant().with(MODEL, offModelTwoSideSide).with(
                        X_ROT, rotation.first).with(Y_ROT, rotation.second))
                    dispatch.select(it, true, Variant.variant().with(MODEL, onModelTwoSideSide).with(
                        X_ROT, rotation.first).with(Y_ROT, rotation.second))
                }
            } else if (it.directions.size == 3) {
                val rotation = toThreeFaceRotation(it)
                dispatch.select(it, false, Variant.variant().with(MODEL, offModelThreeSide).with(
                    X_ROT, rotation.first).with(Y_ROT, rotation.second))
                dispatch.select(it, true, Variant.variant().with(MODEL, onModelThreeSide).with(
                    X_ROT, rotation.first).with(Y_ROT, rotation.second))
            }
        }
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(MODEL, onModel),
            ).with(dispatch),
        )
        generators.delegateItemModel(block, ModelLocationUtils.getModelLocation(block, "_on"))
    }

    fun addModels(generators: BlockModelGenerators) {
        lampBlock(generators)
        switchBlock(generators)
        panelBlock(generators, ModBlocks.LED_PANEL.get())
        panelBlock(generators, ModBlocks.SMOOTH_LED_PANEL.get(), fillingTexture = ResourceLocation.withDefaultNamespace("block/iron_block"))
    }
}
