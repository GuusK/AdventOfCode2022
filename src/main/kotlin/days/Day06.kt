package days

import resources.InputReader

object Day06 : DayInterface {
    override val dayNumber: Int
        get() = 6
    
    var input = InputReader.getResourceString(6)
    
    private fun findDistinctSubset(input: String, numberChars: Int): IndexedValue<String> {
        return input.windowed(numberChars)
            .withIndex()
            .first { it.value.toSet().size == numberChars }
    }

    override fun part1(): Any {
        return findDistinctSubset(input, 4).index + 4
    }

    override fun part2(): Any {
        return findDistinctSubset(input, 14).index + 14
    }
}