package days

import org.junit.jupiter.api.Test

class Day12Test : GenericDayTest(12) {
    @Test
    fun part1(){
        part1(Day12, 31)
    }

    @Test
    fun part2(){
        part2(Day12, 29)
    }
}