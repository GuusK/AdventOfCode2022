package days

import days.day02.Outcome
import days.day02.Shape
import resources.InputReader

object Day02 : DayInterface {

    override val dayNumber: Int
        get() = 2

    val rounds by lazy {
        val input = InputReader.getResourceLines(2)
        input.map { x -> x.split(" ") }
    }

    override fun part1(): Any {
        var totalPoints = 0
        for (moves in rounds) {
            val them = Shape.fromLetter(moves[0][0])
            val us = Shape.fromLetter(moves[1][0])

            totalPoints += us.points
            totalPoints += Outcome.determine(us, them).points
        }

        return totalPoints
    }

    override fun part2(): Any {
        var totalPoints = 0
        for (moves in rounds) {
            val them = Shape.fromLetter(moves[0][0])
            val outcome = Outcome.fromLetter(moves[1][0])

            totalPoints += outcome.points
            if (outcome == Outcome.Lose) {
                totalPoints += Shape.loserOf(them).points
            }
            if (outcome == Outcome.Draw) {
                totalPoints += them.points
            }
            if (outcome == Outcome.Win) {
                totalPoints += Shape.winnerOf(them).points
            }
        }
        return totalPoints
    }
}