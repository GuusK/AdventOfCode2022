package days.day11

import util.allInts

class Monkey(
    val id: Int,
    var items: ArrayDeque<Long>,
    val test: Int,
    val trueTarget: Int,
    val falseTarget: Int,
    val op: Operation,
    val opParam: Int
) {
    var inspectCounter = 0L

    fun inspectItem(modulo: Int): Pass {
        inspectCounter++
        val item = items.removeFirst()
        val valueAfterInspection = when (op) {
            Operation.Add -> item + opParam
            Operation.Mult -> item * opParam
            Operation.Square -> item * item
        }

        val newItem = if (modulo < 0) {
            valueAfterInspection / 3
        } else {
            valueAfterInspection % modulo
        }

        val target = if (newItem % test == 0L) {
            trueTarget
        } else {
            falseTarget
        }
        return Pass(target, newItem)
    }

    companion object {
        private fun determineOp(input: String): Pair<Operation, Int> {
            return if (input.contains("old * old")) {
                Pair(Operation.Square, 1)
            } else if (input.contains("*")) {
                Pair(Operation.Mult, allInts(input)[0])
            } else {
                Pair(Operation.Add, allInts(input)[0])
            }
        }

        fun fromString(input: String): Monkey {
            val splitted = input.split("\n")
            val id = allInts(splitted[0])[0]
            val items = ArrayDeque(allInts(splitted[1]).map { it.toLong() })
            val (op, opParam) = determineOp(splitted[2])
            val test = allInts(splitted[3])[0]
            val trueTarget = allInts(splitted[4])[0]
            val falseTarget = allInts(splitted[5])[0]

            return Monkey(id, items, test, trueTarget, falseTarget, op, opParam)
        }
    }
}