package days

import resources.InputReader
import util.Vec3
import util.allInts

object Day18 : DayInterface {
    override val dayNumber: Int
        get() = 18

    override fun part1(): Any {
        val input = InputReader.getResourceLines(dayNumber)
        val parsed = input.map { Vec3.fromList(allInts(it)) }.toSet()

        return parsed.map { droplet ->
            6 - droplet.attachedNeighbors().count { neighbor -> parsed.contains(neighbor) }
        }.sum()
    }
    
    private fun countOutside(points: Set<Vec3>, mins: Vec3, maxs: Vec3): Int {
        val queue = ArrayDeque<Vec3>()
        queue.add(mins)
        val visited = mutableSetOf<Vec3>()
        var outsideCount = 0
        while (queue.isNotEmpty()) {
            val cur = queue.removeFirst()
            if (visited.contains(cur)) {
                continue
            } else {
                visited.add(cur)
            }
            val neighbors = cur.attachedNeighbors().filter {
                !visited.contains(it)
                        && it.x in mins.x..maxs.x
                        && it.y in mins.y..maxs.y
                        && it.z in mins.z..maxs.z
            }
            val (collisions, free) = neighbors.partition { points.contains(it) }
            outsideCount += collisions.size
            queue.addAll(free)
        }
        return outsideCount
    }

    override fun part2(): Any {
        val input = InputReader.getResourceLines(18)
        val parsed = input.map { Vec3.fromList(allInts(it)) }.toSet()

        var min = Vec3(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        var max = Vec3(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
        for (point in parsed) {
            min = point.minVals(min)
            max = point.maxVals(max)
        }
        // Calculate bounding box
        min += Vec3(-1, -1, -1)
        max += Vec3(1, 1, 1)

        return countOutside(parsed, min, max)
    }
}