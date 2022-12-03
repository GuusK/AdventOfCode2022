package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class Day03Test: GenericDayTest(3) {
    @Test
    fun part1() {
        part1(Day03, 157)
    }

    @Test
    fun part2() {
        part2(Day03, 70)
    }
}