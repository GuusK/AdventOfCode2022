package days

import days.day15.SensorBeaconRange
import resources.InputReader
import util.Point

object Day15 : DayInterface {
    override val dayNumber: Int
        get() = 15

    fun findDims(parsed: List<SensorBeaconRange>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val minX = parsed.map { it.minX() - it.range }.min()
        val maxX = parsed.map { it.maxX() + it.range }.max()
        val minY = parsed.map { it.minY() - it.range }.min()
        val maxY = parsed.map { it.maxY() + it.range }.max()
        return Pair(Pair(minX, maxX), Pair(minY, maxY))
    }

    fun isPossibleLocation(posLoc: Point, sbrs: List<SensorBeaconRange>): Boolean {
        for (sbr in sbrs) {
            if (sbr.isBeaconLocation(posLoc)) {
                return true
            }
            if (sbr.withinSensorRange(posLoc)) {
                return false
            }
        }
        return true
    }

    fun countImpossibleLocations(y: Int, begin: Int, end: Int, sbrs: List<SensorBeaconRange>): Int {
        var countImpossibleLocs = 0
        for (x in begin..end) {
            val loc = Point(x, y)
            if (!isPossibleLocation(loc, sbrs)) {
                countImpossibleLocs += 1
            }
        }
        return countImpossibleLocs
    }

    fun isBeaconLocation(posLoc: Point, sbrs: List<SensorBeaconRange>): Boolean {
        for (sbr in sbrs) {
            if (posLoc == sbr.beacon) {
                return true
            }
        }
        return false
    }
    
    fun isPointWithinLimits(loc: Point, downlim: Int, uplim: Int): Boolean {
        return loc.x in downlim..uplim && loc.y in downlim..uplim
    }

    fun isPossibleDistressLocation(loc: Point, downlim: Int, uplim: Int, sbrs: List<SensorBeaconRange>): Boolean {
        return isPointWithinLimits(loc, downlim, uplim)
                && !isBeaconLocation(loc, sbrs)
                && isPossibleLocation(loc, sbrs)
    }

    fun findDistressSignal(sbrs: List<SensorBeaconRange>, downlim: Int, uplim: Int): Point {
        // If there was no unique solution, one could move it over in one direction without any problem
        // Since there is only one unique solution, it needs to be just outside the range of a sensor.
        for (sbr in sbrs) {
            val sensor = sbr.sensor
            for (dx in -(sbr.range + 1)..sbr.range + 1) {
                val x = sensor.x + dx
                val dy = (sbr.range + 1) + dx
                val upY = Point(x, sensor.y + dy)
                if (isPossibleDistressLocation(upY, downlim, uplim, sbrs)) {
                    return upY
                }

                val downY = Point(x, sensor.y - dy)
                if (isPossibleDistressLocation(downY, downlim, uplim, sbrs)) {
                    return downY
                }
            }
        }
        return Point(-1,-1)
    }

    override fun part1(): Any {
        val input = InputReader.getResourceLines(dayNumber)
        val parsed = input.map(SensorBeaconRange::fromString)

        val (xlims, ylims) = findDims(parsed)
        println("$xlims, $ylims")
        
        return countImpossibleLocations(2000000, xlims.first, xlims.second, parsed)
    }

    override fun part2(): Any {
        val input = InputReader.getResourceLines(dayNumber)
        val sbrs = input.map(SensorBeaconRange::fromString)

        val loc= findDistressSignal(sbrs, 0, 4000000)
        return loc.x * 4000000L + loc.y
    }
}