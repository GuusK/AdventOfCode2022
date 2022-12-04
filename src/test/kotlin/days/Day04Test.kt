package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class Day04Test: GenericDayTest(4) {
    @Test
    fun part1() {
        part1(Day04, 2)
    }

    @Test
    fun part2() {
        part2(Day04, 4)
    }
}