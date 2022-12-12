package days

import resources.InputReader
import util.Point
import java.lang.IllegalArgumentException

object Day12 : DayInterface {
    override val dayNumber: Int
        get() = 12

    private var end: Point = Point(-1, -1)

    private var height: Int = -1
    private var width: Int = -1

    private lateinit var grid: List<List<Int>>

    private fun determineValue(input: Char): Int {
        if (input.isLowerCase()) {
            return input - 'a'
        } else if (input == 'S') {
            return 'a' - 'a'
        } else if (input == 'E') {
            return 'z' - 'a'
        }
        throw IllegalArgumentException("Unknown char")
    }

    private fun adjacentAvailablePositions(currPos: Point, visited: Set<Point>): List<Point> {
        return listOf(
            Point(currPos.x - 1, currPos.y),
            Point(currPos.x + 1, currPos.y),
            Point(currPos.x, currPos.y - 1),
            Point(currPos.x, currPos.y + 1),
        ).filter {
            it.x in 0 until width
                    && it.y in 0 until height
                    && !visited.contains(it)
                    && grid[it.y][it.x] - grid[currPos.y][currPos.x] in -50..1
        }
    }

    data class Path(val currPos: Point, val pathLength: Int)

    private fun stackWalk(start: Point, end: Point): Int {
        val stack = ArrayDeque<Path>()
        stack.add(Path(start, 0))
        val visited = mutableSetOf<Point>()

        while (stack.isNotEmpty()) {
            val stackPath = stack.removeFirst()
            if (stackPath.currPos == end) {
                return stackPath.pathLength
            } else {
                val possibleLocs = adjacentAvailablePositions(stackPath.currPos, visited)
                for (loc in possibleLocs) {
                    visited.add(loc)
                    stack.addLast(Path(loc, stackPath.pathLength + 1))
                }
            }
        }
        return Int.MAX_VALUE
    }

    override fun part1(): Any {
        val input = InputReader
            .getResourceString(12)
            .split("\n").map { it.toCharArray().toList() }

        height = input.size
        width = input[0].size

        var start = Point(-1, -1)

        for (line in input.withIndex()) {
            val sIdx = line.value.indexOf('S')
            if (sIdx >= 0) {
                start = Point(sIdx, line.index)
            }

            val eIdx = line.value.indexOf('E')
            if (eIdx >= 0) {
                end = Point(eIdx, line.index)
            }
        }
        grid = input.map { line -> line.map(::determineValue) }
        return stackWalk(start, end)
    }

    override fun part2(): Any {
        val input = InputReader
            .getResourceString(12)
            .split("\n").map { it.toCharArray().toList() }

        height = input.size
        width = input[0].size

        grid = input.map { line -> line.map(::determineValue) }

        val aList = mutableListOf<Point>()
        for (line in input.withIndex()) {
            for(char in line.value.withIndex()){
                if(char.value == 'S' || char.value == 'a'){
                    aList.add(Point(char.index, line.index))
                }
                if(char.value == 'E'){
                    end = Point(char.index, line.index)
                }
            }
        }

        return aList.map {
            stackWalk(it, end)
        }.min()
    }
}
