package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class Day01Test : GenericDayTest(1) {

    @Test
    fun part1() {
        part1(Day01, 24000)
    }

    @Test
    fun part2() {
        part2(Day01, 45000)
    }
}