package days.day19

data class ResourceRobotCounts(val count: Int, val robot: Int){
    
    fun step(): ResourceRobotCounts {
        return ResourceRobotCounts(count + robot, robot)
    }
}