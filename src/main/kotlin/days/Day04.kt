package days

import resources.InputReader

object Day04 : DayInterface {
    override val dayNumber: Int
        get() = 4

    fun elfRange(input: String): IntRange {
        val (p1, p2) = input.split("-")
        val begin = p1.toInt()
        val end = p2.toInt()
        return begin..end
    }

    private val parsed: List<Pair<Set<Int>, Set<Int>>> by lazy {
        val input = InputReader.getResourceLines(dayNumber)
        input.map { line ->
            val (e1, e2) = line.split(",")
            val elf1 = elfRange(e1).toSet()
            val elf2 = elfRange(e2).toSet()
            elf1 to elf2
        }
    }

    override fun part1(): Any {
        return parsed.map { (elf1, elf2) ->
            elf1.containsAll(elf2) || elf2.containsAll(elf1)
        }.count { it }
    }

    override fun part2(): Any {
        return parsed.map { (elf1, elf2) ->
            elf1.intersect(elf2).isNotEmpty()
        }.count { it }
    }
}