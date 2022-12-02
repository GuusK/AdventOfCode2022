package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class Day02Test: GenericDayTest(2) {

    @Test
    fun part1() {
        part1(Day02, 15)
    }

    @Test
    fun part2() {
        part2(Day02, 12)
    }
}