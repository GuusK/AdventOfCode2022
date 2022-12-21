package days

import resources.InputReader
import kotlin.math.abs

object Day20 : DayInterface {
    override val dayNumber: Int
        get() = 20


    val input by lazy {
        InputReader.getResourceLines(dayNumber)
    }

    fun mix(times: Int, input: List<IndexedValue<Long>>): List<IndexedValue<Long>> {
        val deque = ArrayDeque<IndexedValue<Long>>()
        deque.addAll(input)
        for (round in 0 until (times * input.size)) {
            val idx = round % input.size
            val loc = deque.indexOf(input[idx])

            // Remove items before the needed item
            for (remIdx in 0 until loc) {
                deque.addLast(deque.removeFirst())
            }

            val item = deque.removeFirst()
            if (item.value > 0) {
                // positive value, so progress through
                (0 until item.value % (input.size - 1)).forEach {
                    deque.addLast(deque.removeFirst())
                }
                deque.addFirst(item)
            } else {
                // value is negative, so reverse
                (0 until abs(item.value) % (input.size - 1)).forEach {
                    deque.addFirst(deque.removeLast())
                }
                deque.addLast(item)
            }

            // rotate back to original position
            for (remIdx in 0 until loc) {
                deque.addFirst(deque.removeLast())
            }
        }
        return deque.toList()
    }

    fun determineOutput(processedList: List<IndexedValue<Long>>, zeroElem: IndexedValue<Long>): Long {
        val dls = processedList.size
        val zeroIdx = processedList.indexOf(zeroElem)
        return processedList[(zeroIdx + 1000) % dls].value + processedList[(zeroIdx + 2000) % dls].value + processedList[(zeroIdx + 3000) % dls].value
    }

    override fun part1(): Any {
        val parsed = input.map { it.toLong() }.withIndex().toList()
        val dequeList = mix(1, parsed)
        val zeroElem = parsed.find { it.value == 0L }
        return determineOutput(dequeList, zeroElem!!)
    }

    override fun part2(): Any {
        val parsed = input.map { it.toLong() * 811589153L }.withIndex().toList()
        val dequeList = mix(10, parsed)
        val zeroElem = parsed.find { it.value == 0L }
        return determineOutput(dequeList, zeroElem!!)
    }
}