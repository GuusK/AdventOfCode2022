package days

import days.day21.MonkeyType
import days.day21.ShoutingMonkey
import resources.InputReader

object Day21 : DayInterface {
    override val dayNumber: Int
        get() = 21


//        val input = InputReader.getResourceLines("./input/day21example.txt")
    val input = InputReader.getResourceLines(dayNumber)

    val monkeys: Map<String, ShoutingMonkey> = input.map { ShoutingMonkey.fromString(it) }.associateBy { it.id }
    
    val monkeyVal: MutableMap<String, Long> = monkeys.values
        .filter { it.type == MonkeyType.Number }
        .map { it.id to it.num }
        .toMap()
        .toMutableMap()

    fun calculateMonkeyValue(id: String): Long {
        if (monkeyVal.contains(id)) {
            return monkeyVal[id]!!
        }

        val monkey = monkeys[id]!!
        // all monkeys of type number are already in monkeyVal, so this should be an operation
        val left = calculateMonkeyValue(monkey.left!!)
        val right = calculateMonkeyValue(monkey.right!!)
        return when (monkey.operation) {
            '+' -> left + right
            '-' -> left - right
            '*' -> left * right
            '/' -> left / right
            else -> throw IllegalArgumentException("Unknown operation: " + monkey.operation)
        }
    }

    override fun part1(): Any {
        return calculateMonkeyValue("root")
//        return -1
    }

    override fun part2(): Any {
        return -1
    }
}