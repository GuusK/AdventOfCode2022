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

    data class State(
        val curValve: String,
        val timeleft: Int,
        val sumFlow: Int,
        val opened: List<String>,
        val pathSinceLastOpened: List<String>
    )

    override fun part1(): Any {
//        val input = InputReader.getResourceLines("./input/day16prutsen")
//        val input = InputReader.getResourceLines("./input/day16example.txt")
        val input = InputReader.getResourceLines(16)

        val valves = input.map { Valve.fromString(it) }.associateBy { it.id }

        val sortedFlowValves = valves.values
            .filter { it.flow != 0 }
            .sortedByDescending { it.flow }
            .map { it.id }
            .toSet()

        val bfs = ArrayDeque(listOf(State("AA", 30, 0, listOf(), listOf())))

        var maxRelease = State("", -1, 0, listOf(), listOf())
        while (bfs.isNotEmpty()) {
            val curState = bfs.removeFirst()

            println("Curstate: $curState")

            // stop conditions
            if (curState.opened.toSet() == sortedFlowValves || curState.timeleft <= 0) {
                if (curState.sumFlow > maxRelease.sumFlow) {
                    maxRelease = curState
                }
                continue
            }

            val toBeOpened = sortedFlowValves.minus(curState.opened.toSet())
            if (toBeOpened.sumOf { valves[it]!!.flow * curState.timeleft } + curState.sumFlow < maxRelease.sumFlow) {
                // not possible to get higher result, so we can drop this state
                continue
            }

            val curValve = valves[curState.curValve]!!
            // Open current valve
            if (!curState.opened.contains(curState.curValve) && sortedFlowValves.contains(curState.curValve)) {
                val newState = State(
                    curState.curValve,
                    curState.timeleft - 1,
                    curState.sumFlow + (curState.timeleft - 1) * curValve.flow,
                    curState.opened + curValve.id,
                    listOf()
                )
                bfs.addLast(newState)
                if (newState.sumFlow > maxRelease.sumFlow) {
                    maxRelease = newState
                }
            }
            for (nextValve in curValve.leadsTo) {
                // Check for cycle
                if (!curState.pathSinceLastOpened.contains(nextValve)) {
                    // Moving to next valve
                    bfs.addLast(
                        State(
                            nextValve, curState.timeleft - 1, curState.sumFlow,
                            curState.opened, curState.pathSinceLastOpened + curValve.id
                        )
                    )
                } else {
                    // cycle detected, so ending
                    if (curState.sumFlow > maxRelease.sumFlow) {
                        maxRelease = curState
                    }
                }

            }
        }

        return maxRelease.sumFlow
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}