package days

import resources.InputReader
import util.Vec3
import util.allInts
import java.lang.IllegalArgumentException
import kotlin.math.max
import kotlin.math.min

object Day14 : DayInterface {
    override val dayNumber: Int
        get() = 14

    enum class Stuff {
        Wall, Sand, Start
    }

    private val slice = mutableMapOf<Vec3, Stuff>()
    private val startLoc = Vec3(500, 0)

    private const val minY = 0
    private var maxY = -1
    private var minX = Int.MAX_VALUE
    private var maxX = Int.MIN_VALUE
    
    val input = InputReader
        .getResourceLines(14)
        .forEach(::parseLine)

    init {
        slice[startLoc] = Stuff.Start
    }

    fun printSlice() {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val loc = Vec3(x, y)
                when (slice[loc]) {
                    Stuff.Wall -> print('#')
                    Stuff.Sand -> print('o')
                    Stuff.Start -> print('+')
                    else -> print('.')
                }
            }
            print("\n")
        }
        println("")
    }

    private fun parseLine(line: String) {
        val coords = allInts(line).chunked(2).map { Vec3(it[0], it[1]) }

        maxY = max(maxY, coords.maxOf { it.y })
        minX = min(minX, coords.minOf { it.x })
        maxX = max(maxX, coords.maxOf { it.x })

        val coordPairs = coords.windowed(2)
        for (coordPair in coordPairs) {
            val from = coordPair[0]
            val to = coordPair[1]
            if (from.x != to.x) {
                for (newX in min(from.x, to.x)..max(from.x, to.x)) {
                    val loc = Vec3(newX, from.y)
                    slice[loc] = Stuff.Wall
                }
            } else if (from.y != to.y) {
                for (newY in min(from.y, to.y)..max(from.y, to.y)) {
                    val loc = Vec3(from.x, newY)
                    slice[loc] = Stuff.Wall
                }
            } else {
                throw IllegalArgumentException("diagonal wall detected")
            }
        }
    }

    private fun pourSand() {
        val down = Vec3(0, 1)
        val downLeft = Vec3(-1, 1)
        val downRight = Vec3(1, 1)

        while (true) {
            var currLoc = startLoc
            val prevSliceSize = slice.size
            while (currLoc.y <= maxY || slice[startLoc] == Stuff.Sand) {
                if (slice.contains(currLoc + down)) {
                    if (slice.contains(currLoc + downLeft)) {
                        if (slice.contains(currLoc + downRight)) {
                            slice[currLoc] = Stuff.Sand
                            break
                        } else {
                            currLoc += downRight
                        }
                    } else {
                        currLoc += downLeft
                    }
                } else {
                    currLoc += down
                }
            }

            if (prevSliceSize == slice.size) {
                // same size, so no sand has set, so we can break
                break
            }
        }
    }

    override fun part1(): Any {
        pourSand()
        return slice.values.count { it == Stuff.Sand }
    }

    override fun part2(): Any {
        val floorLevel = maxY + 2
        parseLine("0,$floorLevel -> 1000,$floorLevel")
        pourSand()
        return slice.values.count { it == Stuff.Sand }
    }
}