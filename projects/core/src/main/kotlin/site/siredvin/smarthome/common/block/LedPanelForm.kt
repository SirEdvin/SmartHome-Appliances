package site.siredvin.smarthome.common.block

import net.minecraft.core.Direction
import net.minecraft.util.StringRepresentable
import net.minecraft.world.phys.shapes.VoxelShape

enum class LedPanelForm(vararg directions: Direction) : StringRepresentable {
    UP(Direction.UP),
    DOWN(Direction.DOWN),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    EAST(Direction.EAST),
    WEST(Direction.WEST),
    UP_NORTH(Direction.UP, Direction.NORTH),
    UP_SOUTH(Direction.UP, Direction.SOUTH),
    UP_EAST(Direction.UP, Direction.EAST),
    UP_WEST(Direction.UP, Direction.WEST),
    DOWN_NORTH(Direction.DOWN, Direction.NORTH),
    DOWN_SOUTH(Direction.DOWN, Direction.SOUTH),
    DOWN_EAST(Direction.DOWN, Direction.EAST),
    DOWN_WEST(Direction.DOWN, Direction.WEST),
    NORTH_EAST(Direction.NORTH, Direction.EAST),
    NORTH_WEST(Direction.NORTH, Direction.WEST),
    SOUTH_EAST(Direction.SOUTH, Direction.EAST),
    SOUTH_WEST(Direction.SOUTH, Direction.WEST),
    UP_NORTH_EAST(Direction.UP, Direction.NORTH, Direction.EAST),
    UP_NORTH_WEST(Direction.UP, Direction.NORTH, Direction.WEST),
    UP_SOUTH_EAST(Direction.UP, Direction.SOUTH, Direction.EAST),
    UP_SOUTH_WEST(Direction.UP, Direction.SOUTH, Direction.WEST),
    DOWN_NORTH_EAST(Direction.DOWN, Direction.NORTH, Direction.EAST),
    DOWN_NORTH_WEST(Direction.DOWN, Direction.NORTH, Direction.WEST),
    DOWN_SOUTH_EAST(Direction.DOWN, Direction.SOUTH, Direction.EAST),
    DOWN_SOUTH_WEST(Direction.DOWN, Direction.SOUTH, Direction.WEST), ;

    val shape: VoxelShape = LedPanelHelper.combinedShapes(*directions)
    val directions: List<Direction> = directions.sorted()

    companion object {
        fun searchForm(vararg directions: Direction): LedPanelForm? {
            val sortedDirections = directions.sorted()
            return entries.find { it.directions == sortedDirections }
        }
    }

    override fun getSerializedName(): String = name.lowercase()
}
