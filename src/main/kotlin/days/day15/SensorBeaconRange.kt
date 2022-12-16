package days.day15

import util.Point
import util.allInts

data class SensorBeaconRange(val sensor: Point, val beacon: Point, val range: Int) {

    fun withinSensorRange(point: Point): Boolean {
        return sensor.manhattanDistance(point) <= range
    }

    fun isBeaconLocation(point: Point): Boolean {
        return beacon == point
    }

    fun minX(): Int {
        return if (sensor.x < beacon.x) {
            sensor.x
        } else {
            beacon.x
        }
    }

    fun maxX(): Int {
        return if (sensor.x > beacon.x) {
            sensor.x
        } else {
            beacon.x
        }
    }

    fun minY(): Int {
        return if (sensor.y < beacon.y) {
            sensor.y
        } else {
            beacon.y
        }
    }

    fun maxY(): Int {
        return if (sensor.y > beacon.y) {
            sensor.y
        } else {
            beacon.y
        }
    }

    companion object {
        fun fromString(line: String): SensorBeaconRange {
            val values = allInts(line)
            val sensor = Point(values[0], values[1])
            val beacon = Point(values[2], values[3])
            val range = sensor.manhattanDistance(beacon)
            return SensorBeaconRange(sensor, beacon, range)
        }
    }
}