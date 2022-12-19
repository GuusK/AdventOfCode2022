package days

import days.day19.Blueprint
import days.day19.ResourceRobotState
import resources.InputReader
import kotlin.math.max

object Day19 : DayInterface {
    override val dayNumber: Int
        get() = 19
    
    private fun triangularSum(limit: Int): Int {
        return (limit * (limit + 1)) / 2
    }
    
    private fun calcMaxGeodes(state: ResourceRobotState, timelimit: Int): Int {
        val timeleft = timelimit - state.time
        return state.geode.count + triangularSum(state.geode.robot + timeleft) - triangularSum(state.geode.robot)
    }

    private fun determineOpenGeodes(blueprint: Blueprint, timelimit: Int): Int {
        val queue = ArrayDeque<ResourceRobotState>()
        queue.addFirst(ResourceRobotState.init())
        var maxGeodes = -1
        val visited = HashSet<ResourceRobotState>()
        while (queue.isNotEmpty()) {
            val curState = queue.removeFirst()
            if (visited.contains(curState)) {
                continue
            } else {
                visited.add(curState)
            }

            val nextState = curState.collectResources()

            if (nextState.time == timelimit) {
                if (maxGeodes < nextState.geode.count) {
                    maxGeodes = nextState.geode.count
                }
                continue
            }

            if (calcMaxGeodes(nextState, timelimit) < maxGeodes) {
                // Cant produce larger number of maxGeodes, so we can forget about this one
                continue
            }

            var madeRobot = false
            if (curState.canCraftGeodeRobot(blueprint)) {
                queue.addFirst(nextState.craftGeodeRobot(blueprint))
                madeRobot = true
            }
            if (curState.canCraftObsidianRobot(blueprint) && curState.wantCraftObsidianRobot(blueprint)) {
                queue.add(nextState.craftObsidianRobot(blueprint))
                madeRobot = true
            }
            if (curState.canCraftClayRobot(blueprint) && curState.wantCraftClayRobot(blueprint)) {
                queue.add(nextState.craftClayRobot(blueprint))
                madeRobot = true
            }
            if (curState.canCraftOreRobot(blueprint) && curState.wantCraftOreRobot(blueprint)) {
                queue.add(nextState.craftOreRobot(blueprint))
                madeRobot = true
            }

            if (!madeRobot
                || curState.ore.count < max(blueprint.maxOre(), blueprint.clayRobot)
                || curState.clay.count < blueprint.obsidianRobot.second
                || curState.obsidian.count < blueprint.geodeRobot.second
            ) {
                queue.add(nextState)
            }
        }

        return maxGeodes
    }

    override fun part1(): Any {
        val input = InputReader.getResourceLines(19)
        val parsed = input.map { Blueprint.fromString(it) }
            .map { determineOpenGeodes(it, 24) }

        return parsed
            .withIndex()
            .sumOf { (index, qualityLevel) ->
                qualityLevel * (index + 1)
            }
    }

    override fun part2(): Any {
        val input = InputReader.getResourceLines(19)

        val parsed = input
            .take(3)
            .map { Blueprint.fromString(it) }
            .map { determineOpenGeodes(it, 32) }

        return parsed.reduce { a, b -> a * b }
    }
}