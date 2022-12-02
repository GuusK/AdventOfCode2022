package days

import days.day02.Outcome
import days.day02.Shape
import resources.InputReader

object Day02 : DayInterface {

    override val number: Int
        get() = 2

    val input = InputReader.getResourceLines(2)

    override fun part1(): Any {
        var totalPoints = 0
        for (line in input) {
            val moves = line.split(" ")
            val them = Shape.fromLetter(moves[0][0])
            val us = Shape.fromLetter(moves[1][0])

            totalPoints += us.points
            if (us == Shape.winnerOf(them)) {
                totalPoints += 6
            } else if (us.points == them.points) {
                totalPoints += 3
            }
        }

        return totalPoints
    }

    override fun part2(): Any {
        var totalPoints = 0
        for (line in input) {
            val moves = line.split(" ")
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