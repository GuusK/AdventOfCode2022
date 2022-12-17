package days.day17

import util.PointL
import java.lang.IllegalArgumentException
import kotlin.math.max

class Tetris(val jetstream: String) {

    private val shapesWithStartingLoc = listOf(
        setOf(PointL(2, 0), PointL(3, 0), PointL(4, 0), PointL(5, 0)), // -
        setOf(PointL(2, 1), PointL(3, 1), PointL(4, 1), PointL(3, 0), PointL(3, 2)), // +
        setOf(PointL(2, 0), PointL(3, 0), PointL(4, 0), PointL(4, 1), PointL(4, 2)), // mirror(L)
        setOf(PointL(2, 0), PointL(2, 1), PointL(2, 2), PointL(2, 3)), // |
        setOf(PointL(2, 0), PointL(2, 1), PointL(3, 0), PointL(3, 1)), // █
    )

    val left = PointL(-1, 0)
    val down = PointL(0, -1)
    val right = PointL(1, 0)
    var tip = 0L
    var offsetTip = 0L
    val occupied = mutableSetOf<PointL>()

    fun moveShape(shape: Set<PointL>, movement: PointL): Pair<Status, Set<PointL>> {
        val nextPos = shape.map { it + movement }.toSet()
        return if (nextPos.any { it.x < 0L || it.x > 6L }) {
            Pair(Status.NotMoved, shape)
        } else if (nextPos.any { it.y < 0L } || occupied.intersect(nextPos).isNotEmpty()) {
            // Some collisions happen, so the shape cannot move and is stored
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
        val tips = mutableListOf<Long>()
        val objectIds = mutableListOf<Long>()
        var objectIdx = 0L
        while (objectIdx < limObjects) {
            val shapeIdx = (objectIdx % shapesWithStartingLoc.size).toInt()
            var shape = shapesWithStartingLoc[shapeIdx].toSet()
            var res = moveShape(shape, PointL(0, tip + 3))
            shape = res.second
            while (true) {
                // Check for cycle
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
