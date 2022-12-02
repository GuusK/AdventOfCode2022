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
internal class Day02Test {

    @BeforeEach
    fun init(){
        mockkObject(InputReader)
        every {
            InputReader.getResourceLines(2)
        } returns InputReader.getResourceLines("./input/day02example.txt")
    }

    @Test
    fun part1() {
        assertEquals(Day02.part1(), 15)
    }

    @Test
    fun part2() {
        assertEquals(Day02.part2(), 12)
    }
}