package days

import resources.InputReader

object Day01 : DayInterface {
    override val dayNumber: Int
        get() = 1

    private val totalCalories: List<Int> by lazy {
        InputReader.getResourceString(1)
            .split("\n\n")
            .map { calorieLst ->
                calorieLst
                    .split("\n")
                    .filter { x -> x.isNotEmpty() }
                    .sumOf { posInt -> posInt.toInt() }
            }
    }

    override fun part1(): Any {
        return totalCalories.maxOrNull() ?: -1
    }

    override fun part2(): Any {
        return totalCalories.sortedDescending().take(3).sum()
    }
}