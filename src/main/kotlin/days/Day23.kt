package days

import resources.InputReader
import util.Point

object Day23 : DayInterface {
    override val dayNumber: Int
        get() = 23

    val parsed = InputReader.getResourceLines(23)
        .reversed()
        .withIndex()
        .map { (lineIdx, line) ->
            line.withIndex()
                .filter { it.value == '#' }
                .map { Point(it.index, lineIdx) }
        }.flatten()

    enum class Direction(val point: Point) {
        NORTH(Point(0, 1)),
        SOUTH(Point(0, -1)),
        EAST(Point(1, 0)),
        WEST(Point(-1, 0));

        operator fun plus(other: Direction): Point {
            return this.point + other.point
        }
    }

    data class DirectionSpots(val direction: Direction, val spots: List<Point>)

    val spotsToCheck: List<DirectionSpots> = listOf(
        DirectionSpots(
            Direction.NORTH,
            listOf(Direction.NORTH + Direction.WEST, Direction.NORTH.point, Direction.NORTH + Direction.EAST)
        ), // NW, N, NE
        DirectionSpots(
            Direction.SOUTH,
            listOf(Direction.SOUTH + Direction.WEST, Direction.SOUTH.point, Direction.SOUTH + Direction.EAST)
        ), // SW,S,SE
        DirectionSpots(
            Direction.WEST,
            listOf(Direction.NORTH + Direction.WEST, Direction.WEST.point, Direction.SOUTH + Direction.WEST)
        ), // NW, W, SW
        DirectionSpots(
            Direction.EAST,
            listOf(Direction.NORTH + Direction.EAST, Direction.EAST.point, Direction.SOUTH + Direction.EAST)
        ), // NE, E, SE
    )

    fun countEmptyAndPrintMap(lst: List<Point>): Int {
        val mins = lst.reduce(Point::minVals)
        val maxs = lst.reduce(Point::maxVals)
        var empty = 0
        println("=========")
        for (y in maxs.y downTo mins.y) {
            for (x in mins.x..maxs.x) {
                if (lst.contains(Point(x, y))) {
                    print('#')
                } else {
                    print('.')
                    empty++
                }
            }
            print('\n')
        }
        println("=========")
        return empty
    }

    fun addElfToLocation(proposals: MutableMap<Point, MutableSet<Point>>, target: Point, origin: Point) {
        if (!proposals.contains(target)) {
            proposals.put(target, mutableSetOf())
        }
        proposals[target]!!.add(origin)
    }

    fun doRounds(numRounds: Int, elfs: List<Point>, willRunUntillStable: Boolean = false): Pair<List<Point>, Int> {
        var currentPositions = elfs
        var round = 0
        while (round < numRounds) {
            if(round %10 == 0){
                println("Starting round $round")
            }

            var haveElvesMoved = false
            val proposals: MutableMap<Point, MutableSet<Point>> = mutableMapOf()

            // Planning phase
            for (elf in currentPositions) {
                // Are there any neighbors?
                if (elf.eightNeighbors().none { currentPositions.contains(it) }) {
                    // This elf has no neighbors, so will do nothing
                    addElfToLocation(proposals, elf, elf)
                    continue
                }

                // Let's check in different directions
                var hasProposedMove = false
                for (idx in 0..spotsToCheck.size) {
                    val directionSpots = spotsToCheck[(idx + round) % spotsToCheck.size]
                    val locs = directionSpots.spots.map { it + elf }
                    if (locs.none { currentPositions.contains(it) }) {
                        addElfToLocation(proposals, elf + directionSpots.direction.point, elf)
                        haveElvesMoved = true
                        hasProposedMove = true
                        break
                    }
                }
                // If no move is valid, the elf stays in the same place
                if (!hasProposedMove) {
                    addElfToLocation(proposals, elf, elf)
                }
            }

            // Execute movement
            val result: MutableList<Point> = mutableListOf()
            proposals.entries.map { (target, origins) ->
                // Size 0 shouldnt happen, as sets are only added when there's atleast one location
                if (origins.size == 1) {
                    result.add(target)
                } else {
                    // multiple elves want to move here, so they stay on original location
                    result.addAll(origins)
                }
            }

            // Movement is done, so we prepare for next round
            currentPositions = result
            round += 1
            if(!haveElvesMoved && willRunUntillStable){
                return Pair(currentPositions, round)
            }
        }
        return Pair(currentPositions, round)
    }

    override fun part1(): Any {
        val res = doRounds(10, parsed, false)

        return countEmptyAndPrintMap(res.first)
    }

    override fun part2(): Any {
        val res = doRounds(Int.MAX_VALUE, parsed, true)

        println("Took ${res.second} rounds")
        return res.second
    }
}