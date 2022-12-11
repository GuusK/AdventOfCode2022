package days

import days.day11.Monkey
import resources.InputReader

object Day11 : DayInterface {
    override val dayNumber: Int
        get() = 11

    private fun execute(bigNums: Boolean): Long {
        val input = InputReader.getResourceString(11)

        val monkeys = input.split("\n\n")
            .map { Monkey.fromString(it) }

        var modulo = -1
        var upperbound = 20
        if (bigNums) {
            modulo = monkeys.map { it.test }.reduce { a, b -> a * b }
            upperbound = 10000
        }

        for (round in 1..upperbound) {
            for (monkey in monkeys) {
                while (!monkey.items.isEmpty()) {
                    val pass = monkey.inspectItem(modulo)
                    monkeys[pass.target].items.addLast(pass.value)
                }
            }
        }

        return monkeys
            .map { it.inspectCounter }
            .sortedDescending()
            .take(2)
            .reduce { a, b -> a * b }
    }


    override fun part1(): Any {
        return execute(false)
    }

    override fun part2(): Any {
        return execute(true)
    }
}