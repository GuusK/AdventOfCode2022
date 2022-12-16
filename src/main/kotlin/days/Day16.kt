package days

import days.day16.State
import days.day16.Valve
import resources.InputReader

object Day16 : DayInterface {
    override val dayNumber: Int
        get() = 16

    data class FindLengthNode(val valve: Valve, val length: Int)

    fun findPathLength(valves: Map<String, Valve>, from: Valve, to: Valve, maxLength: Int): Int {
        val visited = mutableSetOf<String>()
        val deque = ArrayDeque(listOf(FindLengthNode(from, 0)))
        var minLength = maxLength
        while (deque.isNotEmpty()) {
            val cur = deque.removeFirst()
            if (cur.valve.id == to.id) {
                if (cur.length < minLength) {
                    minLength = cur.length
                }
            }
            if (visited.contains(cur.valve.id) || minLength < cur.length) {
                continue
            }
            visited.add(cur.valve.id)
            for (next in cur.valve.leadsTo) {
                deque.add(FindLengthNode(valves[next]!!, cur.length + 1))
            }
        }
        return minLength
    }

    private fun findMaxFlowPath(
        nonZeroValvesIds: Set<String>,
        valves: Map<String, Valve>,
        distances: Map<Pair<String, String>, Int>,
        startTime: Int
    ): State {
        val deque = ArrayDeque(listOf(State("AA", startTime, 0, setOf("AA"))))
        var maxFlow = State("EMPTY", startTime, 0, setOf())
        while (deque.isNotEmpty()) {
            val cur = deque.removeFirst()
            val leftToVisit = nonZeroValvesIds.minus(cur.opened)

            // Generous calculation of how much to still try and get
            val maxLeft = leftToVisit.map { valves[it]!!.flow * cur.timeleft }.sum()

            if (cur.sumFlow + maxLeft < maxFlow.sumFlow) {
                continue
            }

            for (next in leftToVisit) {
                val nextValve = valves[next]!!
                val distance = distances[cur.curValve to next]!!
                val nextState = State(
                    next,
                    cur.timeleft - distance - 1,
                    cur.sumFlow + nextValve.flow * (cur.timeleft - distance - 1),
                    cur.opened + next
                )

                if (nextState.opened.size < nonZeroValvesIds.size) {
                    deque.addLast(nextState)
                }
                if (nextState.sumFlow > maxFlow.sumFlow) {
                    maxFlow = nextState
                }
            }
        }
        return maxFlow
    }

    fun findDistances(valves: Map<String, Valve>): Map<Pair<String, String>, Int> {
        val nonZeroValves = valves.values.filter { it.flow != 0 }.toSet()
        val distances = mutableMapOf<Pair<String, String>, Int>()
        for (from in nonZeroValves + valves["AA"]!!) {
            for (to in nonZeroValves) {
                if (from == to) {
                    continue
                }
                distances[from.id to to.id] = findPathLength(valves, from, to, 30)
            }
        }
        return distances.toMap()
    }

    override fun part1(): Any {
        val input = InputReader.getResourceLines(16)
        val valves = input
            .map { Valve.fromString(it) }
            .associateBy { it.id }
        val nonZeroValves = valves.values
            .filter { it.flow != 0 }
            .toSet()
        val nonZeroValvesIds = nonZeroValves
            .map { it.id }
            .toSet()
        val distances = findDistances(valves)

        return findMaxFlowPath(nonZeroValvesIds, valves, distances, 30).sumFlow
    }

    override fun part2(): Any {
        val input = InputReader.getResourceLines(16)
        val valves = input.map { Valve.fromString(it) }
            .associateBy { it.id }
        val nonZeroValves = valves.values
            .filter { it.flow != 0 }
            .toSet()
        var nonZeroValvesIds = nonZeroValves
            .map { it.id }
            .toSet()
        val distances = findDistances(valves)
        val startTime = 26
        val manState = findMaxFlowPath(nonZeroValvesIds, valves, distances, startTime)
        nonZeroValvesIds = nonZeroValves
            .minus(manState.opened.map { valves[it]!! }.toSet())
            .map { it.id }
            .toSet()

        val elephantState = findMaxFlowPath(nonZeroValvesIds, valves, distances, startTime)
        return elephantState.sumFlow + manState.sumFlow
    }
}