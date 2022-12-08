package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import resources.InputReader

@ExtendWith(MockKExtension::class)
class Day08Test: GenericDayTest(8) {

    @Test
    fun part1() {
        part1(Day08, 21)
    }

    @Test
    fun part2() {
        part2(Day08, 8)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun readTestInput() {
            Day08.readInput(InputReader.get2DIntLists("./input/day08example.txt"))
        }
    }
}