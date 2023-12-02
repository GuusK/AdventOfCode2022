package days

import resources.InputReader
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

object Day25 : DayInterface {
    override val dayNumber: Int
        get() = 25

    fun snafuToDec(snafu: String): Double {
        var res = 0.0
        for (x in snafu.reversed().withIndex()) {
            val toAdd = when (x.value) {
                '=' -> -2 * Math.pow(5.0, x.index.toDouble())
                '-' -> -1 * Math.pow(5.0, x.index.toDouble())
                '0' -> 0.0
                '1' -> 1 * Math.pow(5.0, x.index.toDouble())
                '2' -> 2 * Math.pow(5.0, x.index.toDouble())
                else -> throw IllegalArgumentException("unknown identifier of value")
            }
            res += toAdd
        }
        println("Processed $snafu to $res")
        return res
    }

    fun decToSnafu(input: Long): String {
        if (input == 0L) {
            return ""
        }

        return when (input % 5L) {
            0L -> decToSnafu(input / 5L) + "0";
            1L -> decToSnafu(input / 5L) + "1";
            2L -> decToSnafu(input / 5L) + "2";
            3L -> decToSnafu((input + 2) / 5L) + "=";
            4L -> decToSnafu((input + 1) / 5L) + "-";
            else -> throw IllegalStateException("appearently mod 5 can run outside of 0-4")
        }
    }

    override fun part1(): Any {
        val input = InputReader.getResourceLines(25)
        val res = input.map(::snafuToDec).sum()
        print("Sum: $res")
        return decToSnafu(res.toLong())
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}