package days.day17

import util.Point
import java.lang.IllegalArgumentException
import kotlin.math.max

class Tetris(private val jetstream: String) {

    private val shapesWithStartingLoc = listOf(
        setOf(Point(2, 0), Point(3, 0), Point(4, 0), Point(5, 0)), // -
        setOf(Point(2, 1), Point(3, 1), Point(4, 1), Point(3, 0), Point(3, 2)), // +
        setOf(Point(2, 0), Point(3, 0), Point(4, 0), Point(4, 1), Point(4, 2)), // _|
        setOf(Point(2, 0), Point(2, 1), Point(2, 2), Point(2, 3)), // |
        setOf(Point(2, 0), Point(2, 1), Point(3, 0), Point(3, 1)), // █
    )

    private val left = Point(-1, 0)
    private val down = Point(0, -1)
    private val right = Point(1, 0)
    private var tip = 0
    private var offsetTip = 0L
    private val occupied = mutableSetOf<Point>()

    private fun moveShape(shape: Set<Point>, movement: Point): Pair<Status, Set<Point>> {
        val nextPos = shape.map { it + movement }.toSet()
        return if (nextPos.any { it.x < 0L || it.x > 6L }) {
            Pair(Status.NotMoved, shape)
        } else if (nextPos.any { it.y < 0L } || occupied.intersect(nextPos).isNotEmpty()) {
            // Some collisions happen, so the shape cannot move
            // only store if it cannot move it wants to go down
            if (movement == down) {
                occupied.addAll(shape)
                Pair(Status.Stopped, shape)
            } else {
                Pair(Status.NotMoved, shape)
            }
        } else {
            Pair(Status.Moved, nextPos)
        }
    }
    
    fun run(limObjects: Long) : Long {
        var jetIdx = 0
        val tips = mutableListOf<Int>()
        val objectIds = mutableListOf<Long>()
        var objectIdx = 0L
        while (objectIdx < limObjects) {
            val shapeIdx = (objectIdx % shapesWithStartingLoc.size).toInt()
            var shape = shapesWithStartingLoc[shapeIdx].toSet()
            var res = moveShape(shape, Point(0, tip + 3))
            shape = res.second
            while (true) {
                // Check for cycle
                // 3 works for both my input and example, as it is only present once in a cycle 
                // might be different for you
                if (jetIdx == 0 && shapeIdx == 3 && tip < 100000) {
                    // From here there will be repetition
                    tips.add(tip)
                    objectIds.add(objectIdx)
                    if (tips.size == 3) {
                        val diffObjects = objectIds[2] - objectIds[1]
                        val diffTip = tips[2] - tips[1]
                        val numObjects = limObjects - objectIdx - 1
                        val numCycles = numObjects / diffObjects
                        objectIdx += numCycles * diffObjects
                        offsetTip = numCycles * diffTip
                        tips.clear()
                    }
                }

                // Move object around
                val jetPush = when (jetstream[jetIdx]) {
                    '<' -> left
                    '>' -> right
                    else -> throw IllegalArgumentException("Illegal push detected")
                }
                jetIdx = (jetIdx + 1) % jetstream.length
                res = moveShape(shape, jetPush)

                // left/right movement, won't settle a piece so we don't care about the stauts
                // We just move it down, if possible
                res = moveShape(res.second, down)
                shape = res.second

                if (res.first == Status.Stopped) {
                    // Movement stopped, so we need to update the tip
                    tip = max(tip, shape.maxBy { it.y }.y + 1)
                    break
                }
            }
            objectIdx++
        }
        return tip + offsetTip
    }
}
