package days

import resources.InputReader

object Day03 : DayInterface {
    override val dayNumber: Int
        get() = 3

    private fun charToPriority(char: Char): Int {
        return if (char.isUpperCase()) {
            26
        } else {
            0
        } + char.lowercaseChar().code - 96
    }

    val input = InputReader.getResourceLines(3)

    override fun part1(): Any {
        var score = 0
        for (line in input) {
            val firstHalf = line.subSequence(0, line.length / 2).toSet()
            val secondHalf = line.subSequence(line.length / 2, line.length)
            val matchingChar = secondHalf.map { char -> Pair(char, firstHalf.contains(char)) }
                .filter { tup -> tup.second }
                .first().first
            score += charToPriority(matchingChar)
        }
        return score
    }

    override fun part2(): Any {
        var score = 0
        for (idx in input.indices step 3) {
            val rucksacks = input.subList(idx, idx + 3).map { line -> line.toSet() }
            val matchingChar = rucksacks[0].intersect(rucksacks[1]).intersect(rucksacks[2]).first()
            score += charToPriority(matchingChar)
        }
        return score
    }

}