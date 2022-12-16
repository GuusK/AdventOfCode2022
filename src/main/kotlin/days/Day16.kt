package days

import resources.InputReader
import util.allInts

object Day16 : DayInterface {
    override val dayNumber: Int
        get() = 16

    data class Valve(val id: String, val flow: Int, val leadsTo: List<String>) {
        companion object {
            fun fromString(line: String): Valve {
                val id = line.substring("Valve ".length, "Valve ".length + 2)
                val flowrate = allInts(line)[0]
                val leadsTo = if (line.contains(",")) {
                    // multiple next valves
                    line
                        .substring(line.indexOf("valves ") + "valves ".length)
                        .split(", ")
                } else {
                    // single next valve
                    listOf(line.substring(line.length - 2))
                }
                return Valve(id, flowrate, leadsTo)
            }
        }
    }
    
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

    data class DistState(
        val curValve: String,
        val timeleft: Int,
        val sumFlow: Int,
        val opened: Set<String>,
    )

    override fun part1(): Any {
        val input = InputReader.getResourceLines(16)
        val valves = input.map { Valve.fromString(it) }.associateBy { it.id }
        val nonZeroValves = valves.values
            .filter { it.flow != 0 }
            .toSet()
        val nonZeroValvesIds = nonZeroValves.map { it.id }.toSet()
        val distances = mutableMapOf<Pair<String, String>, Int>()

        for (from in nonZeroValves + valves["AA"]!!) {
            for (to in nonZeroValves) {
                if (from == to) {
                    continue
                }
                distances[from.id to to.id] = findPathLength(valves, from, to, 30)
            }
        }

        val deque = ArrayDeque(listOf(DistState("AA", 30, 0, setOf("AA"))))
        var maxFlow = DistState("EMPTY", 30, 0, setOf())
        while (deque.isNotEmpty()) {
            val cur = deque.removeFirst()
            val leftToVisit = nonZeroValvesIds.minus(cur.opened)

            // Generous calculation of how much to still try and get
            val maxLeft = leftToVisit
                .map { valves[it]!!.flow * cur.timeleft }
                .sum()

            if (cur.sumFlow + maxLeft < maxFlow.sumFlow){
                continue
            }

            for (next in leftToVisit) {
                val nextValve = valves[next]!!
                val distance = distances[cur.curValve to next]!!
                val nextState = DistState(
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
                    println("New max: $nextState")
                }
            }
        }

        return maxFlow.sumFlow
    }

    override fun part2(): Any {
        return -1
    }
}