package days.day16

import util.allInts

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