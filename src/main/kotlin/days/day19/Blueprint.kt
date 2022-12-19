package days.day19

import util.allInts

data class Blueprint(
    val id: Int,
    val oreRobot: Int,
    val clayRobot: Int,
    val obsidianRobot: Pair<Int, Int>,
    val geodeRobot: Pair<Int, Int>
) {
    fun maxOre(): Int {
        return listOf(oreRobot, clayRobot, obsidianRobot.first, geodeRobot.first).max()
    }
    
    companion object {
        fun fromString(line: String): Blueprint {
            val ints = allInts(line)
            val id = ints[0]
            val oreRobot = ints[1]
            val clayRobot = ints[2]
            val obsidianRobot = Pair(ints[3],ints[4])
            val geodeRobot = Pair(ints[5], ints[6])
            return Blueprint(id, oreRobot, clayRobot, obsidianRobot, geodeRobot)
        }
    }
}
