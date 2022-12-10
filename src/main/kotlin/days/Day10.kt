package days

import days.day10.CPU
import days.day10.Operation
import resources.InputReader

object Day10 : DayInterface {
    override val dayNumber: Int
        get() = 10

    var input = InputReader
        .getResourceLines(10)
        .map { Operation.fromString(it) }


    fun createAndRunCPU(input: List<Operation>): CPU {
        val cpu = CPU(input)
        cpu.execute()
        return cpu
    }

    override fun part1(): Any {
        return createAndRunCPU(input).getSumSignalStrengths(listOf(20, 60, 100, 140, 180, 220))
    }

    override fun part2(): Any {
        createAndRunCPU(input).printCRT()
        return Unit
    }
}