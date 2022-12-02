package days

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import resources.InputReader

@ExtendWith(MockKExtension::class)
internal class Day01Test {

    @BeforeEach
    fun init(){
        mockkObject(InputReader)
        every {
            InputReader.getResourceLines(1)
        } returns InputReader.getResourceLines("./input/day01example.txt")
    }

    @Test
    fun part1() {
        assertEquals(Day01.part1(), 24000)
    }

    @Test
    fun part2() {
        assertEquals(Day01.part2(), 45000)
    }
}