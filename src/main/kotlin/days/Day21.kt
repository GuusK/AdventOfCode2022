package days

import days.day21.MonkeyType
import days.day21.ShoutingMonkey
import resources.InputReader

object Day21 : DayInterface {
    override val dayNumber: Int
        get() = 21

    data class ContainsHuman(val left: Boolean, val right: Boolean) {
        fun hasHuman(): Boolean = left || right
    }

//        val input = InputReader.getResourceLines("./input/day21example.txt")
    val input = InputReader.getResourceLines(dayNumber)

    val monkeys: Map<String, ShoutingMonkey> = input.map { ShoutingMonkey.fromString(it) }.associateBy { it.id }
    val monkeyContainsHuman: MutableMap<String, ContainsHuman> = mutableMapOf()
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

    fun containsHuman(id: String?): Boolean {
        return if (id == null) {
            false
        } else if ("humn".equals(id)) {
            true
        } else if (monkeyContainsHuman.containsKey(id)) {
            monkeyContainsHuman[id]!!.hasHuman()
        } else {
            val monkey = monkeys[id]!!
            val left = containsHuman(monkey.left)
            val right = containsHuman(monkey.right)
            val res = ContainsHuman(left, right)
            monkeyContainsHuman[id] = res
            res.hasHuman()
        }
    }

    fun walkHumanBranch(id: String, required: Long): Long {
        if ("humn".equals(id)) {
            return required
        }
        val monkey = monkeys[id]!!
        val hasHumanBranch = monkeyContainsHuman[id]!!
        val humanBranch: String
        val otherVal = if (hasHumanBranch.left) {
            humanBranch = monkey.left!!
            calculateMonkeyValue(monkey.right!!)
        } else {
            humanBranch = monkey.right!!
            calculateMonkeyValue(monkey.left!!)
        }
        return when (monkey.operation) {
            '+' -> walkHumanBranch(humanBranch, required - otherVal)
            '-' -> walkHumanBranch(humanBranch, required + otherVal)
            '*' -> walkHumanBranch(humanBranch, required / otherVal)
            '/' -> walkHumanBranch(humanBranch, required * otherVal)
            else -> throw IllegalArgumentException("Unknown operation: " + monkey.operation)
        }
    }

    fun calculatePart2(): Long {
        val root = monkeys["root"]!!

        val hasHumanBranch = monkeyContainsHuman["root"]!!
        return if (hasHumanBranch.left) {
            val required = calculateMonkeyValue(root.right!!)
            walkHumanBranch(root.left!!, required)
        } else {
            val needed = calculateMonkeyValue(root.left!!)
            walkHumanBranch(root.right!!, needed)
        }
    }

    fun cheesePart2(id: String): String {
        val monkey = monkeys[id]!!
        if ("root".equals(id)) {
            return "(" + cheesePart2(monkey.left!!) + ") = (" + cheesePart2(monkey.right!!) + ")"
        } else if ("humn".equals(id)) {
            return "x"
        } else if (monkey.type == MonkeyType.Number) {
            return monkey.num.toString()
        } else {
            return "(" + cheesePart2(monkey.left!!) + " " + monkey.operation + " " + cheesePart2(monkey.right!!) + ")"
        }
    }

    override fun part1(): Any {
//        return calculateMonkeyValue("root")
        return -1
    }

    override fun part2(): Any {
        println(cheesePart2("root")) // and put it in https://www.mathpapa.com/simplify-calculator/
        return -1
        // Works for test input
//        containsHuman("root")
//        return calculatePart2()
    }
}