package days

import days.day09.Move
import resources.InputReader
import util.Vec3
import kotlin.math.sign

object Day09 : DayInterface {
    override val dayNumber: Int
        get() = 9

    private var input = listOf<Move>()

    init {
        readInput(
            InputReader.getResourceLines("./input/day09.txt")
        )
    }

    fun readInput(input: List<String>) {
        this.input = input.map { Move.fromString(it) }
    }


    private fun executeMoves(numPieces: Int): Int {
        val ropePieces = MutableList(numPieces) { Vec3() }
        val tailTrail = mutableSetOf(Pair(0, 0))

        for (move in input) {
            for (idx in 0 until move.steps) {
                for (pieceIdx in 0 until ropePieces.size - 1) {
                    if (pieceIdx == 0) {
                        ropePieces[pieceIdx] += move.direction.vector
                    }
                    val curr = ropePieces[pieceIdx]
                    val next = ropePieces[pieceIdx + 1]

                    if (!next.touching(curr)) {
                        val diff = curr - next
                        ropePieces[pieceIdx + 1].x += diff.x.sign
                        ropePieces[pieceIdx + 1].y += diff.y.sign

                        if (pieceIdx == ropePieces.size - 2) {
                            tailTrail.add(
                                Pair(ropePieces[pieceIdx + 1].x, ropePieces[pieceIdx + 1].y)
                            )
                        }
                    }
                }
            }
        }
        return tailTrail.size
    }

    override fun part1(): Any {
        return executeMoves(2)
    }

    override fun part2(): Any {
        return executeMoves(10)
    }
}