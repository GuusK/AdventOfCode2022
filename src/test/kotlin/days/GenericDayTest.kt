package days

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import resources.InputReader

abstract class GenericDayTest(private val dayNum: Int) {

    @BeforeEach
    fun init() {
        mockkObject(InputReader)
        val exampleFileLocation = "./input/day" + InputReader.stringifyDate(dayNum) + "example.txt"
        every {
            InputReader.getResourceLines(dayNum)
        } returns InputReader.getResourceLines(exampleFileLocation)

        every {
            InputReader.getResourceString(dayNum)
        } returns InputReader.getResourceString(exampleFileLocation)
    }

    fun part1(day: DayInterface, expected: Any) {
        Assertions.assertEquals(expected, day.part1())
    }

    fun part2(day: DayInterface, expected: Any) {
        Assertions.assertEquals(expected, day.part2())
    }
}