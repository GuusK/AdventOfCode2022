package days

import days.day17.Tetris
import resources.InputReader

object Day17 : DayInterface {
    override val dayNumber: Int
        get() = 17

    override fun part1(): Any {
        val input = InputReader.getResourceLines(17)
        val tetris = Tetris(input[0])
        return tetris.run(2022)
    }

    override fun part2(): Any {
        val input = InputReader.getResourceLines(17)
        val tetris = Tetris(input[0])
        return tetris.run(1000000000000)
    }
}