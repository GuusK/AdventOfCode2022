package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import resources.InputReader

@ExtendWith(MockKExtension::class)
class Day09Test : GenericDayTest(9) {
    
    @Test
    fun example1(){
        Day09.readInput(InputReader.getResourceLines("./input/day09example.txt"))
        
        part1(Day09, 13)
        part2(Day09, 1)
    }

    @Test
    fun example2(){
        Day09.readInput(InputReader.getResourceLines("./input/day09example2.txt"))
        part2(Day09, 36)
    }
    
    @Test
    fun input(){
        Day09.readInput(InputReader.getResourceLines("./input/day09.txt"))
        part1(Day09, 6256)
        part2(Day09, 2665)
    }
    
}