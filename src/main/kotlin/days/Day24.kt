package days

import resources.InputReader
import util.Point
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

object Day24 : DayInterface {
    override val dayNumber: Int
        get() = 24


    fun isWithin(num: Int, size: Int): Boolean {
        return num in 1 until size - 1
    }

    fun isWithinField(loc: Point, width: Int, height: Int): Boolean {
        return isWithin(loc.x, width) && isWithin(loc.y, height)
    }

    enum class Direction(val p: Point) {
        Down(Point(0, 1)),
        Right(Point(1, 0)),
        Up(Point(0, -1)),
        Left(Point(-1, 0)),
    }

    data class Blizzard(val location: Point, val direction: Direction) {
        fun move(width: Int, height: Int): Blizzard {
            var posLoc = location + direction.p
            if (!isWithinField(posLoc, width, height)) {
                if (posLoc.x >= width) {
                    posLoc = Point(1, posLoc.y)
                }
                if (posLoc.x <= 0) {
                    posLoc = Point(width, posLoc.y)
                }
                if (posLoc.y >= height) {
                    posLoc = Point(posLoc.x, 1)
                }
                if (posLoc.y <= 0) {
                    posLoc = Point(posLoc.x, height)
                }
            }

            return Blizzard(posLoc, direction)
        }

        companion object {
            val chars = setOf('^', 'v', '<', '>')
            fun fromChar(x: Int, y: Int, char: Char): Blizzard {
                return Blizzard(
                    Point(x, y), when (char) {
                        '^' -> Direction.Up
                        '>' -> Direction.Right
                        'v' -> Direction.Down
                        '<' -> Direction.Left
                        else -> throw IllegalArgumentException("Unknown direction: $char")
                    }
                )
            }
        }
    }

    data class BlizzardState(val player: Point, val blizzards: Set<Blizzard>, val path: List<Point>)

    fun walkThroughBlizzards(startLoc: Point, endLoc: Point, blizzards: Set<Blizzard>, width: Int, height: Int): Int {
        val queue = ArrayDeque<BlizzardState>()
        queue.add(BlizzardState(startLoc, blizzards, listOf(startLoc)))
        var minDistance = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            val distance = state.path.size + 1
            if(minDistance < distance){
                // better path has been found
                continue
            }
            println("Processing state: $state")
            val newBlizzs = walkBlizzards(state.blizzards, width, height)
            val newBlizzLocs = newBlizzs.map { it.location }.toSet()
            val player = state.player

            val neighbors = Direction.values().map { it.p + player }
            if (neighbors.contains(endLoc)) {
                
                if(minDistance > distance){
                    minDistance = distance
                }
            }

            // stand still
            if (!newBlizzLocs.contains(player)) {
                queue.addFirst(BlizzardState(player, newBlizzs, state.path + player))
            }
            
            // Walk in 4 directions
            neighbors
                .filter { isWithinField(it, width, height) }
                .reversed() // heuristic moves are first but we want to process them first, so we reverse
                .forEach {
                    if (!newBlizzLocs.contains(it)) {
                        queue.addFirst(BlizzardState(it, newBlizzs, state.path + it))
                    }
                }
        }
        return minDistance
    }

    fun walkBlizzards(blizzards: Set<Blizzard>, width: Int, height: Int): Set<Blizzard> {
        return blizzards.map { it.move(width, height) }.toSet()
    }


    override fun part1(): Any {
        val input = InputReader.getResourceLines("./input/day24example2.txt")

        val blizzards = input
            .withIndex()
            .map { (y, line) ->
                line.withIndex()
                    .filter { Blizzard.chars.contains(it.value) }
                    .map { (x, char) -> Blizzard.fromChar(x, y, char) }

            }.flatten()
            .toSet()
        val height = input.size
        val width = input[0].length
        val begin = Point(1, 0)
        val endLoc = Point(width - 2, height - 1)
        return walkThroughBlizzards(begin, endLoc, blizzards, width, height)
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}