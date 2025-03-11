package site.siredvin.smarthome.common.block

import net.minecraft.core.Direction
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

object LedPanelHelper {
    val SHAPE = Shapes.box(
        0.0,
        0.0,
        0.0,
        1.0,
        0.0625,
        1.0,
    )
    val DOWN_SHAPE = Shapes.box(
        0.0,
        0.9375,
        0.0,
        1.0,
        1.0,
        1.0,
    )
    val NORTH_SHAPE = Shapes.box(
        0.0,
        0.0,
        0.9375,
        1.0,
        1.0,
        1.0,
    )
    val SOUTH_SHAPE = Shapes.box(
        0.0,
        0.0,
        0.0,
        1.0,
        1.0,
        0.0625,
    )
    val WEST_SHAPE = Shapes.box(
        0.9375,
        0.0,
        0.0,
        1.0,
        1.0,
        1.0,
    )
    val EAST_SHAPE = Shapes.box(
        0.0,
        0.0,
        0.0,
        0.0625,
        1.0,
        1.0,
    )

    fun getShapeByDirection(direction: Direction): VoxelShape = when (direction) {
        Direction.SOUTH -> SOUTH_SHAPE
        Direction.NORTH -> NORTH_SHAPE
        Direction.EAST -> EAST_SHAPE
        Direction.WEST -> WEST_SHAPE
        Direction.DOWN -> DOWN_SHAPE
        else -> SHAPE
    }

    fun combinedShapes(vararg directions: Direction): VoxelShape = directions.map(::getShapeByDirection).reduce { acc, shape -> Shapes.join(acc, shape, BooleanOp.OR) }
}
