package days

import days.day05.Op
import resources.InputReader

object Day05 : DayInterface {
    override val dayNumber: Int
        get() = 5

    private var stacks = mutableListOf<ArrayDeque<Char>>()
    private var instructStart: Int = -1
    private var ops: List<Op> = listOf()

    private fun readInput() {
        val input = InputReader.getResourceLines(dayNumber)
        val stacks = mutableListOf<ArrayDeque<Char>>()
        val numStacks = input[0].length / 4 + 1
        val chars: List<MutableList<Char>> = (0 until numStacks).map { mutableListOf() }

        for (idx in input.indices) {
            val line = input[idx]
            if (line[1] == '1') {
                instructStart = idx + 2
                break
            }

            for (x in 0 until numStacks) {
                val toAdd = line[x * 4 + 1]
                if (toAdd != ' ') {
                    chars[x].add(toAdd)
                }
            }
        }

        for (x in chars.indices) {
            stacks.add(ArrayDeque(chars[x]))
        }

        this.stacks = stacks
        ops = (instructStart until input.size)
            .filter { input[it].isNotEmpty() }
            .map { Op.fromString(input[it]) }
    }

    private fun findResult(): String {
        var res = ""
        for (stack in stacks) {
            res += stack.first()
        }
        return res
    }

    override fun part1(): Any {
        readInput()
        for (op in ops) {
            for (num in 0 until op.num) {
                stacks[op.to].addFirst(stacks[op.from].removeFirst())
            }
        }
        return findResult()
    }

    override fun part2(): Any {
        readInput()
        for (op in ops) {
            val toMove = stacks[op.from].take(op.num)
            for (x in toMove.indices) {
                stacks[op.from].removeFirst()
                stacks[op.to].addFirst(toMove[toMove.size - 1 - x])
            }
        }
        return findResult()

    }
}