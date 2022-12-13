package days

import days.day13.Contents
import days.day13.Node
import resources.InputReader

object Day13 : DayInterface {
    override val dayNumber: Int
        get() = 13

    fun compare(left: Node, right: Node): Int {
        if (left.type == Contents.Item && right.type == Contents.Item) {
            return left.num - right.num
        } else if (left.type == Contents.List && right.type == Contents.List) {
            var idx = 0

            // check content of list
            while (idx < left.lst.size && idx < right.lst.size) {
                val diff = compare(left.lst[idx], right.lst[idx])
                // if there is a difference, return else continue searching
                if (diff != 0) {
                    return diff
                } else {
                    idx++
                }
            }
            // unequal list length
            return left.lst.size - right.lst.size
        } else if (left.type == Contents.Item) {
            return compare(Node(Contents.List, left.num, listOf(left)), right)
        } else {
            // that.type == Contents.Item
            return compare(left, Node(Contents.List, left.num, listOf(right)))
        }
    }

    override fun part1(): Any {
        val input = InputReader
            .getResourceString(13)
            .split("\n\n")
        val parsed = input.map { pair ->
            pair
                .split("\n")
                .map { line -> Node.fromString(line) }
        }

        val processed = parsed
            .map { compare(it[0], it[1]) }

        return processed
            .withIndex()
            .filter { it.value < 0 }
            .sumOf { it.index + 1 }
    }

    override fun part2(): Any {
        val input = InputReader.getResourceLines(13) + "[[2]]" + "[[6]]"
        val parsed = input
            .filter { it.isNotEmpty() }
            .map { line -> Node.fromString(line) }

        val start = parsed[parsed.size - 2]
        val end = parsed[parsed.size - 1]

        val processed = parsed.sorted()
        val startIdx = processed.indexOf(start) + 1
        val endIdx = processed.indexOf(end) + 1
        return startIdx * endIdx
    }
}