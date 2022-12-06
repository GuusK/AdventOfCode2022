package days

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class Day06Test : GenericDayTest(6) {
    
    fun part1Call(input: String, outcome: Any){
        Day06.input = input
        part1(Day06, outcome)
    }

    fun part2Call(input: String, outcome: Any){
        Day06.input = input
        part2(Day06, outcome)
    }
    
    @Test
    fun part1() {
        // example.txt
        part1Call("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7)
        part1Call("bvwbjplbgvbhsrlpgdmjqwftvncz", 5)
        part1Call("nppdvjthqldpwncqszvftbrmjlhg", 6)
        part1Call("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10)
        part1Call("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11)
    }

    @Test
    fun part2() {
        part2Call("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 19)
        part2Call("bvwbjplbgvbhsrlpgdmjqwftvncz", 23)
        part2Call("nppdvjthqldpwncqszvftbrmjlhg", 23)
        part2Call("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 29)
        part2Call("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 26)
    }
}