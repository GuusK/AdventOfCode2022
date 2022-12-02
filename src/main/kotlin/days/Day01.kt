package days

import resources.InputReader

object Day01 : DayInterface {
    override val number: Int
        get() = 1

    private var totalCalories: List<Int> = listOf()

    init {
        val input = InputReader.getResourceLines(1)
        var parsed: MutableList<List<Int>> = mutableListOf()

        var tmp: MutableList<Int> = mutableListOf()
        for (it in input) {
            if ("".equals(it)) {
                parsed += tmp
                tmp = mutableListOf()
            } else {
                tmp += it.toInt()
            }
        }
        // Because last empty line is skipped when reading
        parsed += tmp

        totalCalories = parsed.map { x -> x.sum() }
    }

    override fun part1(): Any {
        return totalCalories.maxOrNull() ?: -1
    }

    override fun part2(): Any {
        return totalCalories.sorted().takeLast(3).sum()
    }
}