package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import resources.InputReader

@ExtendWith(MockKExtension::class)
class Day07Test: GenericDayTest(7) {
    @Test
    fun part1() {
        Day07.readInput(InputReader.getResourceLines("./input/day07example.txt"))
        part1(Day07, 95437)
    }

    @Test
    fun part2() {
        Day07.readInput(InputReader.getResourceLines("./input/day07example.txt"))
        part2(Day07, 24933642)
    }
}