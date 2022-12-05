package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class Day05Test: GenericDayTest(5) {
    @Test
    fun part1() {
        part1(Day05, "CMZ")
    }

    @Test
    fun part2() {
        part2(Day05, "MCD")
    }
}