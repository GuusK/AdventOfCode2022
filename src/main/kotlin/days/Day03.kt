package days

import resources.InputReader

object Day03 : DayInterface {
    override val dayNumber: Int
        get() = 3

    private fun charToPriority(char: Char): Int {
        return if (char.isUpperCase()) {
            27
        } else {
            1
        } + (char.lowercaseChar() - 'a')
    }

    val input = InputReader.getResourceLines(3)

    override fun part1(): Any {
        var score = 0
        for (line in input) {
            val firstHalf = line.subSequence(0, line.length / 2).toSet()
            val secondHalf = line.subSequence(line.length / 2, line.length).toSet()
            val matchingChar = firstHalf.intersect(secondHalf)
            score += charToPriority(matchingChar.first())
        }
        return score
    }

    override fun part2(): Any {
        var score = 0
        for (idx in input.indices step 3) {
            val (a,b,c) = input.subList(idx, idx + 3).map { line -> line.toSet() }
            val matchingChar = a.intersect(b).intersect(c).first()
            score += charToPriority(matchingChar)
        }
        return score
    }

}