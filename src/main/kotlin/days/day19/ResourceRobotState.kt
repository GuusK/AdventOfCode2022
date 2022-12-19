package days.day19

data class ResourceRobotState(
    val time: Int,
    val ore: ResourceRobotCounts,
    val clay: ResourceRobotCounts,
    val obsidian: ResourceRobotCounts,
    val geode: ResourceRobotCounts
) {
    fun collectResources(): ResourceRobotState {
        return ResourceRobotState(
            time + 1,
            ore.step(),
            clay.step(),
            obsidian.step(),
            geode.step()
        )
    }

    fun canCraftOreRobot(blueprint: Blueprint): Boolean {
        return ore.count >= blueprint.oreRobot
    }

    fun canCraftClayRobot(blueprint: Blueprint): Boolean {
        return ore.count >= blueprint.clayRobot
    }

    fun canCraftObsidianRobot(blueprint: Blueprint): Boolean {
        return ore.count >= blueprint.obsidianRobot.first
                && clay.count >= blueprint.obsidianRobot.second
    }

    fun canCraftGeodeRobot(blueprint: Blueprint): Boolean {
        return ore.count >= blueprint.geodeRobot.first
                && obsidian.count >= blueprint.geodeRobot.second
    }
    
    fun wantCraftOreRobot(blueprint: Blueprint): Boolean {
        return ore.robot < blueprint.maxOre() 
    }

    fun wantCraftClayRobot(blueprint: Blueprint): Boolean {
        return clay.robot < blueprint.obsidianRobot.second 
    }
    
    fun wantCraftObsidianRobot(blueprint: Blueprint): Boolean {
        return obsidian.robot < blueprint.geodeRobot.second 
    }

    fun craftOreRobot(blueprint: Blueprint): ResourceRobotState {
        return ResourceRobotState(
            time,
            ResourceRobotCounts(ore.count - blueprint.oreRobot, ore.robot + 1),
            ResourceRobotCounts(clay.count, clay.robot),
            ResourceRobotCounts(obsidian.count, obsidian.robot),
            ResourceRobotCounts(geode.count, geode.robot)
        )
    }

    fun craftClayRobot(blueprint: Blueprint): ResourceRobotState {
        return ResourceRobotState(
            time,
            ResourceRobotCounts(ore.count - blueprint.clayRobot, ore.robot),
            ResourceRobotCounts(clay.count, clay.robot + 1),
            ResourceRobotCounts(obsidian.count, obsidian.robot),
            ResourceRobotCounts(geode.count, geode.robot)
        )
    }

    fun craftObsidianRobot(blueprint: Blueprint): ResourceRobotState {
        return ResourceRobotState(
            time,
            ResourceRobotCounts(ore.count - blueprint.obsidianRobot.first, ore.robot),
            ResourceRobotCounts(clay.count - blueprint.obsidianRobot.second, clay.robot),
            ResourceRobotCounts(obsidian.count, obsidian.robot + 1),
            ResourceRobotCounts(geode.count, geode.robot)
        )
    }

    fun craftGeodeRobot(blueprint: Blueprint): ResourceRobotState {
        return ResourceRobotState(
            time,
            ResourceRobotCounts(ore.count - blueprint.geodeRobot.first, ore.robot),
            ResourceRobotCounts(clay.count, clay.robot),
            ResourceRobotCounts(obsidian.count - blueprint.geodeRobot.second, obsidian.robot),
            ResourceRobotCounts(geode.count, geode.robot + 1)
        )
    }

    companion object {
        fun init(): ResourceRobotState {
            return ResourceRobotState(
                0,
                ResourceRobotCounts(0, 1),
                ResourceRobotCounts(0, 0),
                ResourceRobotCounts(0, 0),
                ResourceRobotCounts(0, 0),
            )
        }
    }
}
