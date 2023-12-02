package days

import days.day23.Move
import resources.InputReader
import util.Point
import java.lang.IllegalStateException

object Day23 : DayInterface {
    override val dayNumber: Int
        get() = 23

    val spotsToCheck = listOf(
        listOf(Point(-1, 1), Point(0, 1), Point(1, 1)), // check NW, N, NE
        listOf(Point(-1, -1), Point(0, -1), Point(1, -1)), // check SW,S,SE
        listOf(Point(-1, -1), Point(-1, 0), Point(-1, 1)), // check SW,W,NW
        listOf(Point(1, -1), Point(1, 0), Point(1, 1)), // check NE,E,SE
    )
    
    data class ProposedMove(val propose: Elf, val origin: Elf)
    data class Elf(val location: Point, val directionIdx: Int) {
        fun move(idx: Int): Elf {
            val point = Move.byIndex(idx).movement
            return Elf(location + point, (directionIdx + idx + 1) % spotsToCheck.size )
        }
    }

    fun countEmptyAndPrintMap(lst: List<Elf>): Int {
        val locs = lst.map { it.location }
        val mins = locs.reduce(Point::minVals)
        val maxs = locs.reduce(Point::maxVals)
        var empty = 0
        println("=========")
        for (y in maxs.y downTo mins.y - 1) {
            for (x in mins.x..maxs.x) {
                if (locs.contains(Point(x, y))) {
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


    fun iterElves(elves: List<Elf>, rounds: Int): Int {
        val curMap = elves.toMutableList()
        for (round in 0 until rounds) {
            val locations = curMap.map { it.location }
            countEmptyAndPrintMap(curMap)
            val proposed = curMap.map { elf ->
                if (elf.location.eightNeighbors().none { locations.contains(it) }) {
                    // No neighbors, so no movement
                    println("no neighbors: " + ProposedMove(elf, elf))
                    return@map ProposedMove(elf, elf)
                } else {
                    // No neighbor
                    for (idx in 0 until spotsToCheck.size) {
                        val directionToCheck = (idx + elf.directionIdx) % spotsToCheck.size
                        if (spotsToCheck[directionToCheck].map { it + elf.location }
                                .none { locations.contains(it) }) {
                            // Empty spots in direction idx
                            println(
                                "No neighbors in direction ${Move.byIndex(directionToCheck)}: $idx" + ProposedMove(
                                    elf.move(directionToCheck), elf
                                )
                            )
                            return@map ProposedMove(elf.move(directionToCheck), elf)
                        }
                    }
                    // should never happen
                    throw IllegalStateException("Not free laying, but no free direction")
                }
            }
            val grouped = proposed.groupBy { it.propose }
            curMap.clear()
            val t = grouped.map { pp ->
                if (pp.value.size > 1) {
                    pp.value.map { it.origin }
                } else {
                    listOf(pp.value[0].propose)
                }
            }.flatten()
            curMap.addAll(t)
        }
        return countEmptyAndPrintMap(curMap)
    }

    override fun part1(): Any {
        val input = InputReader.getResourceLines("./input/day23example2.txt")
        val parsed = input.reversed()
            .withIndex()
            .map { (lineIdx, line) ->
                line.withIndex()
                    .filter { it.value == '#' }
                    .map { Elf(Point(it.index, lineIdx), 0) }
            }.flatten()
        return iterElves(parsed, 2)
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}